package com.example.mytomatotrain.scheduler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mytomatotrain.R
import org.koin.android.ext.android.inject

class ScheduleFragment : Fragment() {

    private val presenter: SchedulePresenter by inject()

    // переход сюда осуществляется по кнопке done из создания таски
    // так же это главный экран, который при запуске уже знает есть ли в БД таски или нет
    // соответственно имеет заглушку "ещё нет задач" и кнопку на экран создания таски

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.schedule_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentView = ScheduleViewImpl(view)
        presenter.attachView(fragmentView)
        presenter.setListeners()
        presenter.setContent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}