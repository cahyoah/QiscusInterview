package wkwkw.asek.tesqiscus.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import wkwkw.asek.tesqiscus.Model.ProductReview;
import wkwkw.asek.tesqiscus.R;

/**
 * Created by ASUS on 10/12/2017.
 */

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.ProductReviewViewHolder> {

    private Context context;
    private List<ProductReview> productReviewList;

    public ProductReviewAdapter(Context context, List<ProductReview> productReviewList) {
        this.context = context;
        this.productReviewList = productReviewList;
    }

    public ProductReviewAdapter() {

    }
    public void setData(List<ProductReview> items){
        productReviewList = items;
        notifyDataSetChanged();
    }

    @Override
    public ProductReviewAdapter.ProductReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_product_review, parent, false);
        return new ProductReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductReviewViewHolder holder, int position) {
        holder.tvAuthor.setText(productReviewList.get(position).getAuthor());
        holder.tvBody.setText(productReviewList.get(position).getBody());
        holder.rbTotalReview.setNumStars(5);
        holder.rbTotalReview.setRating(Integer.parseInt(productReviewList.get(position).getRating()));
    }


    @Override
    public boolean onFailedToRecycleView(ProductReviewViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if (productReviewList.size()>0){
            return productReviewList.size();
        }
        return  0;
    }

    public class ProductReviewViewHolder extends RecyclerView.ViewHolder{
        TextView tvBody, tvAuthor;
        RatingBar rbTotalReview;


        public ProductReviewViewHolder(final View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_product_review_author);
            tvBody = itemView.findViewById(R.id.tv_product_review_body);
            rbTotalReview = itemView.findViewById(R.id.rb_product_review_star);

        }
    }
}