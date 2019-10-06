package com.example.tasksexample.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.tasksexample.*
import com.example.tasksexample.bus.EditTasksAction
import com.example.tasksexample.ui.adapters.TasksAdapter
import com.example.tasksexample.mvp.models.realm.TasksRealm
import com.example.tasksexample.mvp.presenters.MainPresenter
import com.example.tasksexample.mvp.views.MainView
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import io.realm.OrderedCollectionChangeSet
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {

    private var bus = Bus()

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter(): MainPresenter {
        return MainPresenter(this.bus)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bus.register(this)
    }

    /**Presenters**/
    override fun selectTasks(tasks: RealmResults<TasksRealm>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvTaskList.layoutManager = layoutManager
        rvTaskList.adapter = TasksAdapter(bus,this, tasks)

        if (tasks.isEmpty()) {
            tvNoneTask.visibility = View.VISIBLE
        } else {
            tvNoneTask.visibility = View.GONE
        }
        tasks.addChangeListener { t: RealmResults<TasksRealm>, changeSet: OrderedCollectionChangeSet?
            ->
            if (tasks.isEmpty()) {
                tvNoneTask.visibility = View.VISIBLE
            } else {
                tvNoneTask.visibility = View.GONE
            }
            rvTaskList.adapter = TasksAdapter(bus,this, tasks)
            rvTaskList.adapter!!.notifyDataSetChanged()
        }
    }
    override fun finishTask(id: Int, message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun deleteTasks(id: Int) {
        Toast.makeText(this, "Задача удалена", Toast.LENGTH_SHORT).show()
    }

    /**Button**/
    fun btAddTask(v: View) {
        val intent = Intent(this, TasksAddActivity::class.java)
        intent.putExtra("typeQuery", "add")
        startActivityForResult(intent, 1)
    }

    /**Слушатели событий(для items from recyclerview)**/
    //Открыть редактирование задачи
    @Subscribe
    fun showTask(action: EditTasksAction) {
        val intent = Intent(this, TasksAddActivity::class.java)
        intent.putExtra("typeQuery", "edit")
        intent.putExtra("TaskId", action.position)
        startActivityForResult(intent, 1)
    }
}
