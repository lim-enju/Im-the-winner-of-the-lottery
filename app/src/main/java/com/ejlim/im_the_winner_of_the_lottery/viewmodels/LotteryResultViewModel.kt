package com.ejlim.im_the_winner_of_the_lottery.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import java.net.URI
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class LotteryResultViewModel @Inject constructor(
   private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _toastMsg = MutableSharedFlow<String>()
    val toastMsg = _toastMsg.asSharedFlow()

    var roundNumber: Int = 0

    var myLotteryNumber: List<List<Int>> = listOf()

    var winnerLotteryNumber: List<Int> = listOf()

    init {
        decodeLotteryQRUrl()
    }

    private fun decodeLotteryQRUrl() {
        val url = savedStateHandle.get<String>(QR_URL_SAVED_STATE_KEY)
        val lotteryUrl = Uri.parse(url)

        //string file로 변환
        val host = kotlin.runCatching { lotteryUrl.host }.getOrNull()
        if (host != "m.dhlottery.co.kr") {
            viewModelScope.launch {
                _toastMsg.emit("lottery url 형식이 아닙니다.")
            }
            return
        }

        //tr정보를 제외하고 파싱
        val query = lotteryUrl.getQueryParameter("v")?.let { vString ->
            if (vString.length > 10)
                vString.substring(0, vString.length - 10)
            else null
        }

        //기준 문자 찾기
        val standardChar = query?.first { !it.isDigit() }
        if (standardChar == null) {
            viewModelScope.launch {
                _toastMsg.emit("정보가 올바르지 않습니다.")
            }
            return
        }

        //회차정보와 번호 정보가 있는지 확인
        val queryString = query.split(standardChar)
        if (queryString.size < 2) {
            viewModelScope.launch {
                _toastMsg.emit("정보가 올바르지 않습니다.")
            }
            return
        }

        //로또번호를 string 에서 int로 변환
        val myNumber = queryString.map { numberString ->
            numberString
                .chunked(2)
                .filter { number -> number != "00" }
                .mapNotNull {
                    it.toIntOrNull()
                }
        }

        //회차정보
        roundNumber = queryString[0].toIntOrNull() ?: 0

        //로또 번호
        myLotteryNumber = myNumber

        //1등 번호
        val winnerIndex = (1 until queryString.size).random()
        winnerLotteryNumber = myNumber[winnerIndex]

        Log.d("EJLIM11", "NUMBER: " +
                "회차 ${queryString[0].toIntOrNull() ?: 0} " +
                "번호 ${myNumber.map { " $it" }}" +
                "1등 번호 ${myNumber[winnerIndex].map { " $it" }}"
        )
    }

    companion object {
        private const val QR_URL_SAVED_STATE_KEY = "qr_url"
    }
}