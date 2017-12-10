package wkwkw.asek.tesqiscus.Model;

/**
 * Created by ASUS on 10/12/2017.
 */

public class ProductReview {
    private String author;
    private String body;
    private String rating;

    public ProductReview(String author, String body, String rating) {
        this.author = author;
        this.body = body;
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public String getRating() {
        return rating;
    }
}
