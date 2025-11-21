package com.example.bt5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // DEBUG LOG 1: Confirm Activity Started
        Log.d("DEBUG_APP", "MainActivity: onCreate started")
        Toast.makeText(this, "MainActivity Started", Toast.LENGTH_SHORT).show()

        try {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        } catch (e: Exception) {
            // This catches crashes if 'R.id.main' doesn't exist in your XML
            Log.e("DEBUG_APP", "Error setting insets: ${e.message}")
        }

        // LOGIC FIX: Navigate to RetrofitActivity automatically
        Log.d("DEBUG_APP", "MainActivity: Attempting to launch RetrofitActivity")
        val intent = Intent(this, RetrofitActivity::class.java)
        startActivity(intent)
    }
}

// ... (Keep your existing data class, object, and interface below)
data class Category(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("images")
    var images: String,
    @SerializedName("description")
    var description: String
) : Serializable

object RetroClient {
    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("http://app.iotstar.vn:8081/appfoods/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}

interface APIservices {
    @GET("categories.php")
    fun getCategoriesAll(): Call<List<Category>>
}
