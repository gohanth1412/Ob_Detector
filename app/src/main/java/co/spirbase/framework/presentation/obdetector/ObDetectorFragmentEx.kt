package co.spirbase.framework.presentation.obdetector

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.YuvImage
import android.media.Image
import android.util.Log
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import co.spirbase.util.setPreventDoubleClickScaleView
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.frame.Frame
import com.otaliastudios.cameraview.frame.FrameProcessor
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import java.io.ByteArrayOutputStream


fun ObDetectorFragment.backEvent() {
    activity?.onBackPressedDispatcher?.addCallback(this, true) {
        onBackPressed()
    }
    binding.ivBack.setPreventDoubleClickScaleView {
        onBackPressed()
    }
}

fun ObDetectorFragment.onBackPressed() {
    findNavController().navigateUp()
}

fun ObDetectorFragment.setUpImageProcessor() {
    objectDetector = ObjectDetector.createFromFileAndOptions(
        context,
        "obdetect.tflite",
        ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5)
            .setScoreThreshold(0.5f)
            .build()
    )
}

fun ObDetectorFragment.setUpCamera() {
    binding.cameraView.apply {
        setLifecycleOwner(viewLifecycleOwner)
        frameProcessingFormat = ImageFormat.NV21
        addCameraListener(object : CameraListener() {})
        addFrameProcessor { frame ->
            if (frame.dataClass == ByteArray::class.java) {
                val data = frame.getData<ByteArray>()
                processFrame(data, frame.size.width, frame.size.height, frame.rotationToUser)
            }
        }
    }
}

private fun ObDetectorFragment.processFrame(data: ByteArray, width: Int, height: Int, imageRotation: Int) {
    val bitmap = nv21ToBitmap(data, width, height)
    var tensorImage = TensorImage.fromBitmap(bitmap)

    imageProcessor = ImageProcessor.Builder()
        .add(Rot90Op(-imageRotation / 90))
        .build()

    tensorImage = imageProcessor?.process(tensorImage)
    val results = objectDetector?.detect(tensorImage)

    results?.let {
        val listBox = it.map { detection -> detection.boundingBox }.toMutableList()
        for (detection in it) {
            val category = detection.categories.firstOrNull()?.label ?: "Unknown"
            val score = detection.categories.firstOrNull()?.score ?: 0f
            Log.d("CHECKDETECTOR", "category: $category, score: $score")
        }
        binding.detectorView.setListBox(listBox, height, width)
    }
}

private fun nv21ToBitmap(nv21: ByteArray, width: Int, height: Int): Bitmap {
    val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}