package com.ejlim.im_the_winner_of_the_lottery.compose.result

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.ejlim.im_the_winner_of_the_lottery.viewmodels.LotteryResultViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LotteryResultScreen(
    lotteryResultViewModel: LotteryResultViewModel = hiltViewModel()
){
    val url = lotteryResultViewModel.qrUrl.collectAsState(initial = null)
    Scaffold(
    ) {
        LotteryWebView()
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LotteryWebView() {
    val context = LocalContext.current
    //webview instance가 한번만 생성되고 계속 재사용 됨
    AndroidView(factory = {//뷰 생성
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl("file:///android_asset/QRResult.html")
        }
    }, update = {
        it.loadUrl("file:///android_asset/QRResult.html")
    })
}