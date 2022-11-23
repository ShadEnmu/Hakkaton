package com.vinya.hakkaton.scan.presentation.ui

import com.vinya.hakkaton.R
import com.vinya.hakkaton.core.presentation.BaseFragment

class ScanBarcodeFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_barcode
    companion object{
        @JvmStatic
        fun newInstance(): ScanBarcodeFragment {
            return ScanBarcodeFragment()
        }
    }
}