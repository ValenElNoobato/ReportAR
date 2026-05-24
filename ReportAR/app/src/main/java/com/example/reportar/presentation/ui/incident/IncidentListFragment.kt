package com.example.reportar.presentation.ui.incident

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.reportar.R
import com.example.reportar.databinding.FragmentIncidentListBinding
import com.example.reportar.domain.model.Incident
import com.example.reportar.presentation.viewmodel.IncidentViewModel
import com.example.reportar.presentation.viewmodel.IncidentViewModelFactory
import kotlinx.coroutines.launch

class IncidentListFragment :
    Fragment(R.layout.fragment_incident_list) {

    private var _binding: FragmentIncidentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IncidentViewModel by activityViewModels {
        IncidentViewModelFactory()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentIncidentListBinding.bind(view)

        observeState()
        setupListeners()
    }

    private fun setupListeners() {

        binding.btnAddIncident.setOnClickListener {

            viewModel.clearSelectedIncident()

            findNavController().navigate(
                R.id.action_list_to_form
            )
        }
    }

    private fun observeState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.state.collect { state ->

                    renderIncidents(state.incidents)
                }
            }
        }
    }

    private fun renderIncidents(
        incidents: List<Incident>
    ) {

        binding.containerIncidents.removeAllViews()

        incidents.forEach { incident ->

            val button = Button(requireContext())

            button.text = incident.title

            button.setOnClickListener {

                viewModel.selectIncident(incident)

                findNavController().navigate(
                    R.id.action_list_to_form
                )
            }

            binding.containerIncidents.addView(button)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}