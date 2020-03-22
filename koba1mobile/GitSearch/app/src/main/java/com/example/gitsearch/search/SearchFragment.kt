package com.example.gitsearch.search

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.MainActivity
import com.example.gitsearch.R
import kotlinx.android.synthetic.main.activity_main.*

class SearchFragment : Fragment() {
    var recyclerViewGItInfo: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_search, null)
        initView(view)
        init()
        return view
    }

    fun initView(root: View){
        recyclerViewGItInfo = root.findViewById(R.id.recycler_view_git_info)
    }

    fun init(){
        // toolbar에 back 버튼 생성
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                findNavController().navigate(R.id.action_SearchFragment_to_FirstFragment)
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }
}