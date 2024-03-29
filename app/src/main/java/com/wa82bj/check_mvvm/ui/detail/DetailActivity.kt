package com.wa82bj.check_mvvm.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.wa82bj.check_mvvm.R
import com.wa82bj.check_mvvm.databinding.ActivityProductDetailBinding
import com.wa82bj.check_mvvm.ui.main.MainActivity
import com.wa82bj.check_mvvm.ui.webview.WebViewActiviy
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class DetailActivity : DaggerAppCompatActivity() {

    private val TAG = DetailActivity::class.java.name
    private lateinit var binding: ActivityProductDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding.detailModel = viewModel


        val photoId = intent?.extras?.getInt(KEY_PRODUCT_ID) ?: return
        viewModel.getDetail(photoId)

        viewModel.productData.observe(this, Observer {
            binding.detailTitleTextView.text = it?.longDescription
            binding.detailShortTextView.text = it?.description
            binding.productName.text = it?.name
            binding.productDate.text = it?.releaseDate.toString()
            binding.productPrice.text = it?.price?.value.toString()
            binding.productCurrency.text = it?.price?.currency
            binding.ratingBar.rating = it.rating.toFloat()
            binding.detailToolbarImageView.loadImageFull(it?.imageURL)

            if (it.fav == true){
                viewModel.checkFavoriteStatus(1,photoId)
                binding.lottieLike.visibility = VISIBLE
                binding.favoriteFab.text = "UnFavorite"
            }else{
                viewModel.checkFavoriteStatus(0,photoId)
                binding.lottieLike.visibility = GONE
                binding.favoriteFab.text = "Favorite"
            }
        })

        viewModel.isFavorite.observe(this, Observer {
            it?.let {

            }
        })

        binding.favoriteFab.setOnClickListener {


            if (binding.favoriteFab.text == "Favorite"){
                binding.favoriteFab.text = "UnFavorite"
                binding.lottieLike.visibility = VISIBLE
                viewModel.updateFavoriteStatus()
            }else{
                binding.favoriteFab.text = "Favorite"
                binding.lottieLike.visibility = GONE
                viewModel.updateFavoriteStatus()
            }

        }

        binding.linkTxt.setOnClickListener {

            val intent = Intent(this, WebViewActiviy::class.java)
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val KEY_PRODUCT_ID = "KEY_PRODUCT_ID"
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

    }

}