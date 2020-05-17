package com.ferdyhaspin.githubuserapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.base.BaseActivity
import com.ferdyhaspin.githubuserapp.data.model.Resource
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.ui.ViewModelFactory
import com.ferdyhaspin.githubuserapp.ui.detail.DetailActivity
import com.ferdyhaspin.githubuserapp.util.EXTRA_USER
import com.ferdyhaspin.githubuserapp.util.ext.observe
import com.ferdyhaspin.githubuserapp.util.ext.toGone
import com.ferdyhaspin.githubuserapp.util.ext.toVisible
import com.ferdyhaspin.githubuserapp.util.ext.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MainActivity : BaseActivity(),
    MainItem.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener,
    SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: GroupAdapter<ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initView()
        observe()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
    }

    private fun initView() {
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
        refresh.setOnRefreshListener(this)
        searchView.init()
    }

    private fun observe() {
        observe(viewModel.user, ::updateUI)
    }

    private fun SearchView.init() {
        setOnQueryTextListener(this@MainActivity)
        isSubmitButtonEnabled = true
        clearFocus()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.searchUser(query)
        viewModel.setTest(query)
        searchView.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String) = false

    override fun onRefresh() {
        viewModel.run {
            searchText.value?.let { username ->
                searchUser(username)
            }
        }
    }

    private fun updateUI(source: Resource<List<User>>) {
        showLoading(false)
        when (source) {
            is Resource.Loading -> showLoading(true)
            is Resource.Success -> {
                source.data?.parseUser()
            }
            is Resource.DataError -> {
                showError(source.error?.description.toString())
            }
        }
    }

    private fun List<User>.parseUser() {
        val list = this@parseUser.map {
            MainItem(it, this@MainActivity)
        }
        if (::mAdapter.isInitialized) {
            mAdapter.apply {
                clear()
                addAll(list)
                notifyItemRangeChanged(0, list.size)
            }
        } else {
            mAdapter = GroupAdapter<ViewHolder>().apply {
                addAll(list)
            }
            rvUser.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = mAdapter
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            refresh.isRefreshing = true
            loading.startShimmer()
            loading.toVisible()
            rvUser.toGone()
        } else {
            refresh.isRefreshing = false
            loading.stopShimmer()
            loading.toGone()
            rvUser.toVisible()
        }
    }

    private fun showError(message: String) {
        toast(message)
    }

    override fun onItemClickListener(vararg view: View, user: User) {
        val image = Pair(view[0], "detail_image")
        val name = Pair(view[1], "detail_name")

        val activityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                image,
                name
            )
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_USER, user)
        }
        startActivity(intent, activityOptionsCompat.toBundle())
    }
}
