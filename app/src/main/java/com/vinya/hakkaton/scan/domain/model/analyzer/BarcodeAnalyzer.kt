package com.vinya.hakkaton.scan.domain.model.analyzer

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.navigation.NavController
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.vinya.hakkaton.R

class BarcodeAnalyzer(private val navController: NavController) : ImageAnalysis.Analyzer {
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(inputImage)
                .addOnCompleteListener {
                    imageProxy.close()
                    if (it.isSuccessful) {
                        for (barcode in it.result as List<Barcode>) {
                            when (barcode.valueType) {
                                Barcode.TYPE_TEXT -> {
                                    navController.navigate(R.id.faceScanFragment)
                                }
                            }
                        }
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }
}