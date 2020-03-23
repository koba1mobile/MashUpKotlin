package com.example.gitsearch.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gitsearch.MainActivity
import com.example.gitsearch.R
import com.example.gitsearch.constant.Constants
import com.example.gitsearch.search.data.GitRepo

class UserInfoFragment: Fragment() {
    lateinit var ivAvatar: ImageView
    lateinit var tvFullName: TextView
    lateinit var tvStarNum: TextView
    lateinit var tvDescription: TextView
    lateinit var tvLanguage: TextView
    lateinit var tvUpdate: TextView
    lateinit var data: GitRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_info, null)

        initView(view)

        init()

        return view
    }
    fun initView(root: View){
        ivAvatar = root.findViewById(R.id.iv_avatar)
        tvFullName = root.findViewById(R.id.tv_full_name)
        tvStarNum = root.findViewById(R.id.tv_star_num)
        tvDescription = root.findViewById(R.id.tv_description)
        tvLanguage = root.findViewById(R.id.tv_language)
        tvUpdate = root.findViewById(R.id.tv_update)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()
    }

    fun init(){
        data = (arguments?.getSerializable(Constants().key_git_repo_data) as GitRepo)

        // toolbar에 back 버튼 생성
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }
    fun bind(){
        Glide.with(context!!)
            .load(data.user.thumbnail_url)
            .into(ivAvatar)

        tvFullName.text = data.full_name
        tvStarNum.text = getString(R.string.user_info_star_num, data.stargazers_count)
        tvDescription.text = data.description
        tvLanguage.text = data.language
        if(data.updated_at.length > 9){
            tvUpdate.text = data.updated_at.substring(0..9)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                findNavController().navigate(R.id.action_UserInfoFragment_to_SearchFragment)
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }
}