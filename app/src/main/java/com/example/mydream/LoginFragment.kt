package com.example.mydream

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.widget.EditText
import android.content.SharedPreferences
import com.example.mydream.CustomerViewModel
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.view.LayoutInflater
import com.example.mydream.R
import androidx.lifecycle.ViewModelProvider
import com.example.mydream.BookItemActivity
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.mydream.CustomerFormActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : DialogFragment() {
    var login: Button? = null
    var signup: Button? = null
    var bookItem: Button? = null
    var userName: EditText? = null
    var password: EditText? = null
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var customerViewModel: CustomerViewModel? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.content_customer_login_page, null)
        sharedPreferences = requireContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        customerViewModel = ViewModelProvider(requireActivity()).get(CustomerViewModel::class.java)
        setId(view)
        login!!.setOnClickListener(View.OnClickListener {
            CoroutineScope(IO).launch {
                if (customerViewModel!!.logIn(userName!!.text.toString(), password!!.text.toString()) == true) {
                    withContext(Main) {
                        Toast.makeText(activity,"login fragment",Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "onViewCreated: login fragment right password", )
                        editor!!.putBoolean("isLoggedIn", true)
                        editor!!.putString("username", userName!!.text.toString())
                        editor!!.commit()
                        (activity as BookItemActivity?)!!.askConfirmationDialogue()
                        dismiss()
                    }
                } else {
                    withContext(Main) {
                        Toast.makeText(context, "Invalid user name or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        signup!!.setOnClickListener {
            val it = Intent(context, CustomerFormActivity::class.java)
            startActivity(it)
            dismiss()
        }
        return builder.setView(view).create()
    }


    private val isValidUser: Boolean
        private get() {
            if (!customerViewModel!!.isUserExist(userName!!.text.toString())) return false
            val password1 = customerViewModel!!.getPassword(userName!!.text.toString())
            return if (password1 == password!!.text.toString()) true else false
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "onViewCreated: loginFragment ", )
        super.onViewCreated(view, savedInstanceState)

    }

    fun setId(view: View?) {
        if (view != null) {
            login = view.findViewById(R.id.login_button)
            signup = view.findViewById(R.id.signup_button)
            userName = view.findViewById(R.id.login_username)
            password = view.findViewById(R.id.login_password)
            bookItem = view.findViewById(R.id.book_item)
        }
    }
}