package com.ferdyhaspin.githubuserapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.data.model.UsersItem
import com.ferdyhaspin.githubuserapp.databinding.ActivityDetailBinding
import com.ferdyhaspin.githubuserapp.util.EXTRA_USER
import kotlinx.android.synthetic.main.toolbar.*


class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initToolbar()
        initView()
    }

    private fun initView() {
        intent.getParcelableExtra<UsersItem>(EXTRA_USER)?.let {
            viewModel.setUser(it)
        }
    }

    private fun initToolbar() {
        toolbar.title = "Detail"
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_back)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
