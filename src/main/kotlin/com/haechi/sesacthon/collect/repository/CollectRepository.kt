package com.haechi.sesacthon.collect.repository

import com.haechi.sesacthon.collect.model.Collect
import org.springframework.data.jpa.repository.JpaRepository

interface CollectRepository : JpaRepository<Collect, Long>, CollectRepositoryCustom {
}