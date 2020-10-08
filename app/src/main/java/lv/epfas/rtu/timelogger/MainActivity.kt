package lv.epfas.rtu.timelogger

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_project_list.*
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import kotlin.concurrent.timer

@Suppress("MoveLambdaOutsideParentheses")
class MainActivity : AppCompatActivity(), ProjectTaskAdapterClickListener {
    // private val db get() = Database.getInstance(this)

    private val db get() = Database.getInstance(this)

    private val items = mutableListOf<ProjectTask>()

    private lateinit var adapter: ProjectTaskRecyclerAdapter

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

        updateMainPanel()
        showLineCount()

        adapter = ProjectTaskRecyclerAdapter(this, items)
        projectTaskItems.adapter = adapter

        btnFinishTask.setOnClickListener { finishTask() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.projects_item -> {
                startActivity(Intent(this, ProjectListActivity::class.java))
                true
            }

            R.id.demo_data_item -> {
                prepareDemoData()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return true
    }

    private fun updateMainPanel() {
        val itemsPanel = mutableListOf<LoggerState>()
        itemsPanel.addAll(db.LoggerStateDao().getAll())

        if (itemsPanel.size == 0) {
            laTaskDescrValue.setText("---")
            laProjectValue.setText("---")
            laTimeValue.setText("--:--")
            btnFinishTask.visibility = View.INVISIBLE
        } else {
            val item = itemsPanel[0]
            laTaskDescrValue.setText(item.taskDescription)
            laProjectValue.setText(item.projectCode + "  -  " + item.projectName)
            laTimeValue.setText("00:00")
            btnFinishTask.visibility = View.VISIBLE
        }
    }

    private fun showLineCount() {
        Toast.makeText(
            this,
            resources.getString(R.string.line_count) + items.size.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun finishTask() {
        val itemsPanel = mutableListOf<LoggerState>()
        itemsPanel.addAll(db.LoggerStateDao().getAll())
        if (itemsPanel.size != 0) {
            val item = itemsPanel[0]
            db.LoggerStateDao().delete(item)
        }
        updateMainPanel()
    }

    private fun prepareDemoData() {
        val itemsProject = mutableListOf<Project>()
        val itemsTask = mutableListOf<ProjectTask>()

        // Office jobs
        val itemProject1 = Project("B100", "Office processes", false, true, false)
        itemProject1.uid = db.ProjectDao().insertAll(itemProject1).first()
        // itemsProject.add(itemProject)

        // Office jobs
        val itemTask11 = ProjectTask(itemProject1.uid, "B100-10", "Meeting", false, false)
        itemTask11.uid = db.ProjectTaskDao().insertAll(itemTask11).first()

        val itemTask12 = ProjectTask(itemProject1.uid, "B100-20", "E-Mails, etc.", false, false)
        itemTask12.uid = db.ProjectTaskDao().insertAll(itemTask12).first()

        val itemTask13 = ProjectTask(itemProject1.uid, "B100-30", "Accounting", false, false)
        itemTask13.uid = db.ProjectTaskDao().insertAll(itemTask13).first()

        val itemTask14 = ProjectTask(itemProject1.uid, "B100-80", "Self Train", false, false)
        itemTask14.uid = db.ProjectTaskDao().insertAll(itemTask14).first()

        // Project Internal
        val itemProject2 = Project("S215", "Scanner for warehouse management", true, true, false)
        itemProject2.uid = db.ProjectDao().insertAll(itemProject2).first()
        // itemsProject.add(itemProject)

        val itemTask21 =
            ProjectTask(itemProject2.uid, "S215-0019", "Prepare FRD for pick process", false, false)
        itemTask21.uid = db.ProjectTaskDao().insertAll(itemTask21).first()

        val itemTask22 = ProjectTask(
            itemProject2.uid,
            "S215-0047",
            "Bug fix: incorrect invetory after registering",
            false,
            false
        )
        itemTask22.uid = db.ProjectTaskDao().insertAll(itemTask22).first()

        // Project External
        val itemProject3 = Project("C044", "Company ABC WHM implementation", true, false, false)
        itemProject3.uid = db.ProjectDao().insertAll(itemProject3).first()
        // itemsProject.add(itemProject)

        val itemTask31 = ProjectTask(
            itemProject3.uid,
            "C044-0001",
            "Prepare scanner sales offer fpr 20 units",
            false,
            false
        )
        itemTask31.uid = db.ProjectTaskDao().insertAll(itemTask31).first()

    }

    override fun itemClicked(item: ProjectTask) {
        TODO("Not yet implemented")
    }

    override fun startClicked(item: ProjectTask) {
        TODO("Not yet implemented")
    }
}

interface ProjectTaskAdapterClickListener {

    fun itemClicked(item: ProjectTask)

    fun startClicked(item: ProjectTask)

}