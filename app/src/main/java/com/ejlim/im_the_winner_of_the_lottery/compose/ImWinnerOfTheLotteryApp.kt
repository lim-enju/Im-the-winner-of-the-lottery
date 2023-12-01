package com.ejlim.im_the_winner_of_the_lottery.compose

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ejlim.im_the_winner_of_the_lottery.compose.qr.QRScreen

@Composable
fun ImWinnerOfTheLotteryApp(
    onPageChange: (ImWinnerOfTheLotteryPage) -> Unit = {},
    onAttach: (Toolbar) -> Unit = {}
){
    val navController = rememberNavController()
    ImWinnerOfTheLotteryNavHost(
        navController = navController,
        onPageChange = onPageChange,
        onAttach = onAttach
    )
}

@Composable
fun ImWinnerOfTheLotteryNavHost(
    navController: NavHostController,
    onPageChange: (ImWinnerOfTheLotteryPage) -> Unit = {},
    onAttach: (Toolbar) -> Unit = {}
){
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, "qr"){
        composable("qr"){
            QRScreen(
                onSuccessQrScan = {

                }
            )
        }
    }

}