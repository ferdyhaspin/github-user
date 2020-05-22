package com.ferdyhaspin.githubuserfavoriteapps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavorite(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.user.observe(this, Observer {
            if (it.isEmpty())
                Snackbar.make(rvUser, getString(R.string.empty_data), Snackbar.LENGTH_SHORT).show()
            else
                it.parseUser()
        })
    }

    private fun List<User>.parseUser() {
        val list = map {
            UserItem(it)
        }
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(list)
        }
        rvUser.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = mAdapter
        }
    }
}
