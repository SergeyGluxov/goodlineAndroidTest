package com.example.tasksexample.helpers

import com.example.tasksexample.mvp.models.realm.TasksRealm
import io.realm.Realm
import io.realm.RealmResults

class Helpers {
    companion object {
        fun getTaskMaxId(realm:Realm): Int {
            val findMaxId = realm.where(TasksRealm::class.java).max("id")
            val maxId: Int
            maxId = if (findMaxId == null) {
                1
            } else {
                findMaxId.toInt() + 1
            }
            return maxId
        }
    }
}