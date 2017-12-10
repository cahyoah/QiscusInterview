package wkwkw.asek.tesqiscus.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.List;

import wkwkw.asek.tesqiscus.Model.Product;
import wkwkw.asek.tesqiscus.R;

/**
 * Created by ASUS on 10/12/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public ProductAdapter() {

    }
    public void setData(List<Product> items){
        productList = items;
        notifyDataSetChanged();
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        float starReview = 0;
        holder.tvProductName.setText(productList.get(position).getProductName());
        holder.tvProductPrice.setText(productList.get(position).getProductPrice());
        if(productList.get(position).getProductDescription().equals("null")){
            holder.tvProductDescription.setVisibility(View.GONE);
        }else{
            holder.tvProductDescription.setVisibility(View.VISIBLE);
            holder.tvProductDescription.setText(productList.get(position).getProductDescription());
        }
        for(int i =0; i< productList.get(position).getProductReviewArrayList().size();i++){
            starReview = (float) (starReview + Double.parseDouble(productList.get(position).getProductReviewArrayList().get(i).getRating()));
        }
        if(productList.get(position).getProductReviewArrayList().size() >0){
            holder.rbTotalReview.setNumStars(5);
            holder.rbTotalReview.setVisibility(View.VISIBLE);
            holder.tvTotalReview.setVisibility(View.VISIBLE);
            holder.rbTotalReview.setRating(starReview/productList.get(position).getProductReviewArrayList().size());
            holder.tvTotalReview.setText("("+productList.get(position).getProductReviewArrayList().size()+") Click here for product review");
        }else{
            holder.rbTotalReview.setVisibility(View.GONE);
            holder.tvTotalReview.setVisibility(View.GONE);
        }
        if(productList.get(position).getProductImage().size() ==0){
            holder.imgNoImage.setVisibility(View.VISIBLE);
            holder.sliderLayout.setVisibility(View.GONE);
            holder.imgNoImage.setImageResource(R.drawable.noimage);
        }else{
            holder.imgNoImage.setVisibility(View.GONE);
            holder.sliderLayout.setVisibility(View.VISIBLE);
            for(int i=0; i<productList.get(position).getProductImage().size();i++){
                TextSliderView textSliderView = new TextSliderView(context);
                // initialize a SliderLayout
                textSliderView
                        .description(productList.get(position).getProductName())
                        .image(productList.get(position).getProductImage().get(i))
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                //add your extra information
                textSliderView.bundle(new Bundle());
                holder.sliderLayout.addSlider(textSliderView);
            }
            holder.sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
            holder.sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.alert_dialog_product_review, null);
                dialogBuilder.setView(dialogView);
                RecyclerView rvProductReview = dialogView.findViewById(R.id.rv_show_product_review);
                TextView textView = dialogView.findViewById(R.id.tv_empty_state);
                if(productList.get(position).getProductReviewArrayList().size()==0){
                    textView.setVisibility(View.VISIBLE);
                    rvProductReview.setVisibility(View.GONE);
                    textView.setText(context.getResources().getString(R.string.text_no_review));
                }else{
                    textView.setVisibility(View.GONE);
                    ProductReviewAdapter productReviewAdapter = new ProductReviewAdapter(context, productList.get(position).getProductReviewArrayList());
                    rvProductReview.setAdapter(productReviewAdapter);
                    rvProductReview.setVisibility(View.VISIBLE);
                    rvProductReview.setLayoutManager(new LinearLayoutManager(context));
                    rvProductReview.setHasFixedSize(true);

                }
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }


    @Override
    public boolean onFailedToRecycleView(ProductViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if (productList.size()>0){
            return productList.size();
        }
        return  0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvProductPrice, tvProductDescription, tvTotalReview;
        RatingBar rbTotalReview;
        ImageView imgNoImage;
        SliderLayout sliderLayout;


        public ProductViewHolder(final View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvProductDescription = itemView.findViewById(R.id.tv_product_decription);
            tvTotalReview = itemView.findViewById(R.id.tv_total_review);
            rbTotalReview = itemView.findViewById(R.id.rb_product_review);
            imgNoImage = itemView.findViewById(R.id.img_noimage_product);
            sliderLayout = itemView.findViewById(R.id.slider);
        }
    }
}

