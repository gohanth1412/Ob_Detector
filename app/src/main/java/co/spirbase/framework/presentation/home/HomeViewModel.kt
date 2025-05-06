package co.spirbase.framework.presentation.home

import co.spirbase.framework.database.daointerface.TodoDAO
import co.spirbase.framework.database.entities.TodoEntity
import co.spirbase.framework.database.entities.mapToTodoModel
import co.spirbase.framework.model.TodoModel
import co.spirbase.framework.presentation.common.BaseViewModel
import co.spirbase.framework.presentation.common.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val todoDAO: TodoDAO
) : BaseViewModel() {
    init {
        getAllTodo()
    }

    val listTodoStateFlow = MutableStateFlow(emptyList<TodoModel>())

    fun getAllTodo() {
        launchIO {
            todoDAO.getAllTodo().collect { listTodo ->
                listTodoStateFlow.value = listTodo.map { it.mapToTodoModel() }
            }
        }
    }

    fun addNewTodo(todo: TodoModel) {
        launchIO {
            todoDAO.insert(
                TodoEntity(
                    id = todo.id,
                    name = todo.name,
                    des = todo.des,
                    isSelected = todo.isSelected
                )
            )
        }
    }

    fun upDateTodo(todo: TodoModel) {
        launchIO {
            todoDAO.update(
                TodoEntity(
                    id = todo.id,
                    name = todo.name,
                    des = todo.des,
                    isSelected = todo.isSelected
                )
            )
        }
    }
}