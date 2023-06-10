package com.haechi.sesacthon.pharmacy.repository

import com.haechi.sesacthon.pharmacy.model.Pharmacy
import com.haechi.sesacthon.pharmacy.model.QPharmacy.Companion.pharmacy
import com.querydsl.jpa.impl.JPAQueryFactory

class PharmacyRepositoryCustomImpl(
    val queryFactory: JPAQueryFactory
): PharmacyRepositoryCustom {

    // 최소값과 최댓값을 받아 사이의 값들을 리턴
    override fun findByLatitudeAndLongitude(minLat: Double, maxLat: Double, minLon: Double, maxLon: Double): MutableList<Pharmacy> {

        return queryFactory
            .selectFrom(pharmacy)
            .where(
                pharmacy.latitude.doubleValue().loe(maxLat)
                    .and(pharmacy.latitude.doubleValue().goe(minLat))
                    .and(pharmacy.longitude.doubleValue().loe(maxLon))
                    .and(pharmacy.longitude.doubleValue().goe(minLon)))
            .fetch()
    }

}