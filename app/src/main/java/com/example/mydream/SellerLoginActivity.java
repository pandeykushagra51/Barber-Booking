package com.example.mydream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SellerLoginActivity extends AppCompatActivity {


    Button login,sign_up;
    EditText user_name,password;
    private SellerViewModel sellerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        setId();

        sellerViewModel= new ViewModelProvider(this).get(SellerViewModel.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> check=sellerViewModel.getAllUserName();
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
                    Toast.makeText(SellerLoginActivity.this,"This Username Does Not Exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                String str2=password.getText().toString();
                String str1= sellerViewModel.getPassword(user_name.getText().toString());
                if(str1.equals(str2)){
                    Intent it=new Intent(SellerLoginActivity.this, SellerDetailActivity.class);
                    it.putExtra("id",user_name.getText().toString());
                    startActivity(it);
                }
                else{
                    Toast.makeText(SellerLoginActivity.this,"password is incorrect!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(SellerLoginActivity.this, SellerFormActivity.class);
                startActivity(it);
            }
        });
    }
    public void setId(){
        user_name=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);
        login=findViewById(R.id.login_button);
        sign_up=findViewById(R.id.signup_button);
    }
}