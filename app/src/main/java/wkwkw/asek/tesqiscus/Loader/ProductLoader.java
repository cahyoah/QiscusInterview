package wkwkw.asek.tesqiscus.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import wkwkw.asek.tesqiscus.Control.ProductControl;
import wkwkw.asek.tesqiscus.Model.Product;

/**
 * Created by ASUS on 10/12/2017.
 */

public class ProductLoader extends AsyncTaskLoader<ArrayList<Product>> {
    private ArrayList<Product> mData;
    public boolean hasResult = false;

    public ProductLoader(final Context context) {
        super(context);
        onContentChanged();
    }



    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<Product> data) {
        mData = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResources(mData);
            mData = null;
            hasResult = false;
        }
    }

    @Override
    public ArrayList<Product> loadInBackground() {
        ArrayList<Product> products =new ArrayList<>();
        products = ProductControl.showProduct(getContext());

        return products;
    }

    protected void onReleaseResources(ArrayList<Product> data) {
        //nothing to do.
    }

    public ArrayList<Product> getResult() {
        return mData;
    }

}
