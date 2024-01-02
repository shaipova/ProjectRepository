package com.example.mytomatotrain.create_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.mytomatotrain.R
import org.koin.android.ext.android.inject

class CreateTaskFragment : Fragment() {

    private val presenter: CreateTaskPresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_task_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.create_task_toolbar)
        val toolbarAction = toolbar.findViewById<ImageButton>(R.id.toolbar_button_close)

        toolbarAction.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val fragmentView = CreateTaskViewImpl(view)
        presenter.attachView(fragmentView)
        presenter.setListeners()
        presenter.setContent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

}