package com.ejlim.im_the_winner_of_the_lottery.compose.qr

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.ejlim.im_the_winner_of_the_lottery.databinding.QrScreenBinding

@Composable
fun QRScreen(
    modifier: Modifier = Modifier,
    onSuccessQrScan:() -> Unit,
    onAttached: (Toolbar) -> Unit = {}
) {
    val activity = (LocalContext.current as AppCompatActivity)
    AndroidViewBinding(QrScreenBinding::inflate, modifier = modifier) {
        composeView.setContent {
            QrPagerScreen()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QrPagerScreen(

){
    Column {
        Text(text = "qr")
    }
}