package com.example.redditappandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.redditappandroid.databinding.ActivityMainBinding
import com.example.redditappandroid.models.Post
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private val postAdapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        apiCall(postAdapter,"", 10)
    }

    private fun apiCall(postAdapter: PostAdapter, startId: String, countLoaded: Long) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.reddit.com/top.json?limit=${countLoaded}&after=$startId"

        val stringRequest: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val obj: JSONObject = response
                try {
                    val array = obj.getJSONObject("data").getJSONArray("children")
                    for (i in 0..array.length()-1) {
                        val curPost: JSONObject = array.getJSONObject(i)
                        var username = curPost.getJSONObject("data").getString("author")
                        var created_utc = curPost.getJSONObject("data").getLong("created_utc")
                        var thumbnail = curPost.getJSONObject("data").getString("thumbnail")
                        var num_comments = curPost.getJSONObject("data").getInt("num_comments")
                        var name = curPost.getJSONObject("data").getString("name")
                        postAdapter.addPost(Post(username, created_utc.toLong(), thumbnail, num_comments.toInt(), name))
                    }
                }catch (e: Exception){
                    Log.e("Error", e.toString())
                }
            },
            { e ->
                Log.e("Error",e.toString())
            })
        queue.add(stringRequest)
    }

     private fun init(){
         binding.apply {
             rvPostList.layoutManager = LinearLayoutManager(this@MainActivity)
             rvPostList.adapter = postAdapter
         }
     }
}