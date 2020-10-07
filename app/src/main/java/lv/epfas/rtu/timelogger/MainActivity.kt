package lv.epfas.rtu.timelogger

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import kotlin.concurrent.timer

@Suppress("MoveLambdaOutsideParentheses")
class MainActivity : AppCompatActivity() {
    // private val db get() = Database.getInstance(this)

    var dt1: LocalDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    var dt2: LocalDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFinishTask.setOnClickListener({
            Toast.makeText(
                this,
                "Time stopped",
                Toast.LENGTH_SHORT
            ).show()
        })

        btnProjects.setOnClickListener({
            // startActivity(Intent(this, ProjectListActivity::class.java))
            val timelinePoint = LocalDateTime.parse("2020-10-07T02:49:00")
            val now = LocalDateTime.now()

            val elapsedTime = Duration.between(timelinePoint, now)

            // val s : String =   elapsedTime.toHours().toString()  + ":" + elapsedTime.toMinutes().toString()


            val s: String =
                (elapsedTime.toMillis() / 1000 / 1440).toString() + ":" + (elapsedTime.toMillis() / 1000).toString() + ":"

            Toast.makeText(
                this,
                s,
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}