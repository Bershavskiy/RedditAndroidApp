package com.example.redditappandroid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redditappandroid.databinding.PostItemBinding
import com.example.redditappandroid.models.Post

import kotlin.collections.ArrayList

class PostAdapter:RecyclerView.Adapter<PostAdapter.PostHolder>() {

    var postList:ArrayList<Post> = ArrayList()

    class PostHolder(item: View, ): RecyclerView.ViewHolder(item) {
        val binding = PostItemBinding.bind(item)
        fun bind (post: Post) {
            val hours = ((System.currentTimeMillis()/1000) - post.created_utc.toLong())/60/60
            binding.tvAutor.text =  "Posted by ${post.autor}"
            binding.tvDateAdded.text = "${hours} hours ago"
            binding.tvNumbersOfComment.text ="comments: ${post.numberOfComments.toString()}"

            if(post.thummbnail.substring(0,4)=="http"){
                binding.imageView2.visibility = View.VISIBLE
                Glide.with(binding.root).load(post.thummbnail).into(binding.imageView2);
                binding.postCard.setOnClickListener(View.OnClickListener {
                    binding.root.context.startActivity(Intent(binding.root.context,DownloadImageActivity::class.java).putExtra("name", post.thummbnail))
                })
            }else{
                binding.imageView2.visibility = View.GONE
            }
            binding.imageView2.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.bind(postList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun addPost(post:Post){
        postList.add(post)
        notifyDataSetChanged()
    }
}