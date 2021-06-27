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

public class CustomerFormActivity extends AppCompatActivity {
    private Button select_image,customer_form_register;
    private EditText first_name,last_name,phone_number,email_id,user_name,password;
    private ImageView image;
    private Byte[] iamge;
    private CustomerViewModel customerViewModel;
    int SELECT_PICTURE=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);

        setId();

        customerViewModel= new ViewModelProvider(this).get(CustomerViewModel.class);

        customer_form_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> check=customerViewModel.getAllUserName();
                for(int i=0;i<check.size();i++){
                    if(check.get(i)==user_name.getText().toString()){
                        Toast.makeText(CustomerFormActivity.this,"THIS USERNAME IS UNAVAILABLE",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                customerViewModel.Insert(new Customer(user_name.getText().toString(),password.getText().toString(),
                                        first_name.getText().toString(),last_name.getText().toString(),phone_number.getText().toString(),
                                        email_id.getText().toString(),image.toString() ));
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