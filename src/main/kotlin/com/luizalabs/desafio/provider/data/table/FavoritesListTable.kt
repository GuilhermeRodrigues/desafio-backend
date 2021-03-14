package com.luizalabs.desafio.provider.data.table

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "favorites_list")
data class FavoritesListTable(
        @Id
        @Column(nullable = false)
        val id: UUID = UUID.randomUUID(),

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id", nullable = false)
        val customer: CustomerTable
)
