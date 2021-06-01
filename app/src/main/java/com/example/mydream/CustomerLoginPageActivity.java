package com.example.mydream;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CustomerLoginPageActivity extends AppCompatActivity {

    Button login,sign_up;
    EditText user_name,password;
    private CustomerViewModel customerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user_name=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);
        login=findViewById(R.id.login_button);
        sign_up=findViewById(R.id.signup_button);
        customerViewModel= new ViewModelProvider(this).get(CustomerViewModel.class);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it=new Intent(CustomerLoginPageActivity.this, ItemMainPageActivity.class);
                //it.putExtra("id",user_name.getText().toString());
                startActivity(it);
                List<String> check=customerViewModel.getAllUserName();
                int check1=0;
                for(int i=0;i<check.size();i++){
                    String str1=check.get(i);
                    String str2=user_name.getText().toString();
                    if(str1.equals(str2)){
                        check1=1;
                        break;
                    }
                }
                if(check1==0){
                    Toast.makeText(CustomerLoginPageActivity.this,"This Username Does Not Exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                String str2=password.getText().toString();
                String str1=customerViewModel.getPassword((user_name.getText().toString()));
                if(str1.equals(str2)){
                }
                else{
                    Toast.makeText(CustomerLoginPageActivity.this,"password is incorrect!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(CustomerLoginPageActivity.this, CustomerFormActivity.class);
                startActivity(it);
            }
        });
    }
}