package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_key", uniqueConstraints = @UniqueConstraint(columnNames = "valor"))
public class ApiKey extends PanacheEntity {

    @NotBlank
    @Column(length = 64, nullable = false, unique = true)
    public String valor;

    @Column(nullable = false)
    public boolean active = true;

    public String role; // ADMIN, CLIENTE etc

    @CreationTimestamp
    public LocalDateTime criadaEm;

    // (Opcional) Relacionamento com Usuario
    // @ManyToOne
    // public Usuario usuario;
}
