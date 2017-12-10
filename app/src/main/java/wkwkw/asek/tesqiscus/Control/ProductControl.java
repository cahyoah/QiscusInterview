package wkwkw.asek.tesqiscus.Control;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import wkwkw.asek.tesqiscus.Model.Product;
import wkwkw.asek.tesqiscus.Model.ProductReview;
import wkwkw.asek.tesqiscus.R;
import wkwkw.asek.tesqiscus.helper.AppUrl;
import wkwkw.asek.tesqiscus.helper.Util;

/**
 * Created by ASUS on 10/12/2017.
 */

public class ProductControl {

    public void addProduct(final Context context, final String productName, final String productPrice, final String productDescription){
        final AsyncHttpClient client = new AsyncHttpClient();
        Util.showProgresDialog(context);
        RequestParams params = new RequestParams();
        params.add("product[name]", productName);
        params.add("product[price]", String.valueOf(Double.parseDouble(productPrice)));
        params.add("product[description]", productDescription);
        client.post(AppUrl.URL_PRODUCT, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject addProductResult = new JSONObject(result);
                    String name = addProductResult.getString("name");
                    String price = Integer.toString(addProductResult.getInt("price"));
                    String description = addProductResult.getString("description");
                    if(name.equals(productName)&&price.equals(productPrice)&&description.equals(productDescription)){
                        Util.showToast(context, context.getResources().getString(R.string.text_add_product_succes));
                    }else {
                        Util.showToast(context, context.getResources().getString(R.string.text_add_product_failed));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    Util.showToast(context, context.getResources().getString(R.string.text_add_product_failed));
                }
                Util.closeProgresDialog();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(error.getMessage());
                Util.showToast(context, context.getResources().getString(R.string.text_add_product_failed));
                Util.closeProgresDialog();
            }
        });
    }

    public static ArrayList<Product> showProduct(Context context){
        final ArrayList<Product> productArrayList = new ArrayList<>();
        final SyncHttpClient client = new SyncHttpClient();
        client.get(AppUrl.URL_PRODUCT, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                ArrayList<String> productImageUrlArrayList = new ArrayList<>();
                ArrayList<ProductReview> productReviewArrayList = new ArrayList<>();
                try {
                    String id;
                    String name = null;
                    String price = null;
                    String description = null;
                    JSONArray productArrayResult = new JSONArray(result);
                    System.out.println("panjang "+productArrayResult);
                    for(int i=0; i< productArrayResult.length();i++){
                        productImageUrlArrayList = new ArrayList<>();
                        productReviewArrayList = new ArrayList<>();
                        JSONObject productObject = productArrayResult.getJSONObject(i);
                        JSONArray imageArray = productObject.getJSONArray("images");
                        JSONArray reviewsArray = productObject.getJSONArray("reviews");
                        id = Integer.toString(productObject.getInt("id"));
                        if(productObject.getString("name")!=null){
                            name = productObject.getString("name");
                        }
                        if(productObject.getString("price") != null){
                            price = productObject.getString("price");
                        }
                        if(productObject.getString("description") != null){
                            description = productObject.getString("description");
                        }
                        if(imageArray.length() >0){
                            for(int j=0; j<imageArray.length();j++){
                                JSONObject imageObject = imageArray.getJSONObject(j);
                                if(imageObject.getString("full")!=null){
                                    productImageUrlArrayList.add(imageObject.getString("full"));
                                }
                                if(imageObject.getString("thumb")!=null){
                                    productImageUrlArrayList.add(imageObject.getString("thumb"));
                                }
                            }
                        }
                        if(reviewsArray.length() >0){
                            for(int j=0; j<reviewsArray.length();j++){
                                JSONObject reviewsObject = reviewsArray.getJSONObject(j);
                                String author = reviewsObject.getString("author");
                                String body = reviewsObject.getString("body");
                                String star = Integer.toString(reviewsObject.getInt("stars"));
                                ProductReview productReview = new ProductReview(author, body, star);
                                productReviewArrayList.add(productReview);
                            }
                        }
                        Product product = new Product(id, name, price, description, productImageUrlArrayList, productReviewArrayList);
                        productArrayList.add(product);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(error.getMessage());
            }
        });
        return productArrayList;
    }
}
