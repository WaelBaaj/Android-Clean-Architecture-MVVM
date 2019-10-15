package com.wa82bj.check24_mvvm.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wa82bj.check24_mvvm.R
import com.wa82bj.check24_mvvm.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityWebViewBinding>(
            this,
            R.layout.activity_web_view
        )

        init(binding)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(binding: ActivityWebViewBinding) {
        val webSiteUrl = EXTRA_URL

        binding.apply {
            webView.settings.javaScriptEnabled = true

            url = webSiteUrl
            toolbarTitle = webSiteUrl
            setToolbarHomeNavClickListener {
                onBackPressed()
            }
        }
    }

    companion object {
        const val EXTRA_URL = "https://m.check24.de/rechtliche-hinweise/?deviceoutput=app"
    }
}