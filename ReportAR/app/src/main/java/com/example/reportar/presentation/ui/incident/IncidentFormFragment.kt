package com.example.reportar.presentation.ui.incident

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.reportar.R
import com.example.reportar.databinding.FragmentIncidentFormBinding
import com.example.reportar.domain.model.Incident
import com.example.reportar.presentation.viewmodel.IncidentViewModel
import com.example.reportar.presentation.viewmodel.IncidentViewModelFactory
import kotlinx.coroutines.launch
import kotlin.random.Random

class IncidentFormFragment :
    Fragment(R.layout.fragment_incident_form) {

    private var _binding: FragmentIncidentFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IncidentViewModel by activityViewModels {
        IncidentViewModelFactory()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentIncidentFormBinding.bind(view)

        observeState()
        setupListeners()
    }

    private fun observeState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.state.collect { state ->

                    state.selectedIncident?.let {

                        binding.etTitle.setText(it.title)

                        binding.etDescription.setText(
                            it.description
                        )
                    }
                }
            }
        }
    }

    private fun setupListeners() {

        binding.btnSave.setOnClickListener {

            val selected =
                viewModel.state.value.selectedIncident

            val incident = Incident(

                id = selected?.id ?: Random.nextInt(),

                title = binding.etTitle.text.toString(),

                description = binding.etDescription.text.toString(),

                latitude = 0.0,

                longitude = 0.0
            )

            if (selected == null) {

                viewModel.addIncident(incident)

            } else {

                viewModel.updateIncident(incident)
            }

            findNavController().popBackStack()
        }

        binding.btnDelete.setOnClickListener {

            viewModel.state.value.selectedIncident?.let {

                viewModel.deleteIncident(it.id)
            }

            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}