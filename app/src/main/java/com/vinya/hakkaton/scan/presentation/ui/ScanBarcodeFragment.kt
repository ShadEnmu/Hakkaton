package com.vinya.hakkaton.scan.presentation.ui

import android.os.Bundle
import android.util.Size
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.google.common.util.concurrent.ListenableFuture
import com.vinya.hakkaton.R
import com.vinya.hakkaton.core.presentation.BaseFragment
import com.vinya.hakkaton.scan.domain.model.analyzer.BarcodeAnalyzer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanBarcodeFragment :
    BaseFragment() {
    override val layoutId: Int = R.layout.fragment_barcode
    private lateinit var cameraProvider: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var previewView: PreviewView
    private lateinit var analyzer: BarcodeAnalyzer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraProvider = ProcessCameraProvider.getInstance(requireContext())
        previewView = view.findViewById(R.id.previewView)
        analyzer = BarcodeAnalyzer(findNavController())
        cameraProvider.addListener({
            val _cameraProvider = cameraProvider.get()
            val preview: Preview = Preview.Builder()
                .build()
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1240, 680))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            preview.setSurfaceProvider(previewView.surfaceProvider)
            imageAnalysis.setAnalyzer(cameraExecutor, analyzer)
            _cameraProvider.bindToLifecycle(
                this as LifecycleOwner,
                cameraSelector,
                imageAnalysis,
                preview
            )
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    companion object {
        @JvmStatic
        fun newInstance(): ScanBarcodeFragment {
            return ScanBarcodeFragment()
        }
    }
}