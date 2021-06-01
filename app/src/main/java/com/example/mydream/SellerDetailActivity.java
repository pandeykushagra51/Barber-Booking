package com.example.mydream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SellerDetailActivity extends AppCompatActivity {

    ImageView image;
    TextView shop_name;
    RecyclerView rv;
    FloatingActionButton fab;
    SellerItemAdapter sellerItemAdapter;
    String id;
    SellerViewModel sellerViewModel;
    List<Product> products=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);

        setId();

        Intent it=getIntent();
        id=it.getStringExtra("id");
        ProductRepo productRepo=new ProductRepo(getApplication());
        ProductViewModel productViewModel = new ProductViewModel(getApplication());
        sellerViewModel= new ViewModelProvider(this).get(SellerViewModel.class);
        Seller seller=sellerViewModel.getSellerDetail(id);
        sellerItemAdapter= new SellerItemAdapter(this,setAdapterItems(productViewModel.getAllProduct().getValue()));

        setData(seller);

        productViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                sellerItemAdapter.setData(setAdapterItems(products));
                sellerItemAdapter= new SellerItemAdapter(SellerDetailActivity.this,setAdapterItems(products));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(SellerDetailActivity.this,AddItemActivity.class);
                it.putExtra("sellerId",id);
                startActivity(it);
            }
        });
       // rv.setLayoutManager(new LinearLayoutManager(this));
      //  rv.setAdapter(sellerItemAdapter);

    }

    public void setId(){
        image=findViewById(R.id.seller_detail_image);
        rv=findViewById(R.id.product_recycler_view);
        shop_name=findViewById(R.id.seller_shop_name);
        fab=findViewById(R.id.seller_add_item);
    }

    public void setData(Seller seller){
        image.setImageBitmap(Tools.byteToBitmap(seller.image));
        shop_name.setText(seller.shop_name);
    }

    public List<Product> setAdapterItems(List<Product> products){
        if(products==null) {
            System.out.println( id + "\n" + id);
            return null;
        }
        List<Product> ans=new ArrayList<Product>();
        String str;
        for(int i=0;i<products.size();i++){
            str=products.get(i).sellerId;
            System.out.println(str + " " + id + "\n");
            if(str.equals(id))
                ans.add(products.get(i));
        }
        return ans;
    }
}