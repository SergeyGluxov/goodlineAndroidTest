package com.example.tasksexample.ui.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasksexample.R
import com.example.tasksexample.bus.DeleteTasksAction
import com.example.tasksexample.bus.EditTasksAction
import com.example.tasksexample.bus.TasksCheckedAction
import com.example.tasksexample.mvp.models.realm.TasksRealm
import com.squareup.otto.Bus
import io.realm.RealmResults
import kotlinx.android.synthetic.main.task_item.view.*

class TasksAdapter(
    val bus: Bus,
    private val context: Context,
    private val tasks: RealmResults<TasksRealm>
) :
    RecyclerView.Adapter<TasksAdapter.TasksVH>() {

    init {
        bus.register(this)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TasksVH {
        val view = LayoutInflater.from(context).inflate(R.layout.task_item, p0, false)
        return TasksVH(view)
    }

    override fun onBindViewHolder(p0: TasksVH, p1: Int) {
        p0.setData(tasks, p1)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class TasksVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var selectedPos: Int = RecyclerView.NO_POSITION
        var currentPosition: Int = 0
        fun setData(taskRealm: RealmResults<TasksRealm>, p0: Int) {
            itemView.cbFinishTask.isChecked = (selectedPos == p0)
            itemView.tvTitle.text = taskRealm[p0]!!.content

            /**Если текст задачи длинный**/
            if (taskRealm[p0]!!.content!!.length >= 20) {
                itemView.tvTitle.ellipsize = TextUtils.TruncateAt.END
            }

            /**Если задача завершена**/
            if (taskRealm[p0]!!.finish) {
                itemView.cbFinishTask.isChecked = taskRealm[p0]!!.finish
                itemView.tvTitle.paintFlags =
                    itemView.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                itemView.tvTitle.setTextColor(Color.parseColor("#8C000000"))
            }

            /**Select checkbox**/
            itemView.cbFinishTask.setOnClickListener {
                if (taskRealm[p0]!!.finish) {
                    itemView.tvTitle.setTextColor(Color.parseColor("#000000"))
                    itemView.tvTitle.paintFlags = 0
                }
                bus.post(TasksCheckedAction(taskRealm[p0]!!.id!!))
            }

            /**Click item**/
            itemView.setOnClickListener {
                bus.post(EditTasksAction(taskRealm[p0]!!.id!!))
            }

            /**Click delete**/
            itemView.btDelete.setOnClickListener {
                bus.post(DeleteTasksAction(taskRealm[p0]!!.id!!))
            }
            this.currentPosition = p0
        }

    }
}