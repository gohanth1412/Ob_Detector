package co.spirbase.framework.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.spirbase.framework.database.entities.DummyEntity.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class DummyEntity(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = VALUE)
    val value: String
) : Parcelable {
    companion object {
        const val TABLE_NAME = "TABLE_NAME"
        const val ID = "ID"
        const val VALUE = "VALUE"
    }
}
