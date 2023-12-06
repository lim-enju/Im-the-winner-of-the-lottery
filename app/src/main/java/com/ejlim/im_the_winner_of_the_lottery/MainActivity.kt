package com.ejlim.im_the_winner_of_the_lottery

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ejlim.im_the_winner_of_the_lottery.compose.ImWinnerOfTheLotteryApp
import com.ejlim.im_the_winner_of_the_lottery.ui.theme.ImthewinnerofthelotteryTheme
import com.ejlim.im_the_winner_of_the_lottery.viewmodels.LotteryResultViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImthewinnerofthelotteryTheme {
                // A surface container using the 'background' color from the theme
               ImWinnerOfTheLotteryApp()
            }
        }
    }
}
