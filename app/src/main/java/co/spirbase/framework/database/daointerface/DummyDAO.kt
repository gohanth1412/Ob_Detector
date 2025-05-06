package co.spirbase.framework.database.daointerface

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import co.spirbase.framework.database.entities.DummyEntity

@Dao
interface DummyDAO {

    @Insert
    fun insert(vararg entities: DummyEntity)

    @Update
    fun update(vararg entities: DummyEntity)

    @Delete
    fun delete(entity: DummyEntity)

    @Query("SELECT * FROM ${DummyEntity.TABLE_NAME} ORDER BY ${DummyEntity.ID} DESC")
    fun getAllDummies(): List<DummyEntity>?
}