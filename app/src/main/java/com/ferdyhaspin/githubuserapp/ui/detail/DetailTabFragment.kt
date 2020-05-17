package com.ferdyhaspin.githubuserapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.base.BaseFragment
import com.ferdyhaspin.githubuserapp.data.model.Resource
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.ui.main.MainItem
import com.ferdyhaspin.githubuserapp.util.ext.observe
import com.ferdyhaspin.githubuserapp.util.ext.toGone
import com.ferdyhaspin.githubuserapp.util.ext.toVisible
import com.ferdyhaspin.githubuserapp.util.ext.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.tab_detail_fragment.*

class DetailTabFragment : BaseFragment(), MainItem.OnClickListener {

    private var type = 0

    companion object {
        private const val TYPE = "type"

        fun newInstance(type: Int) = DetailTabFragment().apply {
            val bundle = bundleOf().apply {
                putInt(TYPE, type)
            }
            arguments = bundle
        }
    }

    private lateinit var mAdapter: GroupAdapter<ViewHolder>

    override val layoutId: Int
        get() = R.layout.tab_detail_fragment

    override val activity
        get() = requireActivity() as DetailActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(TYPE)?.let {
            type = it
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.run {
            val liveData = if (type == 0) {
                viewModel.followers
            } else {
                viewModel.following
            }
            observe(liveData, ::updateUI)
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
                source.error?.code?.let {
                    if (it == 0) {
                        setListVisibility(it)
                    } else {
                        showError(source.error.description)
                    }
                } ?: run {
                    showError(source.error?.description.toString())
                }
            }
        }
    }

    private fun List<User>.parseUser() {
        val list = this@parseUser.map {
            MainItem(it, this@DetailTabFragment)
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
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = mAdapter
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.startShimmer()
            loading.toVisible()
            rvUser.toGone()
        } else {
            loading.stopShimmer()
            loading.toGone()
            rvUser.toVisible()
        }
    }

    private fun showError(message: String) {
        activity.toast(message)
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
                activity,
                image,
                name
            )
        val intent = DetailActivity.newIntent(activity, user)
        startActivity(intent, activityOptionsCompat.toBundle())
    }
}