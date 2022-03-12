package com.example.lenskart

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lenskart.model.ItemModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var detailViewModel: DetailViewModel? = null
    var detailsList = ArrayList<ItemModel>()
    var detailsAdapter:DetailsAdapter? = null
    @SuppressLint("SimpleDateFormat")
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    var map:MutableMap<String, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callApi()
        initUI()
    }

    private fun initUI() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                detailsAdapter!!.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                detailsAdapter!!.filter(newText)
                return true
            }
        })
    }

    private fun getFormattedDateOneMonthAgo(): String? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val currentDate = calendar.time
        return dateFormat.format(currentDate)
    }
    private fun initMap() {
        val date = getFormattedDateOneMonthAgo()
        map["q"] = "created:>"
        map["sort"] = "stars"
        map["order"] = "desc"
        map["page"] = "1"
        map["q"] = "created:>$date"
    }
    private fun callApi() {
        initMap()
        idPBLoading!!.visibility = View.VISIBLE
        if (detailViewModel == null)
            detailViewModel = ViewModelProviders.of(this, DetailViewModel.ViewModelFactory(map)).get(DetailViewModel::class.java)
        detailViewModel!!.getDetails()?.observe(this, androidx.lifecycle.Observer { baseResponse ->
            if (baseResponse != null) {
                idPBLoading!!.visibility = View.GONE
                detailsList =  baseResponse.items
                if(detailsList.size > 0)
                {
                    showDetails()
                }
            } else {
                Toast.makeText(this, "Some thing went wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun showDetails()
    {
        val layoutManager = LinearLayoutManager(this)
        rv_Details.layoutManager = layoutManager
        detailsAdapter = DetailsAdapter(this,  detailsList)
        rv_Details.adapter = detailsAdapter
        detailsAdapter!!.notifyDataSetChanged()
    }


}