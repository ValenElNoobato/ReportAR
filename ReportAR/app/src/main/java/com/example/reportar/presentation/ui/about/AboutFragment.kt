package com.example.reportar.presentation.ui.about

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.reportar.R
import com.example.reportar.presentation.viewmodel.AboutViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class AboutFragment : Fragment(R.layout.fragment_info) {

    private val viewModel: AboutViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val info1 = view.findViewById<TextView>(R.id.tvInfo)
        val info2 = view.findViewById<TextView>(R.id.tvInfo2)

        lifecycleScope.launch {
            viewModel.state.collect { state ->

                info1.text = state.info
                info2.text = state.info2
            }
        }
    }
}