package com.vinya.hakkaton.scan.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.vinya.hakkaton.R
import com.vinya.hakkaton.scan.domain.analyzer.BarcodeAnalyzer
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var cameraProvider: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var previewView: PreviewView
    private val analyzer : BarcodeAnalyzer = BarcodeAnalyzer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: Переделать запрос на доступ к камере   
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Intent().also {
                it.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                it.data = Uri.fromParts("package", packageName, null)
                startActivity(it)
                finish()
            }
        }

        // TODO: Вынести всё это в модули и отдельные классы 
        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraProvider = ProcessCameraProvider.getInstance(this)
        previewView = findViewById(R.id.previewView)
        cameraProvider.addListener(Runnable {
            val _cameraProvider = cameraProvider.get()
            val preview: Preview = Preview.Builder()
                .build()
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1980, 1024))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            preview.setSurfaceProvider(previewView.createSurfaceProvider(null))
            imageAnalysis.setAnalyzer(cameraExecutor, analyzer)
            _cameraProvider.bindToLifecycle(
                this as LifecycleOwner,
                cameraSelector,
                imageAnalysis,
                preview
            )
        }, ContextCompat.getMainExecutor(this))
    }
}
