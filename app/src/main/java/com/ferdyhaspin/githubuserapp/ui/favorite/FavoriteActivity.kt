package com.ferdyhaspin.githubuserapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.base.BaseActivity
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.ui.ViewModelFactory
import com.ferdyhaspin.githubuserapp.ui.detail.DetailActivity
import com.ferdyhaspin.githubuserapp.ui.main.MainItem
import com.ferdyhaspin.githubuserapp.util.ext.observe
import com.ferdyhaspin.githubuserapp.util.ext.toGone
import com.ferdyhaspin.githubuserapp.util.ext.toVisible
import com.ferdyhaspin.githubuserapp.util.ext.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class FavoriteActivity : BaseActivity(),
    MainItem.OnClickListener,
    MainItem.OnFavoriteClick {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var mAdapter: GroupAdapter<ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initViewModel()
        initView()
        observe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavorite()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
    }

    private fun initView() {
        toolbar.title = getString(R.string.favorite)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_back)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    private fun observe() {
        observe(viewModel.listFavorite, ::updateUI)
    }

    private fun updateUI(list: List<User>) {
        setListVisibility(list.size)
        val listItem = list.map {
            MainItem(it, this, true, this)
        }
        if (::mAdapter.isInitialized) {
            mAdapter.apply {
                clear()
                addAll(listItem)
                notifyItemRangeChanged(0, list.size)
            }
        } else {
            mAdapter = GroupAdapter<ViewHolder>().apply {
                addAll(listItem)
            }
            rvUser.apply {
                layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
                adapter = mAdapter
            }
        }
    }

    private fun setListVisibility(size: Int) {
        if (size == 0) {
            rvUser.toGone()
            linEmptyData.toVisible()
        } else {
            rvUser.toVisible()
            linEmptyData.toGone()
        }
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
        val intent = DetailActivity.newIntent(this, user)
        startActivity(intent, activityOptionsCompat.toBundle())
    }

    override fun onDeleteClickListener(user: User) {
        viewModel.delete(user)
        toast(getString(R.string.success_deleted))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
