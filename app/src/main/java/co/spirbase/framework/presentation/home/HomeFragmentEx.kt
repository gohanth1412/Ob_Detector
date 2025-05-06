package co.spirbase.framework.presentation.home

import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import co.spirbase.R
import co.spirbase.framework.model.TodoModel
import co.spirbase.util.displayToast
import co.spirbase.util.setPreventDoubleClick
import co.spirbase.util.showAddTodoDialog
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback

fun HomeFragment.backEvent() {
    activity?.onBackPressedDispatcher?.addCallback(this, true) {
        onBackPressed()
    }
}

fun HomeFragment.onBackPressed() {
    findNavController().popBackStack()
}

fun HomeFragment.setUpAdapter() {
    todoAdapter.setListener(this)
    binding.rcv.adapter = todoAdapter
    binding.rcv.itemAnimator = null
}

fun HomeFragment.addNewTodoEvent() {
    binding.tvAdd.setPreventDoubleClick {
        requireContext().showAddTodoDialog(lifecycle = lifecycle, onAdd = { s: String, s1: String ->
            viewModel.addNewTodo(
                TodoModel(
                    id = System.currentTimeMillis(),
                    name = s,
                    des = s1,
                    isSelected = false
                )
            )
            viewModel.getAllTodo()
        })
    }
}

fun HomeFragment.openCameraEvent() {
    binding.tvOpenCamera.setPreventDoubleClick {
        PermissionX.init(this)
            .permissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            )
            .request { allGranted, _, _ ->
                if (allGranted) {
                    safeNav(R.id.homeFragment, R.id.action_homeFragment_to_obDetectorFragment)
                } else {
                    displayToast("Need permission")
                }
            }
    }
}