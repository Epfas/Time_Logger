package lv.epfas.rtu.timelogger

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    entities = [Project::class, ProjectTask::class, LoggerEntry::class, LoggerState::class]
)
@TypeConverters(Converters::class)

abstract class TimeLoggerDatabase : RoomDatabase() {

    abstract fun ProjectDao(): ProjectDao
    abstract fun ProjectTaskDao(): ProjectTaskDao
    abstract fun LoggerEntryDao(): LoggerEntryDao
    abstract fun LoggerStateDao(): LoggerStateDao
}

object Database {

    private var instance: TimeLoggerDatabase? = null

    fun getInstance(context: Context) = instance ?: Room.databaseBuilder(
        context.applicationContext, TimeLoggerDatabase::class.java, "logger-db"
    )
        .allowMainThreadQueries()
        .build()
        .also { instance = it }
}

