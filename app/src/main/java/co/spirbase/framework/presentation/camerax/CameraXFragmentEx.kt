package co.spirbase.framework.presentation.camerax

import android.util.Log
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.task.vision.detector.ObjectDetector

fun CameraXFragment.setUpCameraX() {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder()
            .build().also {
                it.surfaceProvider = binding.previewView.surfaceProvider
            }

        val imageAnalysis = ImageAnalysis.Builder()
            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext())) { imageProxy ->
            processImageProxy(imageProxy)
        }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )
        } catch (exc: Exception) {
            Log.e("CameraX", "Use case binding failed", exc)
        }

    }, ContextCompat.getMainExecutor(requireContext()))
}

fun CameraXFragment.processImageProxy(imageProxy: ImageProxy) {

    if (imageProcessor == null) {
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        imageProcessor = ImageProcessor.Builder()
            .add(Rot90Op(-rotationDegrees / 90))
            .build()
    }

    val image = imageProxy.toBitmap()

    var tensorImage = TensorImage.fromBitmap(image)

    tensorImage = imageProcessor?.process(tensorImage)
    val results = objectDetector?.detect(tensorImage)

    results?.let {
        binding.detectorView.setListDetection(it, tensorImage.width, tensorImage.height)
    }

    imageProxy.close()
}

fun CameraXFragment.setUpImageProcessor() {
    objectDetector = ObjectDetector.createFromFileAndOptions(
        context,
        "mobilenetv1.tflite",
        ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(1)
            .setScoreThreshold(0.5f)
            .build()
    )
}