package com.example.tasksexample.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.tasksexample.helpers.Helpers
import com.example.tasksexample.mvp.models.realm.TasksRealm
import com.example.tasksexample.mvp.views.AddTaskView
import io.realm.Realm

@InjectViewState
class TasksAddPresenter : MvpPresenter<AddTaskView>() {
    var realm: Realm = Realm.getDefaultInstance()
    fun saveTask(content: String) {
        realm.beginTransaction()
        val tasks = realm.createObject(
            TasksRealm::class.java,
            Helpers.getTaskMaxId(this.realm)
        )
        tasks.content = content
        tasks.finish=false
        realm.commitTransaction()
        viewState.saveTask("Задача успечшно создана!")
    }

    fun editTask(id: Int, newContent: String) {
        val task = findTask(id)
        realm.beginTransaction()
        task.content = newContent
        realm.commitTransaction()
        viewState.editTask("Задача изменена!")
    }

    fun showTask(id: Int) {
        viewState.showTask(findTask(id))
    }

    private fun findTask(id: Int): TasksRealm {
        val task = realm.where(TasksRealm::class.java)
            .equalTo("id", id)
            .findFirst()
        return task!!
    }
}