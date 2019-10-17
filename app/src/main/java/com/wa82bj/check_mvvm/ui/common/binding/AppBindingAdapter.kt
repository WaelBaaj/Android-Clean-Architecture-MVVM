package com.wa82bj.check_mvvm.ui.common.binding

import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RatingBar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.wa82bj.check_mvvm.R

object AppBindingAdapter {

    @JvmStatic
    @BindingAdapter("image")
    fun setUrl(imageView: ImageView, imageUrl: String?) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun changeVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("ratingValue")
    fun setRating(ratingBar: RatingBar, mVoteAverage: Double){
        ratingBar.rating = mVoteAverage.toFloat()
        val stars = ratingBar.progressDrawable as LayerDrawable
        stars.getDrawable(2).setColorFilter(
            ContextCompat.getColor(ratingBar.context, R.color.yellow)
            , PorterDuff.Mode.SRC_ATOP)
        val roundVal = Math.round(mVoteAverage)
        ratingBar.numStars = roundVal.toInt()
    }

    @JvmStatic
    @BindingAdapter("app:displayUrl")
    fun displayUrl(
        webView: WebView,
        url: String?
    ) {
        url?.let { webView.loadUrl(it) }
    }

}