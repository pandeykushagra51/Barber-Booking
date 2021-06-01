package com.example.mydream;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    RecyclerView rv;
    View view;
    Context ctx;
    Product product;
    Product pr=new Product("barber",4,null,"swn","snd");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_fragment,container,false);
        ctx=getActivity();
        rv=view.findViewById(R.id.service_row_id);
        //ItemAdapter adapter=new ItemAdapter(ctx,null);
        rv.setLayoutManager(new LinearLayoutManager(ctx));
      /*  productViewModel= new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        productViewModel.getAllProduct().observe(requireActivity(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.updatItem(products);
            }
        });*/
        //     rv.setAdapter(adapter);
//        ProductAdapter adapter=new ProductAdapter(ctx);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;


    }


}