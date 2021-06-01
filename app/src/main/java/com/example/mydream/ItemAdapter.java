package com.example.mydream;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    List<Product> products = new ArrayList<Product>(0);
    ItemClic itemClic;
    Context context;
    public ItemAdapter(Context context, ItemClic itemClic, List<Product> products) {
        this.products=products;
        this.itemClic=itemClic;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view,itemClic);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(products.get(position).itemName);
        holder.rate.setText(String.valueOf(products.get(position).rate));
        holder.rb.setRating(4f);
        try {
            holder.iv.setImageBitmap(products.get(position).getItemImages().get(0));
        }
        catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        if(products==null)
            return 0;
        return products.size();
    }

    public void setData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView iv;
        TextView name,rate;
        RatingBar rb;
        ItemClic itemClic;
        public ViewHolder(View view, ItemClic itemClic) {
            super(view);
            iv=view.findViewById(R.id.item_image);
            name=view.findViewById(R.id.item_name);
            rate=view.findViewById(R.id.item_rate);
            rb=view.findViewById(R.id.main_item_rating);
            this.itemClic=itemClic;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClic.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            return itemClic.onItemLongClick(v,getAdapterPosition());
        }
    }

    public interface ItemClic{
        public void onItemClick(View view, int id);
        public boolean onItemLongClick(View view, int position);
    }

}
