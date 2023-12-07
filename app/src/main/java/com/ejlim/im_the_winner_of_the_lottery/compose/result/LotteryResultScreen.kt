package com.ejlim.im_the_winner_of_the_lottery.compose.result

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.ejlim.im_the_winner_of_the_lottery.viewmodels.LotteryResultViewModel
import com.google.gson.Gson

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LotteryResultScreen(
    lotteryResultViewModel: LotteryResultViewModel = hiltViewModel()
){
    Scaffold(
    ) {
        LotteryWebView(
            lotteryResultViewModel.roundNumber,
            lotteryResultViewModel.myLotteryNumber,
            lotteryResultViewModel.winnerLotteryNumber
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LotteryWebView(
    roundNumber: Int,
    myLotteryNumber: List<List<Int>>,
    winnerLotteryNumber: List<Int>
) {
    val context = LocalContext.current
    //webview instance가 한번만 생성되고 계속 재사용 됨
    AndroidView(factory = {//뷰 생성
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
                    Log.d(
                        "WebView",
                        "${cm.message()} -- From line ${cm.lineNumber()} of ${cm.sourceId()}"
                    )
                    return true
                }
            }
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            addJavascriptInterface(LotteryNumberJavascriptInterface(
                roundNumber,
                myLotteryNumber,
                winnerLotteryNumber
            ), "LotteryAndroid")
            loadUrl("file:///android_asset/QRResult.html")
        }
    })
}

class LotteryNumberJavascriptInterface(
    val roundNumber: Int,
    val allLotteryNumber: List<List<Int>>,
    val winnerLotteryNumber: List<Int>
){
    @JavascriptInterface
    public fun getRoundNumberFromAndroid(): Int
        = roundNumber

    @JavascriptInterface
    public fun getWinnerNumberFromAndroid(): String {
        Log.d("EJLIM11", "getWinnerNumberFromAndroid: ${winnerLotteryNumber.map { " $it" }}")
         return Gson().toJson(winnerLotteryNumber)
    }
    @JavascriptInterface
    public fun getAllNumberFromAndroid(): String{
        return Gson().toJson(allLotteryNumber)
    }
}