package lv.epfas.rtu.timelogger

import androidx.room.*
import java.time.LocalDateTime

@Entity(tableName = "logger_entry")
data class LoggerEntry(
    val projectId: Long,
    val taskId: Long,
    val startedAt: LocalDateTime,
    val finishedAt: LocalDateTime,
    val note: String,
    val closed: Boolean,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
)

@Dao
interface LoggerEntryDao {
    @Query("SELECT * FROM logger_entry")
    fun getAll(): List<LoggerEntry>

    @Query("SELECT * FROM logger_entry WHERE uid = :entryId")
    fun getItemById(entryId: Long): LoggerEntry

    @Query("SELECT * FROM logger_entry WHERE projectId = :projectId")
    fun getItemsByProjectId(projectId: Long): LoggerEntry

    @Query("SELECT * FROM logger_entry WHERE taskId = :taskId")
    fun getItemsByTaskId(taskId: Long): LoggerEntry

    @Insert
    fun insertAll(vararg items: LoggerEntry): List<Long>

    @Update
    fun update(item: LoggerEntry)

    @Delete
    fun delete(item: LoggerEntry)
}