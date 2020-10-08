package lv.epfas.rtu.timelogger

import android.content.Intent
import android.icu.util.UniversalTimeScale.toLong
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_project_card.*
import lv.epfas.rtu.timelogger.ProjectListActivity.Companion.EXTRA_ID

class ProjectCardActivity : AppCompatActivity() {

    private val db get() = Database.getInstance(this)

    private val items = mutableListOf<Project>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_card)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getLongExtra(EXTRA_ID, 0)

        updateFiels(id)

        btnSave.setOnClickListener { saveItem(id) }
    }

    private fun updateFiels(projectId: Long) {
        if (projectId == 0L) {
            edCodeData.setText("")
            edNameData.setText("")
            chInternal.setChecked(false)
            chFavorite.setChecked(false)
            chClosed.setChecked(false)
        } else {
            val item = db.ProjectDao().getItemById(projectId)
            edCodeData.setText(item.code)
            edNameData.setText(item.name)
            chInternal.setChecked(item.internal)
            chFavorite.setChecked(item.favorite)
            chClosed.setChecked(item.closed)
        }
    }

    private fun saveItem(projectId: Long) {
        if (edCodeData.text.toString() == "") {
            Toast.makeText(
                this,
                R.string.enter_data_msg,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (projectId == 0L) {
            val item = Project(
                edCodeData.text.toString(),
                edNameData.text.toString(),
                chFavorite.isChecked,
                chInternal.isChecked,
                chClosed.isChecked
            )
            item.uid = db.ProjectDao().insertAll(item).first()
            val intent = Intent().putExtra(EXTRA_ID, item.uid)
        } else {
            val item = db.ProjectDao().getItemById(projectId)
            db.ProjectDao().update(
                item.copy(
                    code = edCodeData.text.toString(),
                    name = edNameData.text.toString(),
                    favorite = chFavorite.isChecked,
                    internal = chInternal.isChecked,
                    closed = chClosed.isChecked
                )
            )
            // val intent = Intent().putExtra(EXTRA_ID, item.uid)
        }

        setResult(RESULT_OK, intent)
        finish()
    }
}