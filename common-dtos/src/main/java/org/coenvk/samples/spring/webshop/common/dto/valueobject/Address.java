package org.coenvk.samples.spring.webshop.common.dto.valueobject;

import java.util.Iterator;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.model.ValueObject;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class Address extends ValueObject {
    String street;
    String city;
    String state;
    String country;
    String zipCode;

    @Override
    @Transient
    protected Iterator<?> getEqualityComponents() {
        return List.of(street, city, state, country, zipCode).iterator();
    }
}
