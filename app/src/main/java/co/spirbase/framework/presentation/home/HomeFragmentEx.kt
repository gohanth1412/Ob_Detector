package co.spirbase.framework.presentation.home

import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import co.spirbase.framework.model.TodoModel
import co.spirbase.util.setPreventDoubleClick
import co.spirbase.util.showAddTodoDialog

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