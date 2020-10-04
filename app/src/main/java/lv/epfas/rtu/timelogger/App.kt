package lv.epfas.rtu.timelogger

import android.app.Application
import androidx.room.Room

class App : Application() {

    val db by lazy {
        Room.databaseBuilder(this, TimeLoggerDatabase::class.java, "logger-db")
            .allowMainThreadQueries()
            .build()
    }
}