public class Game {

    private String cover;
    private String name;
    private String amountOfAchievements;
    private String amountOfPoints;
    private String href;

    public Game() {
    }

    public Game(String cover, String name, String amountOfAchievements, String amountOfPoints, String href) {
        this.cover = cover;
        this.name = name;
        this.amountOfAchievements = amountOfAchievements;
        this.amountOfPoints = amountOfPoints;
        this.href = href;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmountOfAchievements() {
        return amountOfAchievements;
    }

    public void setAmountOfAchievements(String amountOfAchievements) {
        this.amountOfAchievements = amountOfAchievements;
    }

    public String getAmountOfPoints() {
        return amountOfPoints;
    }

    public void setAmountOfPoints(String amountOfPoints) {
        this.amountOfPoints = amountOfPoints;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
