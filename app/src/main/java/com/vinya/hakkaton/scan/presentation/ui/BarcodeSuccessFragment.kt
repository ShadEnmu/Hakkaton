package com.vinya.hakkaton.scan.presentation.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import androidx.navigation.fragment.findNavController
import com.vinya.hakkaton.R
import com.vinya.hakkaton.core.presentation.BaseFragment

class BarcodeSuccessFragment: BaseFragment() {
    override val layoutId: Int = R.layout.fragment_barcode_success

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        bundle.putAll(arguments)
        Handler().postDelayed({
            findNavController().navigate(R.id.faceScanFragment, bundle)
        }, 2000)
    }
}