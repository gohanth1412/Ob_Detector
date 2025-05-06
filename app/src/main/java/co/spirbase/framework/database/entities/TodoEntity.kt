package co.spirbase.framework.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.spirbase.framework.database.entities.TodoEntity.Companion.TABLE_NAME
import co.spirbase.framework.model.TodoModel
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class TodoEntity(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = NAME)
    val name: String,

    @ColumnInfo(name = DES)
    val des: String,

    @ColumnInfo(name = ISSELECTED)
    val isSelected: Boolean
) : Parcelable {
    companion object {
        const val TABLE_NAME = "TABLE_TODO"
        const val ID = "ID"
        const val NAME = "NAME"
        const val DES = "DES"
        const val ISSELECTED = "ISSELECTED"
    }
}

fun TodoEntity.mapToTodoModel(): TodoModel {
    return TodoModel(id = this.id, name = this.name, des = this.des, isSelected = this.isSelected)
}