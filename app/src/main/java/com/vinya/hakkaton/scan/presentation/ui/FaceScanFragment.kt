package com.vinya.hakkaton.scan.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.google.common.util.concurrent.ListenableFuture
import com.vinya.hakkaton.R
import com.vinya.hakkaton.core.presentation.BaseFragment
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FaceScanFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_face
    private lateinit var cameraProvider: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var previewView: PreviewView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraProvider = ProcessCameraProvider.getInstance(requireContext())
        previewView = view.findViewById(R.id.previewView)
        cameraProvider.addListener({
            val _cameraProvider = cameraProvider.get()
            val preview: Preview = Preview.Builder()
                .build()
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            preview.setSurfaceProvider(previewView.surfaceProvider)
            _cameraProvider.bindToLifecycle(
                this as LifecycleOwner,
                cameraSelector,
                preview
            )
        }, ContextCompat.getMainExecutor(requireContext()))

        Handler().postDelayed({
            val bundle = Bundle()
            bundle.putAll(arguments)
            findNavController().navigate(R.id.profileDetailsFragment, bundle)
        }, 3000)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FaceScanFragment()
    }
}