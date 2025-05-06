package co.spirbase.framework.presentation.splash

import android.view.View
import co.spirbase.databinding.FragmentSplashBinding
import co.spirbase.framework.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate,
    SplashViewModel::class.java
) {
    override fun init(view: View) {
        backEvent()
        startAnimation()
    }

    override fun subscribeObserver(view: View) {

    }
}