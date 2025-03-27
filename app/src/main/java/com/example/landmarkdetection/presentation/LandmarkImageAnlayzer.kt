package com.example.landmarkdetetction.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.landmarkdetection.domain.Classification
import com.example.landmarkdetection.domain.LandMarkClassifier
import com.example.landmarkdetection.presentation.centerCrop

class LandmarkImageAnalayzer(
    private val classifier: LandMarkClassifier,
    private val onResult:(List<Classification>)->Unit
):ImageAnalysis.Analyzer {
    var skipDrameCounter=0
    override fun analyze(image: ImageProxy) {
        if(skipDrameCounter%60==0) {

            val rotation = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(321, 321)

            val results = classifier.classify(bitmap, rotation)
            onResult(results)
        }
        skipDrameCounter++
        image.close()
    }
}