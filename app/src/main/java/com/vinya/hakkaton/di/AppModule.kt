package com.vinya.hakkaton.di

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePreview(): Preview = Preview.Builder().build()

    @Provides
    @Singleton
    fun provideCameraSelector(): CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    @Provides
    @Singleton
    fun provideImageAnalysis(): ImageAnalysis = ImageAnalysis.Builder()
        .setTargetResolution(Size(1980, 1024))
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
}