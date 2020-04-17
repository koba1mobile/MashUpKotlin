package com.example.gitsearch.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitsearch.R
import com.example.gitsearch.common.list.ItemClickListener
import com.example.gitsearch.data.GitRepo
import kotlinx.android.synthetic.main.holder_git_repo.view.*

class GitRepoAdapter(var context: Context, var itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var data: MutableList<GitRepo>? = null

    init {
        data = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GitRepoViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_git_repo, parent, false), itemClickListener)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GitRepoViewHolder).bind(data!![position])
    }

    fun setData(list: List<GitRepo>){
        data?.clear()
        data?.addAll(list)
    }

    inner class GitRepoViewHolder(itemView: View, val listener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var context: Context

        init {
            initView(itemView)
            context = itemView.context
        }

        private fun initView(view: View){
            view.setOnClickListener{
                listener.onItemClick(view, this@GitRepoAdapter.data!![adapterPosition])
            }
        }

        fun bind(data: GitRepo){
            with(itemView){
                Glide.with(context)
                    .load(data.user.thumbnail_url)
                    .into(iv_avatar)

                tv_full_name.text = data.full_name
                tv_language.text = data.language ?: ""
            }
        }
    }
}