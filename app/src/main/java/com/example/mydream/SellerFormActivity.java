package com.example.mydream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class SellerFormActivity extends AppCompatActivity {

    private Button select_image,customer_form_register;
    private EditText first_name,last_name,phone_number,email_id,user_name,password,city,locality,picode,shop_name;
    private ImageView image;
    private Byte[] iamge;
    private SellerViewModel sellerViewModel;
    int SELECT_PICTURE=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_form);

        setId();

        sellerViewModel= new ViewModelProvider(this).get(SellerViewModel.class);

        customer_form_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> check=sellerViewModel.getAllUserName();
                for(int i=0;i<check.size();i++){
                    String str1=check.get(i);
                    String str2=user_name.getText().toString();
                    if(str1.equals(str2)){
                        Toast.makeText(SellerFormActivity.this,"THIS USERNAME IS UNAVAILABLE",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                sellerViewModel.Insert(new Seller(user_name.getText().toString(),password.getText().toString(),
                        first_name.getText().toString(),last_name.getText().toString(),phone_number.getText().toString(),
                        email_id.getText().toString(),Tools.Companion.ImageViewToByte(image), picode.getText().toString(),
                        locality.getText().toString() + " " + city.getText().toString(), shop_name.getText().toString() ));
            }
        });

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

    }

    public void setId(){
        select_image=findViewById(R.id.login_button);
        customer_form_register=findViewById(R.id.customer_form_register);
        first_name=findViewById(R.id.customer_form_first_name);
        last_name=findViewById(R.id.customer_form_last_name);
        phone_number=findViewById(R.id.customer_form_phone_number);
        email_id=findViewById(R.id.customer_form_email_id);
        user_name=findViewById(R.id.customer_form_username);
        password=findViewById(R.id.customer_form_password);
        image=findViewById(R.id.customer_form_image);
        picode=findViewById(R.id.seller_form_pin_code);
        city=findViewById(R.id.seller_form_city);
        locality=findViewById(R.id.seller_form_locality);
        shop_name=findViewById(R.id.seller_form_shop_name);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    image.setImageURI(selectedImageUri);
                }
            }
        }
    }
}