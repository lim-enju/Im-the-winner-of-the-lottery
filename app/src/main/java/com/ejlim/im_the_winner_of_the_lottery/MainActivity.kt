package com.ejlim.im_the_winner_of_the_lottery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ejlim.im_the_winner_of_the_lottery.compose.ImWinnerOfTheLotteryApp
import com.ejlim.im_the_winner_of_the_lottery.ui.theme.ImthewinnerofthelotteryTheme
import com.ejlim.im_the_winner_of_the_lottery.viewmodels.QRViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: QRViewModel by viewModels()

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
