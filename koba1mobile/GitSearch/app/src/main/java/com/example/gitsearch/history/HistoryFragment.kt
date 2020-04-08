package com.example.gitsearch.history

import android.os.Bundle
import android.view.*
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
import kotlinx.android.synthetic.main.fragment_history.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HistoryFragment : Fragment(), ItemClickListener{
    lateinit var adapter: GitRepoAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    private fun init(){
        rv_git_repo_history.layoutManager = LinearLayoutManager(context)

        ItemTouchHelper(simpleItemTouchCallback).apply {
            attachToRecyclerView(rv_git_repo_history)
        }

        // back 버튼 제거
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val list: List<GitRepo> = DatabaseManager().getGitRepoDao(context!!).getAll()

        tv_history_description.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
        rv_git_repo_history.visibility = if(list.isEmpty()) View.GONE else View.VISIBLE

        adapter = GitRepoAdapter(context!!, this)
        with(adapter){
            rv_git_repo_history.adapter = this@HistoryFragment.adapter
            data = list
            notifyDataSetChanged()
        }

        floatingActionButton_to_search.setOnClickListener {
            findNavController().navigate(R.id.action_HistoryFragment_to_SearchFragment)
        }
    }

    private var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
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

                tv_history_description.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
                rv_git_repo_history.visibility = if(list.isEmpty()) View.GONE else View.VISIBLE

            }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }


    override fun onItemClick(v: View, item: ItemData) {
        findNavController().navigate(R.id.action_HistoryFragment_to_UserInfoFragment, Bundle().apply {
            putSerializable(Constants.key_git_repo_data, item as GitRepo)
        })
    }
}
