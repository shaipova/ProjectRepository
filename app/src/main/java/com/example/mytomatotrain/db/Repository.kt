package com.example.mytomatotrain.db

import com.example.mytomatotrain.task.Periodic
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
}

class RepositoryImpl(private val tasksDatabase: TasksDatabase): Repository {

    private val dao = tasksDatabase.dao

    override fun upsertTask(task: Task) = Completable
        .fromAction { dao.upsertTask(task) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun deleteTask(task: Task) = Completable.fromAction { dao.deleteTask(task) }

    fun getTask(id: Int) = dao.getTaskById(id)

    override fun getTaskByName(title: String) = Single
        .fromCallable { dao.getTaskByName(title) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getAllTasksFromPeriod(periodic: Periodic) = Single
        .fromCallable { dao.getAllTaskFromPeriod(periodic) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}