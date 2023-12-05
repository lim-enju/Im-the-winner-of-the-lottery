package com.ejlim.im_the_winner_of_the_lottery.compose

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ejlim.im_the_winner_of_the_lottery.compose.qr.QRScreen
import com.ejlim.im_the_winner_of_the_lottery.compose.result.ResultScreen

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
                onQrReadResult = { code ->
                    navController.navigate("result/$code")
                }
            )
        }
        composable(
            "result/{qrCode}",
            arguments = listOf(navArgument("qrCode"){
                type = NavType.StringType
            })
        ){
            ResultScreen()
        }
    }

}