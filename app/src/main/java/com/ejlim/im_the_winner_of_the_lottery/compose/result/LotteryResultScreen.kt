package com.ejlim.im_the_winner_of_the_lottery.compose.result

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ejlim.im_the_winner_of_the_lottery.viewmodels.LotteryResultViewModel

@Composable
fun LotteryResultScreen(
    lotteryResultViewModel: LotteryResultViewModel = hiltViewModel()
){
    val url = lotteryResultViewModel.qrUrl.collectAsState(initial = null)

    Column(
        modifier = Modifier
    ){
        Text( text = "result")
    }
}