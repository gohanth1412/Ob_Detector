package co.spirbase.framework.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

typealias Inflate<Binding> = (LayoutInflater, ViewGroup?, Boolean) -> Binding

abstract class BaseFragment<Binding : ViewBinding, VM : ViewModel>(
    private val inflate: Inflate<Binding>,
    private val viewModelClass: Class<VM>
) : Fragment() {

    lateinit var navController: NavController

    private var _binding: Binding? = null

    val binding: Binding
        get() = checkNotNull(_binding) {
            "Fragment $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }

    val commonViewModel: CommonViewModel by activityViewModels()

    val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    private var isInit = false
    private var saveView = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (saveView) {
            if (_binding == null) {
                isInit = true
                _binding = inflate.invoke(inflater, container, false)
            } else {
                isInit = false
            }
        } else {
            _binding = inflate.invoke(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        navController.addOnDestinationChangedListener { _, _, _ ->

        }
        init(view)
        subscribeObserver(view)
    }

    abstract fun init(view: View)

    abstract fun subscribeObserver(view: View)

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun safeNav(currentDestination: Int, action: Int, bundle: Bundle? = null) {
        if (navController.currentDestination?.id == currentDestination) {
            doActionWhenResume {
                try {
                    navController.navigate(action, bundle)
                } catch (e: IllegalArgumentException) {

                }
            }
        }
    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}

fun Fragment.doActionWhenResume(action: () -> Unit) {
    lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_RESUME) {
                action.invoke()
                lifecycle.removeObserver(this)
            }
        }
    })
}

fun Fragment.launchIO(
    exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

    },
    block: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch(Dispatchers.IO + exceptionHandler, block = block)

fun Fragment.launchDefault(
    exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

    },
    block: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch(Dispatchers.Default + exceptionHandler, block = block)

fun Fragment.launchMain(
    exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

    },
    block: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch(Dispatchers.Main + exceptionHandler, block = block)