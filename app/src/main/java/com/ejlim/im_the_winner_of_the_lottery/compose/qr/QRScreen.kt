package com.ejlim.im_the_winner_of_the_lottery.compose.qr

import android.Manifest
import android.util.Size
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.content.ContextCompat
import com.ejlim.im_the_winner_of_the_lottery.databinding.QrScreenBinding
import com.ejlim.im_the_winner_of_the_lottery.util.QRCode
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Math.sqrt
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun QRScreen(
    modifier: Modifier = Modifier,
    onQrReadResult:(String) -> Unit,
    onAttached: (Toolbar) -> Unit = {}
) {
    AndroidViewBinding(factory = QrScreenBinding::inflate, modifier = modifier){
        composeView.setContent{
            QrPagerScreen(onQrReadResult)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrPagerScreen(
    onQrReadResult:(String) -> Unit,
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    //권한 요청
    val permisionState = rememberPermissionState(Manifest.permission.CAMERA)

    //permisionState가 바뀔 때 마다 suspend fun을 취소하고 재실행
    LaunchedEffect(permisionState){
        if(!permisionState.status.isGranted){
            permisionState.launchPermissionRequest()
        }
    }

    //인식 된 QR 코드
    var code by remember { mutableStateOf("") }
    LaunchedEffect(code){
        if(code.isNotBlank()){
            val url = withContext(Dispatchers.IO) {
                URLEncoder.encode(code, StandardCharsets.UTF_8.toString())
            }
            onQrReadResult(url)
        }
    }

    val cameraProviderFeature = remember {
        ProcessCameraProvider.getInstance(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                val preview = Preview.Builder().build()
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(
                        Size(
                            previewView.width,
                            previewView.height
                        )
                    )
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    QRCode { result ->
                        code = result
                    }
                )
                try {
                    cameraProviderFeature.get().bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                previewView
            },
        )

        Divider(
            modifier = Modifier
                .align(Alignment.Center)
                .width(30.dp)
                .height(1.dp),
            color = Color.Green,
        )
        Divider(
            modifier = Modifier
                .align(Alignment.Center)
                .width(1.dp)
                .height(30.dp),
            color = Color.Green,
        )
        Column (
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                modifier = Modifier.padding(2.dp),
                text = "QR/바코드",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            TriangleShape(
                color = Color.White
            )
        }
    }
}

@Composable
fun TriangleShape(color: Color = Color.White) {
    Canvas(modifier = Modifier.size(20.dp),) {
        val path = Path().apply {
            val triangleSide = size.width
            val height = triangleSide * (sqrt(3.0) / 2.0).toFloat()
            moveTo(triangleSide / 2f, 0f)
            lineTo(0f, height)
            lineTo(triangleSide, height)
            close()
        }
        drawPath(path, color)
    }
}