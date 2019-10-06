package com.example.tasksexample.mvp.presenters

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.tasksexample.bus.DeleteTasksAction
import com.example.tasksexample.bus.EditTasksAction
import com.example.tasksexample.bus.TasksCheckedAction
import com.example.tasksexample.mvp.models.realm.TasksRealm
import com.example.tasksexample.mvp.views.MainView
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@InjectViewState
class MainPresenter(bus: Bus) : MvpPresenter<MainView>() {
    init {
        bus.register(this)
    }
    var realm: Realm = Realm.getDefaultInstance()
    var id: Int = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        selectTasks()
    }

    fun selectTasks() {
        //Получаем все задачи из БД
        val tasks = realm.where(TasksRealm::class.java)
            .findAll()
        viewState.selectTasks(tasks)
    }

    fun deleteTasks(id: Int) {
        val task = findTask(id)
        realm.beginTransaction()
        task.deleteFromRealm()
        realm.commitTransaction()
    }

    fun finishTasks(id: Int) {
        realm.beginTransaction()
        val tasks = realm.where(TasksRealm::class.java)
            .equalTo("id", id)
            .findFirstAsync()
        if (tasks.finish) {
            tasks.finish = false
            viewState.finishTask(id, "Задача снова активна!")
        } else {
            tasks.finish = true
            viewState.finishTask(id, "Задача выполнена!")
        }
        realm.commitTransaction()
    }


    private fun findTask(id: Int): TasksRealm {
        val task = realm.where(TasksRealm::class.java)
            .equalTo("id", id)
            .findFirst()
        return task!!
    }

    /**Прослушка шины**/
    @Subscribe
    fun finishTasksa(action: TasksCheckedAction) {
        finishTasks(action.position)
    }

    @Subscribe
    fun deleteTasksa(action: DeleteTasksAction) {
        deleteTasks(action.position)
    }
}