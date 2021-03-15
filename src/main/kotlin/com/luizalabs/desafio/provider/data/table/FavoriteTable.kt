package com.luizalabs.desafio.provider.data.table

import com.luizalabs.desafio.provider.api.challenge.response.Product
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
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
@TypeDef(
    name = "jsonb",
    typeClass = JsonBinaryType::class
)
data class FavoriteTable(
        @Id
        @Column(nullable = false)
        val id: UUID = UUID.randomUUID(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "favorites_list_id", nullable = false)
        val favoritesList: FavoritesListTable,

        @Type(type = "jsonb")
        @Column(nullable = false, name = "product", columnDefinition = "jsonb")
        val product: Product,

        @Column(name = "created_at", nullable = false, updatable = false)
        @CreatedDate
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "deleted_at", nullable = true)
        @LastModifiedDate
        var deletedAt: LocalDateTime? = null
)
