package com.example.reportar.presentation.ui.incident

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.material3.MaterialTheme
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reportar.domain.adapter.ImageAdapter
import com.example.reportar.R
import com.example.reportar.databinding.FragmentIncidentFormBinding
import com.example.reportar.domain.model.Incident
import com.example.reportar.domain.model.IncidentTags
import com.example.reportar.presentation.state.IncidentState
import com.example.reportar.presentation.viewmodel.IncidentViewModel
import com.example.reportar.presentation.viewmodel.IncidentViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.io.File
import kotlin.random.Random

class IncidentFormFragment :
    Fragment(R.layout.fragment_incident_form) {

    private var _binding: FragmentIncidentFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: IncidentViewModel by activityViewModels {
        IncidentViewModelFactory()
    }
    private lateinit var marker: Marker
    private val cameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->

            if (success) {

                viewModel.state.value.currentPhotoUri?.let {

                    viewModel.addImage(it)
                }
            }
        }
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var mapController: IncidentMapController
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var formInitialized = false

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentIncidentFormBinding.bind(view)


        mapController =
            IncidentMapController(
                binding.map
            )

        mapController.setupMap()

        imageAdapter = ImageAdapter { uri ->

            viewModel.removeImage(uri)
        }

        binding.rvImages.apply {

            adapter = imageAdapter

            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }

        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences(
                "osm",
                Context.MODE_PRIVATE
            )
        )

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(
                requireActivity()
            )

        observeState()
        setupListeners()
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun observeState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.state.collect { state ->

                    initializeForm(state)

                    mapController.initializeMap(state)

                    binding.tvLatitud.text = "Latitud: ${state.latitude ?: "-"}"

                    binding.tvLongitud.text = "Longitud: ${state.longitude ?: "-"}"

                    binding.composeTags.setContent {

                        MaterialTheme {

                            TagSelector(

                                availableTags =
                                    IncidentTags.availableTags,

                                selectedTags =
                                    state.tags,

                                onTagClick = { tag ->

                                    viewModel.toggleTag(tag)
                                }
                            )
                        }
                    }

                    imageAdapter.submitList(
                        state.images
                    )
                }
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun setupListeners() {

        binding.btnSave.setOnClickListener {

            val selected =
                viewModel.state.value.selectedIncident

            val incident = Incident(

                id = selected?.id ?: Random.nextInt(),

                title = binding.etTitle.text.toString(),

                description = binding.etDescription.text.toString(),

                latitude = viewModel.state.value.latitude ?: 0.0,

                longitude = viewModel.state.value.longitude ?: 0.0,

                tags = viewModel.state.value.tags,

                imageUris = viewModel.state.value.images
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

        binding.btnTakePhoto.setOnClickListener {

            val uri = createImageUri()

            viewModel.setCurrentPhotoUri(uri.toString())

            cameraLauncher.launch(uri)
        }

        mapController.setOnMapClick { lat, lon ->

            viewModel.setLocation(
                lat,
                lon
            )
        }

        binding.btnMyLocation.setOnClickListener {

            centerOnUser()
        }
    }

    private fun createImageUri(): Uri {

        val file = File(
            requireContext().cacheDir,
            "incident_${System.currentTimeMillis()}.jpg"
        )

        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )
    }

    private fun initializeForm(
        state: IncidentState
    ) {

        if (formInitialized) return

        state.selectedIncident?.let { incident ->

            binding.etTitle.setText(
                incident.title
            )

            binding.etDescription.setText(
                incident.description
            )
        }

        formInitialized = true
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun centerOnUser() {

        if (!hasLocationPermission()) return

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->

                location ?: return@addOnSuccessListener

                mapController.moveToLocation(
                    location.latitude,
                    location.longitude
                )

                viewModel.setLocation(
                    location.latitude,
                    location.longitude
                )
            }
    }

    fun hasLocationPermission(): Boolean {

        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}