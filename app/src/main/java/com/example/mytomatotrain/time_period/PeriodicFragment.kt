package com.example.mytomatotrain.time_period

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.utils.Constants.PERIODIC_KEY
import org.koin.android.ext.android.inject

class PeriodicFragment : Fragment() {

    private val presenter: PeriodicPresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.period_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val periodic = arguments?.getSerializable(PERIODIC_KEY) as Periodic

        val toolbar = view.findViewById<Toolbar>(R.id.periodic_toolbar)
        val toolbarTitle = toolbar.findViewById<TextView>(R.id.toolbar_title)
        val toolbarAction = toolbar.findViewById<ImageButton>(R.id.toolbar_button_back)

        toolbarTitle.text = periodic.title
        toolbarAction.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val fragmentView = PeriodicViewImpl(view)
        presenter.attachView(fragmentView)
        presenter.setPeriodic(periodic)
        presenter.setListeners()
        presenter.setContent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}