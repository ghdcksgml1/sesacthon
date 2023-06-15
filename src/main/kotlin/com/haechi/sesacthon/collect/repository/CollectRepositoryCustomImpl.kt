package com.haechi.sesacthon.collect.repository

import com.haechi.sesacthon.collect.model.Collect
import com.haechi.sesacthon.collect.model.QCollect.Companion.collect
import com.haechi.sesacthon.pharmacy.model.QPharmacy.Companion.pharmacy
import com.haechi.sesacthon.publichealth.model.QPublicHealth.Companion.publicHealth
import com.querydsl.jpa.impl.JPAQueryFactory

class CollectRepositoryCustomImpl(
    val queryFactory: JPAQueryFactory
) : CollectRepositoryCustom {

    override fun findByIdWithAll(collect_id: Long, user_id: Long): Collect? {
        return queryFactory
            .selectFrom(collect)
            .join(collect.pharmacy, pharmacy)
            .join(collect.publichealth, publicHealth)
            .where(collect.id.eq(collect_id)
                .and(pharmacy.user.id.eq(user_id)))
            .fetchOne()
    }

    override fun findAllWithAllPharmacy(user_id: Long, memo: String): MutableList<Collect> {
        return queryFactory
            .selectFrom(collect)
            .join(collect.pharmacy, pharmacy).fetchJoin()
            .join(collect.publichealth, publicHealth).fetchJoin()
            .where(pharmacy.user.id.eq(user_id)
                .and(collect.memo.eq(memo)))
            .orderBy(collect.id.desc())
            .fetch()
    }

    override fun findAllWithAllPublicHealth(user_id: Long, memo: String): MutableList<Collect> {
        return queryFactory
            .selectFrom(collect)
            .join(collect.pharmacy, pharmacy).fetchJoin()
            .join(collect.publichealth, publicHealth).fetchJoin()
            .where(publicHealth.user.id.eq(user_id)
                .and(collect.memo.eq(memo)))
            .orderBy(collect.id.desc())
            .fetch()
    }
}