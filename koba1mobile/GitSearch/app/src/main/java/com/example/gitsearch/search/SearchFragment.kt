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
import com.example.gitsearch.common.list.ItemClickListener
import com.example.gitsearch.common.list.ItemData
import com.example.gitsearch.constant.Constants
import com.example.gitsearch.search.api.GitSearchManager
import com.example.gitsearch.data.GitRepo
import com.example.gitsearch.db.DatabaseManager
import com.example.gitsearch.db.GitRepoDao
import com.example.gitsearch.search.data.GitSearchResponse
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(), ItemClickListener {
    lateinit var searchManager: GitSearchManager
    lateinit var adapter: GitRepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        // toolbar에 back 버튼 생성
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)


        searchManager = GitSearchManager()
        adapter = GitRepoAdapter(context!!, this)
        rv_git_info.layoutManager = LinearLayoutManager(context)
        rv_git_info.adapter = adapter
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
                findNavController().navigate(R.id.action_SearchFragment_to_HistoryFragment)
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }

    fun requestSearch(query: String?){
        val searchCall: Call<GitSearchResponse>

        searchCall = searchManager.requestGitRepositories(query ?: "")
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

    override fun onItemClick(v: View, item: ItemData) {
        val data = item as GitRepo
        var bundle = Bundle()
        bundle.putSerializable(Constants().key_git_repo_data, data)

        findNavController().navigate(R.id.action_SearchFragment_to_UserInfoFragment, bundle)

        val gitRepoDao: GitRepoDao = DatabaseManager().getGitRepoDao(context!!)
        gitRepoDao.insertRepos(data)
    }
}