package co.spirbase.framework.presentation.camerax

import android.view.View
import co.spirbase.databinding.FragmentCameraXBinding
import co.spirbase.framework.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.task.vision.detector.ObjectDetector

@AndroidEntryPoint
class CameraXFragment : BaseFragment<FragmentCameraXBinding, CameraXViewModel>(
    FragmentCameraXBinding::inflate,
    CameraXViewModel::class.java
) {
    var objectDetector: ObjectDetector? = null
    var imageProcessor: ImageProcessor? = null

    override fun init(view: View) {
        setUpCameraX()
        setUpImageProcessor()
    }

    override fun subscribeObserver(view: View) {

    }
}