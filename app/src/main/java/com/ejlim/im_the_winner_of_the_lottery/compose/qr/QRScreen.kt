package com.ejlim.im_the_winner_of_the_lottery.compose.qr

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.content.ContextCompat
import com.ejlim.im_the_winner_of_the_lottery.databinding.QrScreenBinding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRScreen(
    modifier: Modifier = Modifier,
    onSuccessQrScan:() -> Unit,
    onAttached: (Toolbar) -> Unit = {}
) {
    val context = LocalContext.current

    val permisionState = rememberPermissionState(Manifest.permission.CAMERA)

    //permisionState가 바뀔 때 마다 suspend fun을 취소하고 재실행
    LaunchedEffect(permisionState){
       if(!permisionState.status.isGranted){
           permisionState.launchPermissionRequest()
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