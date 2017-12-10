package wkwkw.asek.tesqiscus.Fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import wkwkw.asek.tesqiscus.Control.ProductControl;
import wkwkw.asek.tesqiscus.MainActivity;
import wkwkw.asek.tesqiscus.R;
import wkwkw.asek.tesqiscus.helper.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment implements View.OnClickListener {

    public EditText etProductName, etProductPrice, etProductDescription;
    public Button btnAddProduct;
    public CoordinatorLayout coordinatorLayout;

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Add Product");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        etProductName = view.findViewById(R.id.et_product_name);

        etProductPrice = view.findViewById(R.id.et_product_price);
        etProductDescription = view.findViewById(R.id.et_product_description);
        btnAddProduct = view.findViewById(R.id.btn_add_product);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        btnAddProduct.setOnClickListener(this);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add_product){
            ConnectivityManager cm =
                    (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null && activeNetwork.isConnected()) {
                if(etProductName.getText().toString().trim().isEmpty()||
                        etProductPrice.getText().toString().trim().isEmpty() ||
                        etProductDescription.getText().toString().trim().isEmpty()){
                    Util.showSnackBar(coordinatorLayout, getResources().getString(R.string.text_complete_data));
                }else{
                    ProductControl productControl = new ProductControl();
                    productControl.addProduct(getContext(), etProductName.getText().toString().trim(),
                            etProductPrice.getText().toString().trim(),
                            etProductDescription.getText().toString().trim());
                }
            } else {
                Util.showSnackBar(coordinatorLayout, getResources().getString(R.string.text_check_intenet_connection));
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            ((MainActivity)getActivity()).getSupportActionBar().setTitle("Product");
            ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
             getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
}
