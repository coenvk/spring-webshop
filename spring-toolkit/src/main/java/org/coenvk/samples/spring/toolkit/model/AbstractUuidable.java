package org.coenvk.samples.spring.toolkit.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Data
@MappedSuperclass
public abstract class AbstractUuidable<T extends UUID> implements Idable<T> {
    @Id
    @Setter(AccessLevel.NONE)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(updatable = false, nullable = false, unique = true, insertable = false)
    protected T id;

    public T getId() {
        return id;
    }
}
