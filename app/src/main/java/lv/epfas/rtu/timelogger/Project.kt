package lv.epfas.rtu.timelogger

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity(tableName = "project")
data class Project(
    val code: String,
    val name: String,
    val favorite: Boolean,
    val internal: Boolean,
    val closed: Boolean,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0L
)

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project")
    fun getAll(): List<Project>

    @Query("SELECT * FROM project WHERE uid = :projectId")
    fun getItemById(projectId: Long): Project

    @Query("SELECT * FROM project WHERE closed = 0")
    fun getOpenItems(): List<Project>

    @Query("SELECT * FROM project WHERE closed = 0 AND internal = 1")
    fun getInternalItems(): List<Project>

    @Query("SELECT * FROM project WHERE closed = 0 AND favorite = 1")
    fun getFavoriteItems(): List<Project>

    @Insert
    fun insertAll(vararg items: Project): List<Long>

    @Update
    fun update(item: Project)

    @Delete
    fun delete(item: Project)
}