package com.haechi.sesacthon.collect.repository

import com.haechi.sesacthon.collect.model.Collect

interface CollectRepositoryCustom {

    open fun findByIdWithAll(collect_id: Long, user_id: Long): Collect?
    open fun findAllWithAllPharmacy(user_id: Long, memo: String): MutableList<Collect>
    open fun findAllWithAllPublicHealth(user_id: Long, memo: String): MutableList<Collect>
}