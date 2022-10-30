package org.coenvk.samples.spring.toolkit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AuditedUuidable<T extends UUID> extends AbstractUuidable<T> {
    @Column(updatable = false, nullable = false)
    @CreatedDate
    @JsonIgnore
    protected final Long createdAt;

    public AuditedUuidable() {
        this.createdAt = new Date().getTime();
    }
}
