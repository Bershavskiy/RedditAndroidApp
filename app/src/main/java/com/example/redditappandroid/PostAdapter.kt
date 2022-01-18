package com.example.redditappandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.redditappandroid.databinding.PostItemBinding
import com.example.redditappandroid.models.Post

class PostAdapter:RecyclerView.Adapter<PostAdapter.PostHolder>() {
    var postList:ArrayList<Post> = ArrayList()

    class PostHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = PostItemBinding.bind(item)
        fun bind (post: Post){
            binding.tvAutor.text = post.autor
            binding.tvDateAdded.text = post.created_utc.toString()
            binding.tvNumbersOfComment.text = post.numberOfComments.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }
    fun addPost(post:Post){
        postList.add(post)
        notifyDataSetChanged()
    }
}