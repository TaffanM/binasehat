package com.mage.binasehat.extension

import com.google.android.gms.maps.model.LatLng
import com.mage.binasehat.domain.tracking.model.LocationInfo

fun LocationInfo.toLatLng() = LatLng(
    latitude,
    longitude
)