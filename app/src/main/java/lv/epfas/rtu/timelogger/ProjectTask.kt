package lv.epfas.rtu.timelogger

import androidx.room.*

@Entity(tableName = "project_task")
data class ProjectTask(
    val projectId: Long,
    val externalId: String,
    val description: String,
    val closed: Boolean,
    val favorite: Boolean,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
)

@Dao
interface ProjectTaskDao {
    @Query("SELECT * FROM project_task")
    fun getAll(): List<ProjectTask>

    @Query("SELECT * FROM project_task WHERE closed = :closed")
    fun getItemsByStatus(closed: Boolean): List<ProjectTask>

    @Query("SELECT * FROM project_task WHERE favorite = 1 AND closed = 0")
    fun getFavoriteItems(): List<ProjectTask>

    @Query("SELECT * FROM project_task WHERE closed = 0")
    fun getOpenItems(): List<ProjectTask>

    @Query("SELECT * FROM project_task WHERE projectId = :projectId AND closed = :closed")
    fun getitemsByProjectAndStatus(projectId: Long, closed: Boolean): List<ProjectTask>

    @Query("SELECT * FROM project WHERE uid = :taskId")
    fun getItemById(taskId: Long): ProjectTask

    @Insert
    fun insertAll(vararg items: ProjectTask): List<Long>

    @Update
    fun update(item: ProjectTask)

    @Delete
    fun delete(item: ProjectTask)
}