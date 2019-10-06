package com.example.tasksexample.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.tasksexample.R
import com.example.tasksexample.mvp.models.realm.TasksRealm
import com.example.tasksexample.mvp.presenters.TasksAddPresenter
import com.example.tasksexample.mvp.views.AddTaskView
import kotlinx.android.synthetic.main.activity_task_add.*

class TasksAddActivity : MvpAppCompatActivity(), AddTaskView {

    lateinit var etContent: EditText

    @InjectPresenter
    lateinit var tasksAddTasksPresenter: TasksAddPresenter

    @ProvidePresenter
    fun addTasksPresenter(): TasksAddPresenter {
        return TasksAddPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_add)
        etContent = findViewById(R.id.edContent)
        if (intent.getStringExtra("typeQuery").toString() == "edit") {
            toolbar.title = "Редактирование заметки"
            btSave.visibility = View.GONE
            tasksAddTasksPresenter.showTask(intent.getIntExtra("TaskId", 1))
        } else {
            toolbar.title = "Новая заметка"
            btEdit.visibility = View.GONE
        }
    }

    /**Presenters**/
    override fun showTask(tasksRealm: TasksRealm) {
        edContent.setText(tasksRealm.content)
    }

    override fun saveTask(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun editTask(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**Buttons**/
    fun saveTask(v: View) {
        tasksAddTasksPresenter.saveTask(edContent.text.toString())
        finish()
    }

    fun editTask(v: View) {
        tasksAddTasksPresenter.editTask(intent.getIntExtra("TaskId", 1), edContent.text.toString())
        finish()
    }
}
