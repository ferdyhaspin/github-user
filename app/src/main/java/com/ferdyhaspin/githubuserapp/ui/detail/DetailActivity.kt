package com.ferdyhaspin.githubuserapp.ui.detail

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.adapter.PagerAdapterActivity
import com.ferdyhaspin.githubuserapp.base.BaseActivity
import com.ferdyhaspin.githubuserapp.data.model.Resource
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.data.model.UserDetail
import com.ferdyhaspin.githubuserapp.databinding.ActivityDetailBinding
import com.ferdyhaspin.githubuserapp.ui.ViewModelFactory
import com.ferdyhaspin.githubuserapp.util.ext.observe
import com.ferdyhaspin.githubuserapp.util.ext.toGone
import com.ferdyhaspin.githubuserapp.util.ext.toVisible
import com.ferdyhaspin.githubuserapp.util.ext.toast
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class DetailActivity : BaseActivity() {

    private lateinit var menuItem: MenuItem

    companion object {
        private const val EXTRA_USER = "user"

        fun newIntent(context: Context, user: User): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_USER, user)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initView()
        initViewPager()
        observe()
    }

    private fun initViewModel() {
        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initView() {
        intent.getParcelableExtra<User>(EXTRA_USER)?.let {
            initToolbar(it.login)
            if (viewModel.user.value == null) {
                viewModel.setUser(it)
                viewModel.getDetail(it.login)
            }
        }
    }

    private fun initToolbar(username: String) {
        toolbar.title = username
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_back)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    private fun initViewPager() {
        val listFragment = mutableListOf<Fragment>().apply {
            add(DetailTabFragment.newInstance(0))
            add(DetailTabFragment.newInstance(1))
        }

        viewPager.adapter = PagerAdapterActivity(this, listFragment)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            0 -> getString(R.string.followers)
            1 -> getString(R.string.following)
            else -> ""
        }
    }

    private fun observe() {
        observe(viewModel.userDetailResponse, ::updateUI)
        observe(viewModel.isFavorite, ::setFavorite)
    }

    private fun updateUI(source: Resource<UserDetail>) {
        showLoading(false)
        when (source) {
            is Resource.Loading -> showLoading(true)
            is Resource.Success -> {
                source.data?.let {
                    viewModel.setUserDetail(it)
                }
            }
            is Resource.DataError -> {
                showError(source.error?.description.toString())
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.startShimmer()
            loading.toVisible()
            linDetail.toGone()
        } else {
            loading.stopShimmer()
            loading.toGone()
            linDetail.toVisible()
        }
    }

    private fun showError(message: String) {
        toast(message)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu.findItem(R.id.item_favorite)
        loadFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    private fun loadFavorite() {
        viewModel.loadUserFavorite(viewModel.user.value?.id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_favorite -> {
                viewModel.isFavorite.value?.let { isFavorite ->
                    viewModel.user.value?.let {
                        if (isFavorite) {
                            viewModel.deleteFavorite(it)
                            toast(getString(R.string.success_deleted))
                        } else {
                            viewModel.addFavorite(it)
                            toast(getString(R.string.success_added))
                        }
                        val brIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
                        sendBroadcast(brIntent)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavorite(isFavorite: Boolean) {
        menuItem.setFavoriteIcon(isFavorite)
    }

    private fun MenuItem.setFavoriteIcon(isFavorite: Boolean) {
        val icon = if (isFavorite) {
            R.drawable.ic_favorite
        } else {
            R.drawable.ic_favorite_border
        }
        setIcon(icon)
    }

}
