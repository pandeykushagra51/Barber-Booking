package com.example.mydream;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SellerItemAdapter extends RecyclerView.Adapter<SellerItemAdapter.ViewHolder> {
    Context context;
    List<Product> products= new ArrayList<Product>();
    public SellerItemAdapter(@NonNull Context context,List<Product> products) {
        this.context=context;
        this.products=products;
    }

    @NonNull
    @Override
    public SellerItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerItemAdapter.ViewHolder holder, int position) {
        holder.name.setText(products.get(position).getItemName());
        holder.rate.setText(String.valueOf(products.get(position).getRate()));
        holder.rb.setRating(4f);
        holder.iv.setImageBitmap(products.get(position).getItemImages().get(0));
    }

    @Override
    public int getItemCount() {
        if(products==null)
            return 0;
        return products.size();
    }

    public void setData(List<Product> products) {
        if(products!=null)
        this.products=products;
       notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView name,rate;
        RatingBar rb;

        public ViewHolder(View view) {
            super(view);
            iv=view.findViewById(R.id.item_image);
            name=view.findViewById(R.id.item_name);
            rate=view.findViewById(R.id.item_rate);
            rb=view.findViewById(R.id.main_item_rating);
        }
    }
}
