package com.vinya.hakkaton.scan.domain.analyzer

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer : ImageAnalysis.Analyzer {
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
                                    Log.d("MainTag", "analyze: ${barcode.displayValue.toString()}")
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