package Searching.Model;

public class Book {

    private String title;
    private String author;
    private String type;
    private String summary;
    private Integer price;
    private Long isbn;
    private String publisher;
    private String publicationDate;

    public Book(String title, String author, String type, String summary,
                Integer price, Long isbn, String publisher, String publicationDate) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.summary = summary;
        this.price = price;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
}
