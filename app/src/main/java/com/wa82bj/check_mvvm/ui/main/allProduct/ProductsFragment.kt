package com.wa82bj.check24_mvvm.ui.main.allProduct

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wa82bj.check24_mvvm.AppExecutors
import com.wa82bj.check24_mvvm.R
import com.wa82bj.check24_mvvm.autoCleared
import com.wa82bj.check24_mvvm.data.model.ProductModel
import com.wa82bj.check24_mvvm.databinding.ProductsFragmentBinding
import com.wa82bj.check24_mvvm.ui.common.Result
import com.wa82bj.check24_mvvm.ui.common.RetryAndWebWiewListener
import com.wa82bj.check24_mvvm.ui.common.adapter.EndlessScrollListener
import com.wa82bj.check24_mvvm.ui.main.MainActivity
import com.wa82bj.check24_mvvm.vmFactory.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.products_fragment.*
import timber.log.Timber
import javax.inject.Inject


class ProductsFragment : DaggerFragment(), RetryAndWebWiewListener {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var appExecutors: AppExecutors
    private var adapter by autoCleared<ProductsAdapter>()
    private lateinit var binding: ProductsFragmentBinding
    private lateinit var loadingType: LiveData<Result<List<ProductModel>>>


    private val productViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ProductsViewModel::class.java)
    }

    companion object {
        private const val SPAN_COUNT = 1

        fun newInstance(): ProductsFragment = ProductsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ProductsFragmentBinding.inflate(layoutInflater, container, false)
        binding.productsModel = productViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        if (adapter.itemCount == 0){
            loadingType = productViewModel.product
            loadProducts()
        }else{
            loadingType = productViewModel.productDB
            loadProducts()
        }

        loadHeader()

        productViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading.let {
                adapter.onFailure(!it)
                loadHeader()
            }
        })

        binding.reloadBtn.setOnClickListener(View.OnClickListener {


            startActivity(Intent(context, MainActivity::class.java))
            getActivity()!!.finish()
        })
    }

    private fun loadHeader() {
        productViewModel.header.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {

                    header_title.text = result.data.headerTitle
                    header_desc.text = result.data.headerDescription

                }
                is Result.Failure -> {
                    Timber.wtf(result.errorMessage)
                }
            }
        })
    }

    private fun loadProducts() {
        loadingType.observe(viewLifecycleOwner, Observer { result ->
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
                // We may not call productViewModel.loadProducts(1), this func will be called because
                // the list has more item which always showed
                // so I changed the `current page` in `EndlessScrollListener` to 0
                productViewModel.loadProducts(currentPage)
            }
        }
        productViewModel.refresh.observe(viewLifecycleOwner, Observer {
            scrollListener.reset()

        })

        binding.recyclerView.addOnScrollListener(scrollListener)
        binding.recyclerView.adapter = adapter
    }

    override fun retry() {
        productViewModel.retry()

    }

    override fun toWebView() {
        view?.findNavController()?.navigate(R.id.action_to_webViewActivity)
    }


}
