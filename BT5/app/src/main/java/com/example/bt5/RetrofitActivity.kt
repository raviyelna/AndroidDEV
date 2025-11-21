package com.example.bt5

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitActivity : AppCompatActivity() {
    private lateinit var rcCate: RecyclerView

    // FIX 1: Changed 'CategoryAdapter' to 'AdapterCate' (matched your adapter file)
    private lateinit var categoryAdapter: AdapterCate

    // FIX 2: Changed 'APIService' to 'APIservices' (matched your interface in MainActivity.kt)
    private lateinit var apiService: APIservices

    private lateinit var categoryList: List<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        rcCate = findViewById(R.id.rcCate)

        // FIX: Set the LayoutManager HERE (immediately), not inside the API response
        rcCate.layoutManager = LinearLayoutManager(this)

        getCategory()
    }


    private fun getCategory() {
        // FIX 3: Changed 'RetrofitClient' to 'RetroClient'
        // FIX 4: Changed 'getRetrofitInstance()' to 'getRetrofit()'
        // FIX 5: Added '!!' because getRetrofit() returns a nullable type in your code
        apiService = RetroClient.getRetrofit()!!.create(APIservices::class.java)

        // FIX 6: Changed '.getCategory()' to '.getCategoriesAll()' (matched your interface)
        apiService.getCategoriesAll().enqueue(object : Callback<List<Category>> {
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                android.util.Log.e("API_ERROR", "Error fetching categories", t)
                Toast.makeText(this@RetrofitActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful && response.body() != null) {
                    categoryList = response.body()!!

                    // FIX 7: Instantiate 'AdapterCate' instead of 'CategoryAdapter'
                    categoryAdapter = AdapterCate(this@RetrofitActivity, categoryList)
                    rcCate.adapter = categoryAdapter
                } else {
                    Toast.makeText(this@RetrofitActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
