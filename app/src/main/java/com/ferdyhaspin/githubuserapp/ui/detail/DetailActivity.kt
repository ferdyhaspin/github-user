package com.ferdyhaspin.githubuserapp.ui.detail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.base.BaseActivity
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.databinding.ActivityDetailBinding
import com.ferdyhaspin.githubuserapp.ui.ViewModelFactory
import com.ferdyhaspin.githubuserapp.util.EXTRA_USER
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class DetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initToolbar()
        initView()
    }

    private fun initViewModel() {
        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initView() {
        intent.getParcelableExtra<User>(EXTRA_USER)?.let {
            viewModel.setUser(it)
        }
    }

    private fun initToolbar() {
        toolbar.title = getString(R.string.title_detail)
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
