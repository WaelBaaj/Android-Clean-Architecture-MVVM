package com.wa82bj.check_mvvm.ui.common.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * this is just a copy-paste class.
 * reference :
 * https://gist.github.com/pratikbutani/dc6b963aa12200b3ad88aecd0d103872
 */

abstract class EndlessScrollListener(
    private val gridLayoutManager: GridLayoutManager
) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 4
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    // the list has more item which always showed
    // so I changed the `current page` in `EndlessScrollListener` to 0
    private var currentPage = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = gridLayoutManager.itemCount
        firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
            <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            currentPage++
            onLoadMore(currentPage)
            // Do something

            loading = true
        }
    }

    fun reset() {
        previousTotal = 0
        // the list has more item which always showed
        // so I changed the `current page` in `EndlessScrollListener` to 0
        currentPage = 0
    }

    abstract fun onLoadMore(currentPage: Int)
}