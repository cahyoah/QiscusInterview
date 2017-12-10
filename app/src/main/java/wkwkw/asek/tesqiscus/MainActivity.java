package wkwkw.asek.tesqiscus;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import wkwkw.asek.tesqiscus.Fragment.ShowProductsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    replace(R.id.frame_container,
                            new ShowProductsFragment(),
                            ShowProductsFragment.class.getSimpleName()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().setTitle("Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        super.onBackPressed();
    }
}
