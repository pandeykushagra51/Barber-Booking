package com.example.mydream

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydream.ItemAdapter.ItemClic
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Array
import java.util.*

class ItemMainPageActivity : AppCompatActivity(), ItemClic {
    var viewModel: ProductViewModel? = null
    var rv: RecyclerView? = null
    var rv1: RecyclerView? = null
    var loading_container: LinearLayout? = null
    var text: EditText? = null
    var mic: ImageButton? = null
    var loading_bar: ProgressBar? = null
    var itemAdapter: ItemAdapter? = null
    var suggestionAdapter: SuggestionAdapter? = null
    var product: ArrayList<Product>? = null
    var suggestions: List<String> = ArrayList()
    var productViewModel: ProductViewModel? = null
    var mLayoutManager : LinearLayoutManager? = null
    var visibleItemCount : Int = 0
    var totalItemCount : Int = 0
    var start : Boolean = true
    var pastVisiblesItems : Int = 0
    val offSet : Int = 5
    var prevList : List<Product>? = null
    var list : ArrayList<Product>? = null
    var allItemLoaded: Boolean? = false
    var loading: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_main_page)
        setId()

        itemAdapter = ItemAdapter(this, this@ItemMainPageActivity, product)
        suggestionAdapter = SuggestionAdapter(this, suggestions)
        productViewModel = ProductViewModel(application)
        rv1!!.layoutManager = LinearLayoutManager(this)
        rv1!!.adapter = suggestionAdapter
        mLayoutManager = LinearLayoutManager(this)
        rv!!.layoutManager = mLayoutManager
        rv!!.adapter = itemAdapter
        CoroutineScope(IO).launch {
            withContext(Main){
                loading_bar!!.visibility = View.VISIBLE
                loading_container!!.visibility = View.GONE
            }
            var cist = async {   productViewModel!!.getNextProduct(null, 5)}
            while (!cist.isCompleted){
                cist
            }
            itemAdapter!!.Insert(cist.await())
            prevList = cist.await()
            withContext(Main) {
                loading_bar!!.visibility = View.GONE
            }
            Log.e("gjhv", "onCreate: ${cist.isCompleted}", )
            System.out.println("kfjbdjd kjbdjsj")
            start = false
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

        rv!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.e("itemMainPageAct", "onScrollStateChanged: sdjvkj", )
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    CoroutineScope(IO).async {
                        visibleItemCount = mLayoutManager!!.getChildCount()
                        totalItemCount = mLayoutManager!!.getItemCount()
                        pastVisiblesItems = mLayoutManager!!.findFirstVisibleItemPosition()
                        if (start == false && allItemLoaded == false&&loading==false) {
                            loading = true
                            if (visibleItemCount + pastVisiblesItems  >= totalItemCount && totalItemCount <= 20) {
                                withContext(Main) {
                                    loading_container!!.visibility = View.VISIBLE
                                }
                                val p = async { productViewModel!!.getNextProduct(prevList!!.get(offSet - 1).getItemId(), offSet) }
                                while (p.isCompleted) {
                                    p
                                }
                                list = p.await()
                                prevList = p.await()
                                itemAdapter!!.Insert(p.await())
                                withContext(Main) {
                                    loading_container!!.visibility = View.GONE
                                }
                                if (list!!.size < offSet) {
                                    allItemLoaded = true;
                                }
                            }
                            loading = false
                        }
                    }
                }
            }
        })

    }

    fun setId() {
        rv = findViewById(R.id.item_rv)
        rv1 = findViewById(R.id.suggestion_rv)
        text = findViewById(R.id.serach_item_text)
        mic = findViewById(R.id.serach_mic)
        loading_bar = findViewById(R.id.loading)
        loading_container = findViewById(R.id.progress_bar_container)
    }

    override fun onItemClick(view: View?, itemId: String) {
        val card = view as MaterialCardView
        if (card.isChecked) {
            card.isChecked = !card.isChecked
            return
        }
        val it = Intent(this, BookItemActivity::class.java)
        it.putExtra("itemId", itemId)
        startActivity(it)
    }

    override fun onItemLongClick(view: View?, position: Int): Boolean {
        val card = view as MaterialCardView
        card.isChecked = !card.isChecked
        return true
    }
}