import os
from math import log
from json import loads, dumps
from sklearn.linear_model import LinearRegression

d = os.path.dirname(os.path.abspath(__file__))
RESULTS_NUM = 40
FIELDS = ['author', 'summary', 'title']


def features(query_tokens, field):
    cnt = sum((1 for token in query_tokens if token in field))
    return cnt / len(query_tokens)


def dcg(relevances):
    i = 1
    res = 0
    filtered = (min(5, max(1, relevance)) for relevance in relevances)
    for r in filtered:
        res += r / log(i + 1)
        i += 1
    return res


def idcg(relevances):
    return dcg(sorted(relevances, reverse=True))


def ndcg(dcg_val, idcg_val):
    return dcg_val / idcg_val


def check_ndcg(real, prediction):
    idcg_real = idcg(real)
    dcg_real = dcg(real)
    dcg_prediction = dcg(prediction)

    print('old         dcg:\t', round(dcg_real, 2))
    print('predictions dcg:\t', round(dcg_prediction, 2))
    print('old         ndcg:\t', round(ndcg(dcg_real, idcg_real), 2))
    print('predictions ndcg:\t', round(ndcg(dcg_prediction, idcg_real), 2), '\n')


def main():
    x, y = [], []
    jsons = [f for f in os.listdir(d) if f.endswith('.json')]
    for json_file in jsons:
        with open(f'{d}/{json_file}', 'r') as f:
            data = loads(f.read())

        for doc in data:
            x.append([features(doc['query'], doc[field]) for field in FIELDS])
            y.append(doc['relevance'])

    regression = LinearRegression().fit(x, y)
    predictions = regression.predict(x)

    print('Stats')
    for it, json_file in enumerate(jsons):
        print(json_file)
        start = RESULTS_NUM * it
        end = start + RESULTS_NUM

        y_slice = y[start:end]
        pred_slice = predictions[start:end]

        tup_pred = []
        for it in range(len(y_slice)):
            tup_pred.append((y_slice[it], pred_slice[it]))

        tup_pred.sort(key=lambda x: -x[1])
        for it in range(len(tup_pred)):
            pred_slice[it] = tup_pred[it][0]

        check_ndcg(y_slice, pred_slice)


if __name__ == '__main__':
    main()

