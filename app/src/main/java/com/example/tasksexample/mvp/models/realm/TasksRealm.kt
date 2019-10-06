package com.example.tasksexample.mvp.models.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class TasksRealm : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var content: String? = null
    var finish:Boolean=false
}