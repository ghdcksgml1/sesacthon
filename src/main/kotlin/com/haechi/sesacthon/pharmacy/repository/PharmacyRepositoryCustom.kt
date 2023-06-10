package com.haechi.sesacthon.pharmacy.repository

import com.haechi.sesacthon.pharmacy.model.Pharmacy

interface PharmacyRepositoryCustom {

    open fun findByLatitudeAndLongitude(minLat: Double, maxLat: Double, minLon: Double, maxLon: Double): MutableList<Pharmacy>

}