package org.coenvk.samples.spring.toolkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.coenvk.samples.spring.toolkit.model.Idable;
import org.springframework.stereotype.Component;

@Component
public class ToolkitCollection {

    public <S> void updateCollection(Collection<S> targetCollection, Collection<S> newCollection) {
        updateCollection(targetCollection, newCollection, true);
    }

    /**
     * Updates the target collection to match the new collection.
     *
     * @param targetCollection The collection to modify
     * @param newCollection    The collection with the new data
     * @param keepOldMatching  Keeps old matching elements, otherwise all data is removed from targetCollection
     *                         and newCollection is added. Note that edits in newCollection will be ignored!
     * @param <S>              Type of elements in the collection
     */
    public <S> void updateCollection(Collection<S> targetCollection, Collection<S> newCollection,
                                     boolean keepOldMatching) {
        if (newCollection == null || newCollection.isEmpty()) {
            targetCollection.clear();
        } else if (targetCollection.isEmpty()) {
            targetCollection.addAll(newCollection);
        } else {
            if (keepOldMatching) {
                // Remove all items from targetCollection that are not in newCollection
                Set<S> toKeep = intersection(targetCollection, newCollection);
                targetCollection.removeIf(t -> !toKeep.contains(t));

                // Remove all matching elements from newCollection to not add doubles
                ArrayList<S> copy = new ArrayList<>(newCollection);
                copy.removeAll(intersection(newCollection, targetCollection));
                newCollection = copy;
            } else {
                // simply discard old data
                targetCollection.clear();
            }

            // Finally add all remaining new elements
            targetCollection.addAll(newCollection);
        }
    }

    public <S> Set<S> intersection(Collection<S> targetCollection, Collection<S> newCollection) {
        return targetCollection.stream().filter(old -> {
            if (old instanceof Idable) {
                return newCollection.stream().map(o -> (Idable) o)
                        .anyMatch(n -> Objects.equals(n.getId(), ((Idable) old).getId()));
            } else {
                return newCollection.stream()
                        .anyMatch(n -> Objects.equals(n, old));
            }
        }).collect(Collectors.toSet());
    }
}