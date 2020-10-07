package lv.epfas.rtu.timelogger

import androidx.room.*

@Entity(tableName = "logger_state")
data class LoggerState(
    val taskId: Long,
    val taskDescription: String,
    val projectId: Long,
    val projectCode: String,
    val projectName: String,
    val startDate: Long,
    val startTime: Long,
    @PrimaryKey var uid: Long = 1
)

@Dao
interface LoggerStateDao {
    @Query("SELECT * FROM logger_state")
    fun getAll(): List<LoggerState>

    @Insert
    fun insertAll(vararg items: LoggerState): List<Long>

    @Update
    fun update(item: LoggerState)

    @Delete
    fun delete(item: LoggerState)
}