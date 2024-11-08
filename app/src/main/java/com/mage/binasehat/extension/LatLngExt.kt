package com.mage.binasehat.extension

import com.google.android.gms.maps.model.LatLng
import com.mage.binasehat.domain.tracking.model.LocationInfo

fun LatLng.toLocationInfo() = LocationInfo(
    longitude = longitude,
    latitude = latitude
)