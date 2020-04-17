package com.example.gitsearch.search

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.MainActivity
import com.example.gitsearch.R
import com.example.gitsearch.common.DisposableManager
import com.example.gitsearch.common.list.ItemClickListener
import com.example.gitsearch.common.list.ItemData
import com.example.gitsearch.common.util.Utils
import com.example.gitsearch.constant.Constants
import com.example.gitsearch.search.api.GitSearchManager
import com.example.gitsearch.data.GitRepo
import com.example.gitsearch.db.DatabaseManager
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.jakewharton.rxbinding2.support.v7.widget.queryTextChangeEvents
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search.*
import java.lang.IllegalStateException

class SearchFragment : Fragment(), ItemClickListener {
    lateinit var searchManager: GitSearchManager
    lateinit var adapter: GitRepoAdapter

    private lateinit var searchDisposables: DisposableManager
    private lateinit var viewDisposables: DisposableManager

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        activity as AppCompatActivity
        searchDisposables = DisposableManager(activity)
        viewDisposables = DisposableManager(activity, isClearOnStop = false)
    }

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
        adapter = GitRepoAdapter(requireContext(), this)

        rv_git_info.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SearchFragment.adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search_view).apply {
            expandActionView()
        }

        with(searchItem.actionView as SearchView){
            requestFocus()
            Utils.showKeyboard(context, this)

            viewDisposables.add(queryTextChangeEvents()
                .filter { it.isSubmitted }
                .map { it.queryText() }
                .filter { it.isNotEmpty() }
                .map { it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ query ->
                    requestSearch(query)
                })
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
        searchDisposables.add(searchManager.requestGitRepositories(query ?: "")
            .flatMap {
                if(0 == it.total_count){
                    // 검색 결과가 없을 경우
                    Observable.error(IllegalStateException(getString(R.string.search_no_result)))
                } else {
                    println("총 검색 결과 : ${it.total_count}개")
                    Observable.just(it.items)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ items ->
                with(adapter){
                    setData(items)
                    notifyDataSetChanged()
                }
            }) {
                // 에러 메시지 토스트 출력
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            })

    }

    override fun onItemClick(v: View, item: ItemData) {
        val data: GitRepo = item as GitRepo
        val bundle: Bundle = Bundle().apply {
            putSerializable(Constants.key_git_repo_data, data)
        }

        findNavController().navigate(R.id.action_SearchFragment_to_UserInfoFragment, bundle)

        DatabaseManager().getGitRepoDao(requireContext()).apply {
            insertRepos(data)
        }
    }
}