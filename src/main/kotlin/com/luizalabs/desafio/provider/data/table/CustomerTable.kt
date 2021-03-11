package com.luizalabs.desafio.provider.data.table

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.PreUpdate
import javax.persistence.Table

@Entity
@Table(name = "customer")
data class CustomerTable(
        @Id
        @Column(nullable = false)
        val id: UUID = UUID.randomUUID(),

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val email: String,

        @Column(name = "created_at", nullable = false, updatable = false)
        @CreatedDate
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at", nullable = true)
        @LastModifiedDate
        var updatedAt: LocalDateTime? = null
) {
        @PreUpdate
        fun setLastUpdate() {
                this.updatedAt = LocalDateTime.now()
        }
}
