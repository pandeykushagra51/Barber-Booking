package com.example.mydream;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceDataStore;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends DialogFragment {
    Button login,signup,bookItem;
    EditText userName,password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CustomerViewModel customerViewModel;
    View view;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        view = inflater.inflate(R.layout.content_customer_login_page,null);

        setId(view);

        sharedPreferences = getContext().getSharedPreferences("loginInfo",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        customerViewModel =new ViewModelProvider(getActivity()).get(CustomerViewModel.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidUser()){
                    editor.putBoolean("isLoggedIn",true);
                    editor.putString("username",userName.getText().toString());
                    editor.commit();
                    ((BookItemActivity) getActivity()).askConfirmationDialogue();
                    dismiss();
                    return;
                }
                // use snackbar too show invalid username or password
                Toast.makeText(getContext(),"Invalid user name or password",Toast.LENGTH_SHORT).show();

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(),CustomerFormActivity.class);
                startActivity(it);
                dismiss();
            }
        });
        return builder.setView(view).create();
    }

    private boolean isValidUser() {
        if(!customerViewModel.isUserExist(userName.getText().toString()))
            return  false;
        String password1 = customerViewModel.getPassword(userName.getText().toString());
        if(password1.equals(password.getText().toString()))
        return true;
        return false;
    }

    public void setId(View view){
        login = view.findViewById(R.id.login_button);
        signup = view.findViewById(R.id.signup_button);
        userName = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);
        bookItem = view.findViewById(R.id.book_item);
    }

}
