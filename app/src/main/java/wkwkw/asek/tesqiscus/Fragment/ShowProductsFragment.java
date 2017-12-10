package wkwkw.asek.tesqiscus.Fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import wkwkw.asek.tesqiscus.Adapter.ProductAdapter;
import wkwkw.asek.tesqiscus.Loader.ProductLoader;
import wkwkw.asek.tesqiscus.MainActivity;
import wkwkw.asek.tesqiscus.Model.Product;
import wkwkw.asek.tesqiscus.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProductsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Product>> {
    private FloatingActionButton fabAddProduct;
    private RecyclerView rvShowProduct;
    private Button btnReload;
    private View loadingIndicator;
    private TextView tvEmptyState;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProductAdapter productAdapter;

    public ShowProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Product");
        View view = inflater.inflate(R.layout.fragment_show_products, container, false);
        fabAddProduct = view.findViewById(R.id.fab_add_product);
        rvShowProduct = view.findViewById(R.id.rv_show_product);
        btnReload = view.findViewById(R.id.btn_reload);
        loadingIndicator = view.findViewById(R.id.loading_indicator);
        tvEmptyState = view.findViewById(R.id.tv_empty_state);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadData();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                        add(R.id.frame_container,
                                new AddProductFragment(),
                                AddProductFragment.class.getSimpleName())
                        .addToBackStack( AddProductFragment.class.getSimpleName())
                        .commit();
            }
        });
        reloadData();
        setHasOptionsMenu(true);
        return view;
    }
    public void reloadData(){
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        rvShowProduct.setVisibility(View.GONE);
        tvEmptyState.setText("");
        if (activeNetwork != null && activeNetwork.isConnected()) {
            loadingIndicator.setVisibility(View.VISIBLE);
            btnReload.setVisibility(View.GONE);
            getLoaderManager().restartLoader(1, null,this);
            getLoaderManager().initLoader(1, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            btnReload.setVisibility(View.VISIBLE);
            tvEmptyState.setText(getResources().getString(R.string.text_check_intenet_connection));
        }

    }
    @Override
    public Loader<ArrayList<Product>> onCreateLoader(int id, Bundle args) {
        return new ProductLoader(this.getContext().getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Product>> loader, ArrayList<Product> productArrayList) {
        loadingIndicator.setVisibility(View.GONE);
        if (productArrayList != null && !productArrayList.isEmpty()) {
            ProductAdapter productAdapter1 = new ProductAdapter(getContext(), productArrayList);
            productAdapter = new ProductAdapter();
            productAdapter.setData(productArrayList);
            btnReload.setVisibility(View.GONE);
            rvShowProduct.setAdapter(productAdapter1);
            rvShowProduct.setVisibility(View.VISIBLE);
            rvShowProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvShowProduct.setHasFixedSize(true);
            tvEmptyState.setText("");
        }else{
            btnReload.setVisibility(View.VISIBLE);
            tvEmptyState.setText(getResources().getString(R.string.text_no_data_found));
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Product>> loader) {

    }
}
