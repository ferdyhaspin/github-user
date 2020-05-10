package com.ferdyhaspin.githubuserapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.data.model.Resource
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.data.model.UsersItem
import com.ferdyhaspin.githubuserapp.data.repository.UserRepository
import com.ferdyhaspin.githubuserapp.util.ext.toGone
import com.ferdyhaspin.githubuserapp.util.ext.toVisible
import com.ferdyhaspin.githubuserapp.util.ext.toast
import com.throwback.adminkq.utils.observe
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), MainItem.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var repository: UserRepository
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initView()
        observe(viewModel.user, ::updateUI)
    }

    private fun initView() {
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
        refresh.setOnRefreshListener(this)
    }

    private fun initViewModel() {
        repository = UserRepository(this)
        viewModel = ViewModelProvider(
            this, MainViewModelFactory(repository)
        ).get(MainViewModel::class.java)

        viewModel.getUser()
    }

    override fun onRefresh() {
        viewModel.getUser()
    }

    private fun updateUI(source: Resource<User>) {
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

    private fun User.parseUser() {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            val list = this@parseUser.users.map {
                MainItem(it, this@MainActivity)
            }
            addAll(list)
        }

        rvUser.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = mAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            refresh.isRefreshing = true
            loading.startShimmerAnimation()
            loading.toVisible()
            rvUser.toGone()
        } else {
            refresh.isRefreshing = false
            loading.stopShimmerAnimation()
            loading.toGone()
            rvUser.toVisible()
        }
    }

    private fun showError(message: String) {
        toast(message)
    }

    override fun onItemClickListener(vararg view: View, user: UsersItem) {
        toast(user.toString())
    }
}
