package com.example.reportar.presentation.ui.incident

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.example.reportar.domain.model.IncidentTag
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mapInitialized = false
    private var formInitialized = false

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentIncidentFormBinding.bind(view)

        setupMap()

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

                    initializeMap(state)

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

        binding.map.overlays.add(

            object : org.osmdroid.views.overlay.Overlay() {

                override fun onSingleTapConfirmed(
                    e: MotionEvent?,
                    mapView: MapView?
                ): Boolean {

                    e ?: return false

                    val projection =
                        binding.map.projection

                    val geoPoint =
                        projection.fromPixels(
                            e.x.toInt(),
                            e.y.toInt()
                        ) as GeoPoint

                    marker.position = geoPoint

                    binding.map.invalidate()

                    viewModel.setLocation(
                        geoPoint.latitude,
                        geoPoint.longitude
                    )

                    return true
                }
            }
        )

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

    @SuppressLint("ClickableViewAccessibility")
    private fun setupMap() {

        binding.map.setMultiTouchControls(true)

        val startPoint =
            GeoPoint(
                -42.7692,
                -65.0385
            )

        binding.map.controller.setZoom(13.0)
        binding.map.controller.setCenter(startPoint)

        marker = Marker(binding.map)

        binding.map.overlays.add(marker)

        binding.map.setOnTouchListener { view, event ->

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {

                    view.parent.requestDisallowInterceptTouchEvent(true)
                }

                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {

                    view.parent.requestDisallowInterceptTouchEvent(false)
                }
            }

            false
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun centerOnUser() {

        if (!hasLocationPermission()) return

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->

                location ?: return@addOnSuccessListener

                val point = GeoPoint(
                    location.latitude,
                    location.longitude
                )

                binding.map.controller.setZoom(16.0)
                binding.map.controller.animateTo(point)

                marker.position = point

                viewModel.setLocation(
                    point.latitude,
                    point.longitude
                )

                binding.map.invalidate()
            }
    }

    private fun centerOnIncident(
        latitude: Double,
        longitude: Double
    ) {

        val point = GeoPoint(
            latitude,
            longitude
        )

        marker.position = point

        binding.map.controller.setZoom(16.0)

        binding.map.controller.animateTo(point)

        binding.map.invalidate()
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun initializeMap(state: IncidentState) {

        if (mapInitialized) return

        val incident = state.selectedIncident

        if (
            incident != null &&
            incident.latitude != 0.0 &&
            incident.longitude != 0.0
        ) {

            centerOnIncident(
                incident.latitude,
                incident.longitude
            )

        } else {

            centerOnUser()
        }

        mapInitialized = true
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

    @Composable
    fun TagSelector(

        availableTags: List<IncidentTag>,

        selectedTags: List<String>,

        onTagClick: (String) -> Unit
    ) {

        Column {

            Text(

                text = "Categorías",

                style =
                    MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            LazyRow(

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)

            ) {

                items(availableTags) { tag ->

                    FilterChip(

                        selected = tag.id in selectedTags,

                        onClick = {

                            onTagClick(tag.id)
                        },

                        label = {

                            Text(tag.name)
                        },

                        leadingIcon = {

                            Icon(

                                imageVector = tag.icon,

                                contentDescription = tag.name
                            )
                        }
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            Text("Seleccionadas")

            FlowRow {

                selectedTags.forEach { tagId ->

                    val tag = availableTags.find {

                        it.id == tagId
                    }

                    tag?.let {

                        AssistChip(

                            onClick = {

                                onTagClick(it.id)
                            },

                            label = {

                                Text(it.name)
                            },

                            leadingIcon = {

                                Icon(

                                    imageVector = it.icon,

                                    contentDescription = it.name
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    private fun hasLocationPermission(): Boolean {

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