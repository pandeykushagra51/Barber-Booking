package com.example.mydream

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydream.ItemAdapter.ItemClic
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*

class ItemMainPageActivity : AppCompatActivity(), ItemClic {
    var viewModel: ProductViewModel? = null
    var rv: RecyclerView? = null
    var rv1: RecyclerView? = null
    var text: EditText? = null
    var mic: ImageButton? = null
    var itemAdapter: ItemAdapter? = null
    var suggestionAdapter: SuggestionAdapter? = null
    var product: List<Product>? = null
    var suggestions: List<String> = ArrayList()
    var productViewModel: ProductViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_main_page)
        setId()

        itemAdapter = ItemAdapter(this, this@ItemMainPageActivity, product)
        suggestionAdapter = SuggestionAdapter(this, suggestions)
        productViewModel = ProductViewModel(application)
        rv1!!.layoutManager = LinearLayoutManager(this)
        rv1!!.adapter = suggestionAdapter
        rv!!.layoutManager = LinearLayoutManager(this)
        rv!!.adapter = itemAdapter

        CoroutineScope(Main).launch {
            productViewModel!!.getAllProduct()!!.observe(this@ItemMainPageActivity, { products ->
                    itemAdapter!!.setData(products as List<Product>?)
                    itemAdapter = ItemAdapter(this@ItemMainPageActivity, this@ItemMainPageActivity, products as List<Product>?)

            })
        }
        text!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                rv1!!.visibility = View.VISIBLE
                rv!!.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = text!!.text.toString()
                val suggestion1 = productViewModel!!.getAutoSuggestion(str)
                suggestionAdapter!!.setData(suggestion1)
                suggestionAdapter!!.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable) {
                val str = text!!.text.toString()
                if (str.length == 0) mic!!.visibility = View.VISIBLE else mic!!.visibility = View.GONE
            }
        })
        text!!.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                rv1!!.visibility = View.GONE
                rv!!.visibility = View.VISIBLE
            }
        }
    }

    fun setId() {
        rv = findViewById(R.id.item_rv)
        rv1 = findViewById(R.id.suggestion_rv)
        text = findViewById(R.id.serach_item_text)
        mic = findViewById(R.id.serach_mic)
    }

    override fun onItemClick(view: View?, id: Int) {
        val card = view as MaterialCardView
        if (card.isChecked) {
            card.isChecked = !card.isChecked
            return
        }
        val it = Intent(this, BookItemActivity::class.java)
        it.putExtra("id", id)
        startActivity(it)
    }

    override fun onItemLongClick(view: View?, position: Int): Boolean {
        val card = view as MaterialCardView
        card.isChecked = !card.isChecked
        return true
    }
}