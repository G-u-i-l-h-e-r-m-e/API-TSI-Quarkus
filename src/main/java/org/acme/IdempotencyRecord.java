package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

public class IdempotencyRecord extends PanacheEntity{
    public String keyRecord;
}
