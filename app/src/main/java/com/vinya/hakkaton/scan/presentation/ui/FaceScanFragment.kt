package com.vinya.hakkaton.scan.presentation.ui

import android.os.Bundle
import android.view.View
import com.vinya.hakkaton.R
import com.vinya.hakkaton.core.presentation.BaseFragment

class FaceScanFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_face
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() = FaceScanFragment()
    }
}