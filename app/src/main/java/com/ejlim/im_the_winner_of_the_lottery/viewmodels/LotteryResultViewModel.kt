package com.ejlim.im_the_winner_of_the_lottery.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import java.net.URI
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class LotteryResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _toastMsg = MutableSharedFlow<String>()
    val toastMsg = _toastMsg.asSharedFlow()

    val qrUrl =
        savedStateHandle
            .getStateFlow(QR_URL_SAVED_STATE_KEY, "")
            .transform { url ->
                Log.d("EJLIM", "LotteryResultViewModel: $url")

                val lotteryUrl = Uri.parse(url)

                //string file로 변환
                val host = kotlin.runCatching { lotteryUrl.host }.getOrNull()
                if(host != "m.dhlottery.co.kr"){
                    _toastMsg.emit("lottery url 형식이 아닙니다.")
                    return@transform
                }

                //선택한 번호
                val query = lotteryUrl.getQueryParameter("v")
                val queryString = query?.split("q")

                //회차정보와 번호 정보가 있는지 확인
                if(queryString == null || queryString.size < 2) {
                    _toastMsg.emit("정보가 올바르지 않습니다.")
                    return@transform
                }
                emit(queryString)
            }

    companion object {
        private const val QR_URL_SAVED_STATE_KEY = "qr_url"
    }
}