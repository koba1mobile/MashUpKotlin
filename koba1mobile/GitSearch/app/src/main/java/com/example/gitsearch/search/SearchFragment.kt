package com.example.gitsearch.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.MainActivity
import com.example.gitsearch.R
import com.example.gitsearch.search.api.GitSearchManager
import com.example.gitsearch.search.data.GitSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    lateinit var recyclerViewGItInfo: RecyclerView
    lateinit var searchManager: GitSearchManager
    lateinit var adapter: GitRepoAdapter

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
        recyclerViewGItInfo.layoutManager = LinearLayoutManager(context)
    }

    fun init(){
        // toolbar에 back 버튼 생성
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        searchManager = GitSearchManager()
        adapter = GitRepoAdapter(context!!)
        recyclerViewGItInfo.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchView: MenuItem = menu.findItem(R.id.search_view)

        (searchView.actionView as SearchView).setOnQueryTextListener(searchTextListener)
    }

    val searchTextListener = object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            requestSearch(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
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

    fun requestSearch(query: String?){
        val searchCall: Call<GitSearchResponse>

        searchCall = searchManager.requestGitRepositories(query ?: "") ?: return
        searchCall.enqueue(object : Callback<GitSearchResponse> {
            override fun onFailure(call: Call<GitSearchResponse>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<GitSearchResponse>,
                response: Response<GitSearchResponse>
            ) {
                adapter.data = response.body()?.items
                adapter.notifyDataSetChanged()
                println(response.body()?.total_count)
            }
        })

    }
}