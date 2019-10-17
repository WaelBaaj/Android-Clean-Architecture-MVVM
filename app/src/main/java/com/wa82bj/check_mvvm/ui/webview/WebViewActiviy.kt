package com.wa82bj.check_mvvm.ui.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.wa82bj.check_mvvm.R
import com.wa82bj.check_mvvm.databinding.ActivityWebViewActiviyBinding

class WebViewActiviy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityWebViewActiviyBinding>(
            this,
            R.layout.activity_web_view_activiy
        )

        init(binding)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(binding: ActivityWebViewActiviyBinding) {
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
