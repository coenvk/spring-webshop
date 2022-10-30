package org.coenvk.samples.spring.toolkit.model;

import java.util.Iterator;
import java.util.function.BiFunction;
import org.springframework.data.util.Pair;

public abstract class ValueObject {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        var other = (ValueObject)obj;

        var zippedComponents = zip(getEqualityComponents(), other.getEqualityComponents());
        while (zippedComponents.hasNext()) {
            var next = zippedComponents.next();
            if (!next.getFirst().equals(next.getSecond())) {
                return false;
            }
        }
        return true;
    }

    private static <A, B> Iterator<Pair<A, B>> zip(Iterator<A> a, Iterator<B> b) {
        return map(a, b, Pair::of);
    }

    private static <A, B, C> Iterator<C> map(Iterator<A> a, Iterator<B> b, BiFunction<A, B, C> f) {
        return new Iterator<C>() {
            @Override
            public boolean hasNext() {
                return a.hasNext() && b.hasNext();
            }

            @Override
            public C next() {
                return f.apply(a.next(), b.next());
            }
        };
    }

    protected abstract Iterator<?> getEqualityComponents();
}
