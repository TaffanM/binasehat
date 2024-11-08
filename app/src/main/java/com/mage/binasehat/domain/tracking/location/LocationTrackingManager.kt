package com.mage.binasehat.domain.tracking.location

import com.mage.binasehat.domain.tracking.model.LocationTrackingInfo

interface LocationTrackingManager {
    fun setCallback(locationCallback: LocationCallback)

    fun removeCallback()

    interface LocationCallback {
        fun onLocationUpdate(results: List<LocationTrackingInfo>)
    }
}