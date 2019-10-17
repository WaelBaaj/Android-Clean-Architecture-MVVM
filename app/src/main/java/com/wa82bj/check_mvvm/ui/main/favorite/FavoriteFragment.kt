package com.wa82bj.check_mvvm.ui.main.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wa82bj.check_mvvm.AppExecutors
import com.wa82bj.check_mvvm.R
import com.wa82bj.check_mvvm.autoCleared
import com.wa82bj.check_mvvm.databinding.FavoriteFragmentBinding
import com.wa82bj.check_mvvm.ui.common.Result
import com.wa82bj.check_mvvm.ui.common.RetryAndWebWiewListener
import com.wa82bj.check_mvvm.ui.common.adapter.EndlessScrollListener
import com.wa82bj.check_mvvm.ui.common.adapter.GridSpacingItemDecoration
import com.wa82bj.check_mvvm.ui.main.allProduct.ProductsAdapter
import com.wa82bj.check_mvvm.vmFactory.ViewModelFactory
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject


/*
** the favorite list will download from Room db because the are no end point
* to get favorite items from web server
*/

class FavoriteFragment : DaggerFragment(), RetryAndWebWiewListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var appExecutors: AppExecutors

    private var adapter by autoCleared<ProductsAdapter>()

    private lateinit var binding: FavoriteFragmentBinding

    private val favoriteViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(FavoriteViewModel::class.java)
    }

    companion object {
        private const val SPAN_COUNT = 1

        fun newInstance(): FavoriteFragment = FavoriteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FavoriteFragmentBinding.inflate(layoutInflater, container, false)
        binding.favoriteModel = favoriteViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        loadFavorites()

        favoriteViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading.let {
                adapter.onFailure(!it)
            }
        })
    }

    private fun loadFavorites() {
        favoriteViewModel.favoriteProduct.observe(viewLifecycleOwner
            , Observer { result ->
                when (result) {
                    is Result.Success -> {
                        adapter.submitList(result.data)
                        adapter.onFailure(false)
                    }
                    is Result.Failure -> {
                        adapter.onFailure(true)
                        Timber.wtf(result.errorMessage)
                    }
                }
            })
    }

    @SuppressLint("LogNotTimber")
    private fun initRecyclerView() {

        val adapter = ProductsAdapter(appExecutors, this) {

            val bundle = bundleOf("KEY_PRODUCT_ID" to it.id)
            view?.findNavController()?.navigate(R.id.action_ToDetailActivity, bundle)

            Log.d("TAG","KEY_PRODUCT_ID IS : " + bundle)
        }

        this.adapter = adapter
        // fix the bug: Auto scroll to bottom
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.recyclerView.layoutManager?.scrollToPosition(0)
                }
            }
        })

        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        binding.recyclerView.layoutManager = layoutManager

        val scrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {

                favoriteViewModel.loadProducts(currentPage)
            }
        }
        favoriteViewModel.refresh.observe(viewLifecycleOwner, Observer {
            scrollListener.reset()
        })
        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_tiny)
        binding.recyclerView.addItemDecoration(
            GridSpacingItemDecoration(
                SPAN_COUNT,
                spacing,
                true
            )
        )
        binding.recyclerView.addOnScrollListener(scrollListener)
        binding.recyclerView.adapter = adapter
    }

    override fun retry() {
        favoriteViewModel.retry()
        view?.findNavController()?.navigate(R.id.action_to_webViewActivity)
    }

    override fun toWebView() {
        view?.findNavController()?.navigate(R.id.action_to_webViewActivity)
    }


}

