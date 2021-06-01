package com.example.mydream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    Button selectImage, addItem;
    ImageView itemImage;
    EditText name,rate,tags;
    String sellerId;
    int SELECT_PICTURE = 200;
    List<byte[]> images= new ArrayList<byte[]>();
    SellerViewModel sellerViewModel;
    Seller seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_item);

        setId();

        Intent it=getIntent();
        sellerId=it.getStringExtra("sellerId");

        ProductViewModel productViewModel = new ProductViewModel(getApplication());
        sellerViewModel= new ViewModelProvider(AddItemActivity.this).get(SellerViewModel.class);
        seller=sellerViewModel.getSellerDetail(sellerId);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json=(new Gson()).toJson(images);
                List<Integer> productId = seller.getProductIds();
                if(itemImage!=null)
                productViewModel.Insert( new Product(name.getText().toString() , Integer.valueOf(rate.getText().toString()), json, sellerId, tags.getText().toString()));
                else
                    Toast.makeText(AddItemActivity.this, "Must Select Image To add Item",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setId(){
        selectImage = findViewById(R.id.add_item_image_button);
        addItem = findViewById(R.id.add_item_button);
        itemImage = findViewById(R.id.add_item_image);
        name = findViewById(R.id.add_item_name);
        rate = findViewById(R.id.add_item_rate);
        tags = findViewById(R.id.add_item_tag);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    itemImage.setImageURI(selectedImageUri);
                    images.add(Tools.ImageViewToByte(itemImage));
                }
            }
        }
    }
}