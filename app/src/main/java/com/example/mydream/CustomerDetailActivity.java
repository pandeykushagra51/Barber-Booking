package com.example.mydream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerDetailActivity extends AppCompatActivity {

    ImageView image;
    TextView first_name,last_name,phone_number,email_id,user_name;
    Customer customer;
    CustomerViewModel customerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        setId();
        Intent it=getIntent();
        String id=it.getStringExtra("id");
        customerViewModel= new ViewModelProvider(this).get(CustomerViewModel.class);
        customer=customerViewModel.getCustomerDetail(id);
        setData();
    }

    private void setId(){
        image=findViewById(R.id.customer_detail_image);
        first_name=findViewById(R.id.customer_detail_last_name);
        last_name=findViewById(R.id.customer_detail_first_name);
        phone_number=findViewById(R.id.customer_detail_phone_number);
        email_id=findViewById(R.id.customer_detail_email_id);
        user_name=findViewById(R.id.customer_detail_user_name);
    }

    private void setData(){
        image.setImageBitmap(Tools.byteToBitmap(customer.getImage()));
        first_name.setText(customer.getFirst_name());
        last_name.setText(customer.getLast_name());
        phone_number.setText(customer.getPhone_number());
        email_id.setText(customer.getEmail_id());
        user_name.setText(customer.getUser_name());
    }
}