package com.wa82bj.check_mvvm.ui.main.allProduct

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.wa82bj.check_mvvm.AppExecutors
import com.wa82bj.check_mvvm.R
import com.wa82bj.check_mvvm.data.model.ProductModel
import com.wa82bj.check_mvvm.databinding.ItemCheckLinkBinding
import com.wa82bj.check_mvvm.databinding.ItemRowLeftBinding
import com.wa82bj.check_mvvm.databinding.ItemRowRightBinding
import com.wa82bj.check_mvvm.ui.common.RetryAndWebWiewListener
import com.wa82bj.check_mvvm.ui.common.adapter.DataBoundListCustomAdapter

class ProductsAdapter(

    appExecutors: AppExecutors,
    private val retryAndWebWiewListener: RetryAndWebWiewListener,
    private val callback: ((ProductModel) -> Unit)?

) : DataBoundListCustomAdapter<ProductModel, ViewDataBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }
    }
) {
    private enum class ItemType(val value: Int) {
        MORE(2),
        LAYOUT_RIGHT(3),
        LAYOUT_LEFT(4)
    }

    private var isError = false


    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutId = when (viewType) {

            ItemType.LAYOUT_RIGHT.value -> R.layout.item_row_right

            ItemType.MORE.value -> R.layout.item_check_link

            ItemType.LAYOUT_LEFT.value -> R.layout.item_row_left

            else -> R.layout.item_check_link
        }

        return DataBindingUtil.inflate(
            layoutInflater, layoutId,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: ProductModel) {
        when (binding) {
            is ItemRowRightBinding -> {
                binding.root.setOnClickListener {
                    binding.productsModel?.let { product ->
                        callback?.invoke(product)
                    }
                }
                binding.productsModel = item

                if (item.fav == true){
                    binding.container.setBackgroundResource(R.color.favoritedColor)
                }else{
                    binding.container.setBackgroundResource(R.color.white)
                }


            }
            is ItemCheckLinkBinding -> {
                binding.retry = retryAndWebWiewListener
                binding.isError = isError
            }
        }

        when (binding) {
            is ItemRowLeftBinding -> {
                binding.root.setOnClickListener {
                    binding.productsModel?.let { product ->
                        callback?.invoke(product)
                    }
                }
                binding.productsModel = item
                if (item.fav == true){
                    binding.container.setBackgroundResource((R.color.favoritedColor))
                }else{
                    binding.container.setBackgroundResource(R.color.white)
                }

            }

        }
    }

    fun onFailure(isError: Boolean) {
        this.isError = isError
        notifyItemChanged(itemCount - 1)
    }

    override fun getItemCount() = getCurrentList().size + 1

    override fun getItem(position: Int): ProductModel =
        getCurrentList().getOrElse(position) {
            ProductModel()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemViewType(position: Int): Int {

        val available = getItem(position).available

        if (available){
            return ItemType.LAYOUT_RIGHT.value
        }
        else{
            if (position == itemCount - 1) {
                return ItemType.MORE.value
            } else{
                return  ItemType.LAYOUT_LEFT.value
            }

        }



    }

}