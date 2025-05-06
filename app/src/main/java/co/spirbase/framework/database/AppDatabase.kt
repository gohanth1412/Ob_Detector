package co.spirbase.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.spirbase.framework.database.daointerface.DummyDAO
import co.spirbase.framework.database.daointerface.TodoDAO
import co.spirbase.framework.database.entities.DummyEntity
import co.spirbase.framework.database.entities.TodoEntity

@Database(entities = [DummyEntity::class, TodoEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dummyDAO(): DummyDAO

    abstract fun todoDAO(): TodoDAO

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}