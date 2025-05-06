package co.spirbase.framework.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import co.spirbase.framework.database.AppDatabase
import co.spirbase.framework.database.daointerface.DummyDAO
import co.spirbase.framework.database.daointerface.TodoDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .enableMultiInstanceInvalidation()
                .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
                .build()

            INSTANCE = instance
            instance
        }
    }

    @Provides
    fun provideDummyDao(db: AppDatabase): DummyDAO {
        return db.dummyDAO()
    }

    @Provides
    fun provideTodoDao(database: AppDatabase) : TodoDAO {
        return database.todoDAO()
    }
}