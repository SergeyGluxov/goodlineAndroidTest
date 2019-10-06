package com.example.tasksexample.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.tasksexample.mvp.models.realm.TasksRealm
import io.realm.RealmResults

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface MainView:MvpView{
    //Выбрать задачи
    fun selectTasks(tasks: RealmResults<TasksRealm>)
    //Закончить задачу
    fun finishTask(id:Int,message:String)
    //Удалить задачу
    fun deleteTasks(id:Int)
}