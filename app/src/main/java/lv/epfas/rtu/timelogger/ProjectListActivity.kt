package lv.epfas.rtu.timelogger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_project_list.*

class ProjectListActivity : AppCompatActivity(), ProjectAdapterClickListener {
    //    private val db get() = (application as App).db
    private val db get() = Database.getInstance(this)

    private val items = mutableListOf<Project>()

    private lateinit var adapter: ProjectRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        items.addAll(db.ProjectDao().getAll())
        adapter = ProjectRecyclerAdapter(this, items)
        lsItems.adapter = adapter

        btnAdd.setOnClickListener { appendItem() }
    }

    private fun appendItem() {
        val intent = Intent(this, ProjectCardActivity::class.java)
            .putExtra(EXTRA_ID, 0L)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun itemClicked(item: Project) {
        val intent = Intent(this, ProjectCardActivity::class.java)
            .putExtra(EXTRA_ID, item.uid)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun deleteClicked(item: Project) {
        db.ProjectDao().delete(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DETAILS && resultCode == RESULT_OK && data != null) {
            val id = data.getLongExtra(EXTRA_ID, 0)
            val item = db.ProjectDao().getItemById(id)

            val position = items.indexOfFirst { it.uid == item.uid }
            items.sortBy { it.code }
            items[position] = item
            adapter.notifyItemChanged(position)
        }
    }

    companion object {
        const val EXTRA_ID = "lv.epfas.rtu.extras.project_id"
        const val REQUEST_CODE_DETAILS = 1234
    }
}

interface ProjectAdapterClickListener {

    fun itemClicked(item: Project)

    fun deleteClicked(item: Project)

}