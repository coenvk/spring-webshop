package org.coenvk.samples.spring.toolkit.model;

import java.io.Serializable;

public interface Idable<T extends Serializable> {
    T getId();
}
