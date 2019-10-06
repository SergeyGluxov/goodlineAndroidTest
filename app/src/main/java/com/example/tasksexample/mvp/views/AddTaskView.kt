package com.example.tasksexample.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.tasksexample.mvp.models.realm.TasksRealm
import io.realm.Realm

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface AddTaskView : MvpView {
    fun showTask(taskRealm: TasksRealm)
    fun saveTask(message: String)
    fun editTask(message: String)
}