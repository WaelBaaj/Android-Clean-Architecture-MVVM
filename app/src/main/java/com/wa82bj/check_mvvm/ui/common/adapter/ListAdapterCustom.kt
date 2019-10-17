package com.wa82bj.check_mvvm.ui.common.adapter

import androidx.recyclerview.widget.*

@Suppress("LeakingThis")
abstract class ListAdapterCustom<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {
    private val helper: AsyncListDiffer<T>

    protected constructor(diffCallback: DiffUtil.ItemCallback<T>) {
        this.helper = AsyncListDiffer(AdapterListUpdateCallback(this), AsyncDifferConfig.Builder(diffCallback).build())
    }

    protected constructor(config: AsyncDifferConfig<T>) {
        this.helper = AsyncListDiffer(AdapterListUpdateCallback(this), config)
    }

    fun submitList(list: List<T>?) {
        this.helper.submitList(list)
    }

    open fun getItem(position: Int): T =
        this.helper.currentList[position]

    override fun getItemCount(): Int =
        this.helper.currentList.size

    fun getCurrentList(): MutableList<T> = this.helper.currentList
}