package org.acme;

//@Repository

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class MyEntityRepository extends PanacheEntity {
    public String field;
}