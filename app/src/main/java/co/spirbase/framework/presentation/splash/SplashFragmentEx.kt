package co.spirbase.framework.presentation.splash

import androidx.activity.addCallback
import co.spirbase.R

fun SplashFragment.backEvent() {
    activity?.onBackPressedDispatcher?.addCallback(this, true) {
        onBackPressed()
    }
//    binding.btnBack.setPreventDoubleClickScaleView {
//        onBackPressed()
//    }
}

fun SplashFragment.onBackPressed() {
//    findNavController().popBackStack()
}

fun SplashFragment.startAnimation() {
    binding.loadingView.startAnim(2000L) {
        safeNav(R.id.splashFragment, R.id.action_splashFragment_to_homeFragment)
    }
}