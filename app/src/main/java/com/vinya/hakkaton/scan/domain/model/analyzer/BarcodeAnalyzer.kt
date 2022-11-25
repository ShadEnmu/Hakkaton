package com.vinya.hakkaton.scan.domain.model.analyzer

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
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
                                Barcode.TYPE_CONTACT_INFO -> {
                                    val contactInfo: ArrayList<String> = ArrayList()
                                    contactInfo.add(barcode.contactInfo?.name?.first.toString())
                                    contactInfo.add(barcode.contactInfo?.name?.last.toString())
                                    if (barcode.contactInfo?.name?.last.toString() + " " + barcode.contactInfo?.name?.first.toString() in listOf<String>(
                                            "Черных Вадим",
                                            "Калашников Никита",
                                            "Хангалова Юлия",
                                            "Болотов Аюр"
                                        )
                                    ) {
                                        contactInfo.add(barcode.contactInfo?.organization.toString())
                                        val bundle = Bundle()
                                        bundle.putStringArrayList("ContactInfo", contactInfo)
                                        Handler().postDelayed({
                                            navController.navigate(R.id.barcodeSuccessFragment, bundle)
                                        }, 700)
                                    } else {
                                        contactInfo.add(barcode.contactInfo?.organization.toString())
                                        val bundle = Bundle()
                                        bundle.putStringArrayList("ContactInfo", contactInfo)
                                        Handler().postDelayed({
                                            navController.navigate(R.id.profileDetailsFragment, bundle)
                                        }, 700)

                                    }
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