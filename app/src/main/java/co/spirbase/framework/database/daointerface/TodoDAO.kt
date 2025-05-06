package co.spirbase.framework.database.daointerface

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import co.spirbase.framework.database.entities.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface TodoDAO {

    @Insert
    fun insert(vararg entities: TodoEntity)

    @Update
    fun update(vararg entities: TodoEntity)

    @Delete
    fun delete(entity: TodoEntity)

    @Query("SELECT * FROM ${TodoEntity.TABLE_NAME} ORDER BY ${TodoEntity.ID} DESC")
    fun getAllTodo(): Flow<List<TodoEntity>>
}