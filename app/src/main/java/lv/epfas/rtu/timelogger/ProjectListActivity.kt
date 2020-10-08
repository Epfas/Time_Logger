package lv.epfas.rtu.timelogger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_project_list.*

class ProjectListActivity : AppCompatActivity(), ProjectAdapterClickListener {
    //    private val db get() = (application as App).db
    private val db get() = Database.getInstance(this)

    private val items = mutableListOf<Project>()

    private lateinit var adapter: ProjectRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        items.addAll(db.ProjectDao().getAll())
        showLineCount()

        adapter = ProjectRecyclerAdapter(this, items)
        lsItems.adapter = adapter

        btnAddProject.setOnClickListener { appendItem() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.mi_projects_all -> {
                items.removeAll(items)
                items.addAll(db.ProjectDao().getAll())
                adapter.notifyDataSetChanged()
                true
            }

            R.id.mi_project_favourite -> {
                items.removeAll(items)
                items.addAll(db.ProjectDao().getFavoriteItems())
                adapter.notifyDataSetChanged()
                true
            }

            R.id.mi_project_internal -> {
                items.removeAll(items)
                items.addAll(db.ProjectDao().getInternalItems())
                adapter.notifyDataSetChanged()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_menu, menu)
        return true
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
            items[position] = item
            adapter.notifyItemChanged(position)
            // adapter.notifyDataSetChanged()
        }
    }

    private fun showLineCount() {
        Toast.makeText(
            this,
            resources.getString(R.string.line_count) + items.size.toString(),
            Toast.LENGTH_SHORT
        ).show()
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