package com.luizalabs.desafio.provider.data.table

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "favorite")
data class FavoriteTable(
        @Id
        @Column(nullable = false)
        val id: UUID = UUID.randomUUID(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "favorites_list_id", nullable = false)
        val favoritesList: FavoritesListTable,

        @Column(name = "product_id", nullable = false)
        val productId: UUID,

        @Column(name = "added_at", nullable = false, updatable = false)
        @CreatedDate
        val addedAt: LocalDateTime = LocalDateTime.now()
)
