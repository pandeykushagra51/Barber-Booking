package com.example.mydream;

import androidx.annotation.MenuRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

public class BookItemActivity extends AppCompatActivity {

    ImageView image;
    TextView itemName,sellerName,itemRate,phoneNumber, emailId, pinCode, adress;
    MaterialCardView card, cardDetail;
    Product product=null;
    Seller seller=null;
    List<byte []> images;
    ProductViewModel productViewModel;
    SellerViewModel sellerViewModel;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item);
        Intent it = getIntent();
        id= it.getIntExtra("id",-1);
        System.out.println(id+"\n");
        setId();
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        sellerViewModel = new ViewModelProvider(this).get(SellerViewModel.class);
        product = productViewModel.getProductDetail(id);
        seller = sellerViewModel.getSellerDetail(product.getSellerId());
        setData();

        cardDetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                cardDetail.setChecked(!cardDetail.isChecked());
                PopupMenu popup = new PopupMenu(BookItemActivity.this, cardDetail, Gravity.RIGHT);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        cardDetail.setChecked(!cardDetail.isChecked());
                        //Toast.makeText(BookItemActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                setPopupIcon(popup);

                popup.show();
                return true;
            }
        });
        cardDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cardDetail.isChecked())
                    cardDetail.setChecked(!cardDetail.isChecked());
            }
        });


    }



    public void setId(){
        image = findViewById(R.id.main_item_image);
        itemName = findViewById(R.id.main_item_name);
        itemRate = findViewById(R.id.main_item_rate);
        sellerName = findViewById(R.id.main_seller_name);
        phoneNumber = findViewById(R.id.main_phone_number);
        emailId = findViewById(R.id.main_emailid);
        pinCode = findViewById(R.id.main_pincode);
        adress = findViewById(R.id.main_adress);
        card = findViewById(R.id.image_card);
        cardDetail = findViewById(R.id.product_row);
        sellerName.setPaintFlags(sellerName.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        phoneNumber.setPaintFlags(phoneNumber.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        emailId.setPaintFlags(emailId.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        pinCode.setPaintFlags(pinCode.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        adress.setPaintFlags(adress.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
    }

    public void setData(){
        if(product==null)
            return;
       List<Bitmap> images = product.getItemImages();
        image.setImageBitmap(images.get(0));
        itemName.setText(product.getItemName());
        itemRate.setText(String.valueOf(product.getRate()));
        if(seller==null)
            return;
        sellerName.setText(seller.getShop_name());
        phoneNumber.setText(seller.getPhone_number());
        emailId.setText(seller.getEmail_id());
        pinCode.setText(seller.getPin_code());
        adress.setText(seller.getAdress());
    }

    public void  setPopupIcon(PopupMenu popup){
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon",boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}