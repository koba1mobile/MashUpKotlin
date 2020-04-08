package com.example.gitsearch.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.gitsearch.MainActivity
import com.example.gitsearch.R
import com.example.gitsearch.constant.Constants
import com.example.gitsearch.data.GitRepo
import kotlinx.android.synthetic.main.fragment_user_info.*

class UserInfoFragment: Fragment() {
    lateinit var data: GitRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_info, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        bind()
    }

    fun init(){
        data = (arguments?.getSerializable(Constants.key_git_repo_data) as GitRepo)

        // toolbar에 back 버튼 생성
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    fun bind(){
        Glide.with(context!!)
            .load(data.user.thumbnail_url)
            .into(iv_avatar)

        tv_full_name.text = data.full_name
        tv_star_num.text = getString(R.string.user_info_star_num, data.stargazers_count)
        tv_description.text = data.description
        tv_language.text = data.language
        if(data.updated_at.length > 9){
            tv_update.text = data.updated_at.substring(0..9)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }
}