package com.example.mytomatotrain.db

import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.Status
import com.example.mytomatotrain.task.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

interface Repository {
    fun upsertTask(task: Task): Completable
    fun deleteTask(task: Task): Completable
    fun getTaskByName(title: String): Single<Task>
    fun getAllTasksFromPeriod(periodic: Periodic): Single<List<Task>>
    fun updateTaskById(id: Int, newTomatoStatus: Status, timeLeft: Int): Completable
    fun getTaskById(id: Int): Single<Task>
    fun isTaskByIdComplete(id: Int): Single<Boolean>
}

class RepositoryImpl(tasksDatabase: TasksDatabase) : Repository {

    private val dao = tasksDatabase.dao

    override fun upsertTask(task: Task) = Completable
        .fromAction { dao.upsertTask(task) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun deleteTask(task: Task) = Completable.fromAction { dao.deleteTask(task) }

    override fun getTaskById(id: Int) = Single
        .fromCallable { dao.getTaskById(id) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    override fun getTaskByName(title: String) = Single
        .fromCallable { dao.getTaskByName(title) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun isTaskByIdComplete(id: Int) = Single
        .fromCallable { dao.getTaskById(id).isTaskComplete }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getAllTasksFromPeriod(periodic: Periodic) = Single
        .fromCallable { dao.getAllTaskFromPeriod(periodic) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun updateTaskById(id: Int, newTomatoStatus: Status, timeLeft: Int) = Completable.fromAction {
        val task = dao.getTaskById(id)
        if (task.listTomatoes.isNotEmpty()) {
            val lastTomato = task.listTomatoes.last()
            lastTomato.status = newTomatoStatus
            lastTomato.timeLeft = timeLeft
            dao.updateTask(task)
        }
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}