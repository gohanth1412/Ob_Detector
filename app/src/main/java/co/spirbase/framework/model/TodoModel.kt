package co.spirbase.framework.model

data class TodoModel(
    val id: Long,
    val name: String,
    val des: String,
    val isSelected: Boolean = false
)
