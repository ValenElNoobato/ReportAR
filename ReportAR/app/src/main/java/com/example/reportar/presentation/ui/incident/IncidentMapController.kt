package com.example.reportar.presentation.ui.incident

import android.Manifest
import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.annotation.RequiresPermission
import com.example.reportar.presentation.state.IncidentState
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class IncidentMapController (
    private val map: MapView
) {
    private val marker = Marker(map)
    private var mapInitialized = false

    @SuppressLint("ClickableViewAccessibility")
    fun setupMap() {

        map.setMultiTouchControls(true)

        val startPoint =
            GeoPoint(
                -42.7692,
                -65.0385
            )

        map.controller.setZoom(13.0)
        map.controller.setCenter(startPoint)

        map.overlays.add(marker)

        map.setOnTouchListener { view, event ->

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
    fun initializeMap(state: IncidentState) {

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

        }

        mapInitialized = true
    }

    fun centerOnIncident(
        latitude: Double,
        longitude: Double
    ) {

        val point = GeoPoint(
            latitude,
            longitude
        )

        marker.position = point

        map.controller.setZoom(16.0)

        map.controller.animateTo(point)

        map.invalidate()
    }

    fun moveToLocation(
        latitude: Double,
        longitude: Double
    ) {

        val point =
            GeoPoint(latitude, longitude)

        marker.position = point

        map.controller.setZoom(16.0)

        map.controller.animateTo(point)

        map.invalidate()
    }

    fun setOnMapClick(
        callback: (Double, Double) -> Unit
    ) {
        map.overlays.add(

            object : org.osmdroid.views.overlay.Overlay() {

                override fun onSingleTapConfirmed(
                    e: MotionEvent?,
                    mapView: MapView?
                ): Boolean {

                    e ?: return false

                    val projection = map.projection

                    val geoPoint =
                        projection.fromPixels(
                            e.x.toInt(),
                            e.y.toInt()
                        ) as GeoPoint

                    marker.position = geoPoint

                    map.invalidate()

                    callback(
                        geoPoint.latitude,
                        geoPoint.longitude
                    )

                    return true
                }
            }
        )
    }
}