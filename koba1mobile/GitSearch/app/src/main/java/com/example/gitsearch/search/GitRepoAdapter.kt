package com.example.gitsearch.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitsearch.R
import com.example.gitsearch.common.list.ItemClickListener
import com.example.gitsearch.search.data.GitRepo

class GitRepoAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var context: Context
    var data: List<GitRepo>?
    var itemClickListener: ItemClickListener

    constructor(context: Context, itemClickListener: ItemClickListener){
        this.context = context
        data = ArrayList()
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GitRepoViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_git_repo, parent, false), itemClickListener)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GitRepoViewHolder).bind(data!!.get(position))
    }


    inner class GitRepoViewHolder(itemView: View, val listener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var context: Context
        lateinit var IvAvatar: ImageView
        lateinit var TvName: TextView
        lateinit var TvLanauage: TextView

        init {
            initView(itemView)
            context = itemView.context
        }

        fun initView(view: View){
            view.setOnClickListener{
                listener.onItemClick(view, adapterPosition)
            }
            IvAvatar = view.findViewById(R.id.iv_avatar)
            TvName = view.findViewById(R.id.tv_full_name)
            TvLanauage = view.findViewById(R.id.tv_language)
        }

        fun bind(data: GitRepo){
            Glide.with(context)
                .load(data.user.thumbnail_url)
                .into(IvAvatar)

            TvName.text = data.full_name
            TvLanauage.text = data.language ?: ""
        }
    }
}