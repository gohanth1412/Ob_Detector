package co.spirbase.framework.presentation.obdetector

import android.view.View
import co.spirbase.databinding.FragmentObDetectorBinding
import co.spirbase.framework.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.task.vision.detector.ObjectDetector

@AndroidEntryPoint
class ObDetectorFragment : BaseFragment<FragmentObDetectorBinding, ObDetectorViewModel>(
    FragmentObDetectorBinding::inflate,
    ObDetectorViewModel::class.java
) {
    var objectDetector: ObjectDetector? = null
    var imageProcessor: ImageProcessor? = null

    override fun init(view: View) {
        backEvent()
        setUpImageProcessor()
        setUpCamera()
    }

    override fun subscribeObserver(view: View) {

    }
}