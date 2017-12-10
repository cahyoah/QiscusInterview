package wkwkw.asek.tesqiscus.Model;

import java.util.ArrayList;

/**
 * Created by ASUS on 10/12/2017.
 */

public class Product {

    private String id;
    private String productName;
    private String productPrice;
    private String productDescription;
    private ArrayList<String> productImage;
    private ArrayList<ProductReview> productReviewArrayList;

    public Product(String id, String productName, String productPrice, String productDescription, ArrayList<String> productImage, ArrayList<ProductReview> productReviewArrayList) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productReviewArrayList = productReviewArrayList;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public ArrayList<String> getProductImage() {
        return productImage;
    }

    public ArrayList<ProductReview> getProductReviewArrayList() {
        return productReviewArrayList;
    }
}
