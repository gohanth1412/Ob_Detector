package co.spirbase.framework.presentation.home

import android.view.View
import co.spirbase.databinding.FragmentHomeBinding
import co.spirbase.framework.model.TodoModel
import co.spirbase.framework.presentation.common.BaseFragment
import co.spirbase.framework.presentation.home.adapter.TodoAdapter
import co.spirbase.util.collectFlowOnView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
), TodoAdapter.Listener {
    val todoAdapter = TodoAdapter()

    override fun init(view: View) {
        backEvent()
        setUpAdapter()
        addNewTodoEvent()
    }

    override fun subscribeObserver(view: View) {
        viewModel.listTodoStateFlow.collectFlowOnView(viewLifecycleOwner) {
            todoAdapter.submitList(it)
        }
    }

    override fun onTodoClick(item: TodoModel) {

    }

    override fun onCheckClick(item: TodoModel) {
        viewModel.upDateTodo(item.copy(isSelected = !item.isSelected))
    }
}