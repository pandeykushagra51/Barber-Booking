package com.example.mydream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ItemMainPageActivity extends AppCompatActivity implements ItemAdapter.ItemClic {

    ProductViewModel viewModel;
    RecyclerView rv,rv1;
    EditText text;
    Button search;
    ItemAdapter itemAdapter;
    SuggestionAdapter suggestionAdapter;
    List<Product> product=null;
    List<String> suggestions=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_main_page);
        setId();
        itemAdapter = new ItemAdapter(this,ItemMainPageActivity.this,product);
        suggestionAdapter = new SuggestionAdapter(this,suggestions);

        ProductViewModel productViewModel = new ProductViewModel(getApplication());
        productViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                itemAdapter.setData(products);
                itemAdapter = new ItemAdapter(ItemMainPageActivity.this,ItemMainPageActivity.this,products);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(itemAdapter);
        rv1.setLayoutManager(new LinearLayoutManager(this));
        rv1.setAdapter(suggestionAdapter);
        rv1.setVisibility(View.GONE);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                rv1.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=text.getText().toString();
                List<String> suggestion1=productViewModel.getAutoSuggestion(str);
                suggestionAdapter.setData(suggestion1);
                suggestionAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        text.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    rv1.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void setId(){
        rv = findViewById(R.id.item_rv);
        rv1= findViewById(R.id.suggestion_rv);
        text = findViewById(R.id.serach_item_text);
        search = findViewById(R.id.search_bitton);
    }

    @Override
    public void onItemClick(View view, int id) {
        MaterialCardView card = (MaterialCardView) view;
        if(card.isChecked()){
            card.setChecked(!card.isChecked());
            return;
        }
        Intent it = new Intent(this,BookItemActivity.class);
        it.putExtra("id",id);
        System.out.println(id+"\n");
        startActivity(it);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        MaterialCardView card= (MaterialCardView) view;
        card.setChecked(!card.isChecked());
        return true;
    }



    public void hideSoftKeyboard(Activity activity) {


    }
}