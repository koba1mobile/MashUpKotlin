package com.example.gitsearch.history

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.MainActivity
import com.example.gitsearch.R
import com.example.gitsearch.common.list.ItemClickListener
import com.example.gitsearch.common.list.ItemData
import com.example.gitsearch.constant.Constants
import com.example.gitsearch.data.GitRepo
import com.example.gitsearch.db.DatabaseManager
import com.example.gitsearch.search.GitRepoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HistoryFragment : Fragment(), ItemClickListener{
    lateinit var tvHitoryDescription: TextView
    lateinit var recyclerViewGitInfo: RecyclerView
    lateinit var adapter: GitRepoAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_history, container, false);
        initView(view)
        init()
        return view
    }

    fun initView(root: View){
        tvHitoryDescription = root.findViewById(R.id.tv_history_description)
        recyclerViewGitInfo = root.findViewById(R.id.rv_git_repo_history)
        recyclerViewGitInfo.layoutManager = LinearLayoutManager(context)

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewGitInfo)
    }

    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val list = adapter.data
                val data: GitRepo = list!![viewHolder.adapterPosition]

                DatabaseManager().getGitRepoDao(context!!).delete(data)
                (list as ArrayList).removeAt(position)
                adapter.notifyItemRemoved(position)

                tvHitoryDescription.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
                recyclerViewGitInfo.visibility = if(list.isEmpty()) View.GONE else View.VISIBLE

            }
        }

    fun init(){
        // back 버튼 제거
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val list: List<GitRepo> = DatabaseManager().getGitRepoDao(context!!).getAll()

        tvHitoryDescription.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
        recyclerViewGitInfo.visibility = if(list.isEmpty()) View.GONE else View.VISIBLE
        adapter = GitRepoAdapter(context!!, this)
        recyclerViewGitInfo.adapter = adapter

        adapter.data = list
        adapter.notifyDataSetChanged()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton_to_search).setOnClickListener {
            findNavController().navigate(R.id.action_HistoryFragment_to_SearchFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }


    override fun onItemClick(v: View, item: ItemData) {
        val data = item as GitRepo

        val bundle = Bundle()
        bundle.putSerializable(Constants().key_git_repo_data, data)

        findNavController().navigate(R.id.action_HistoryFragment_to_UserInfoFragment, bundle)
    }
}
