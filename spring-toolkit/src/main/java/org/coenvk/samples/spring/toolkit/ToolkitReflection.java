package org.coenvk.samples.spring.toolkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;
import org.coenvk.samples.spring.toolkit.annotation.MappedWhen;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;

@Component
public class ToolkitReflection {
    private final ToolkitCollection toolkitCollection;

    @Autowired
    public ToolkitReflection(ToolkitCollection toolkitCollection) {
        this.toolkitCollection = toolkitCollection;
    }

    @SuppressWarnings("unchecked")
    public <T, U> U copyAllowedFields(T source, U destination) {
        Set<Field> fields = ReflectionUtils.getAllFields(source.getClass());
        for (Field field : fields) {
            updateAllowedField(source, destination, field);
        }
        return destination;
    }

    private <T, U> void updateAllowedField(T source, U model, Field field) {
        updateAllowedField(source, model, field, false);
    }

    private <T, U> void updateAllowedField(T source, U model, Field requestField, boolean force) {
        // Skip transient fields, we don't want them!
        if (!Modifier.isTransient(requestField.getModifiers())) {
            String fieldName = requestField.getName();
            Object newValue = FieldUtils.getProtectedFieldValue(fieldName, source);
            if (force || this.checkUpdateAllowed(requestField, newValue)) {
                Field matchingField = FieldUtils.getField(model.getClass(), fieldName);
                // check if types are the same
                if (matchingField.getType().isAssignableFrom(requestField.getType())) {
                    this.updateFieldValueToField(model, fieldName, newValue, matchingField);
                }
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> void updateFieldValueToField(T model, String fieldName, Object newValue, Field matchingField) {
        if (Collection.class.isAssignableFrom(matchingField.getType())) {
            Collection oldCollection = (Collection) FieldUtils.getProtectedFieldValue(fieldName, model);
            toolkitCollection.updateCollection(oldCollection, (Collection) newValue);
        } else {
            FieldUtils.setProtectedFieldValue(fieldName, model, newValue);
        }
    }

    public boolean checkUpdateAllowed(Field field, Object newValue) {
        boolean result = true;
        if (field.isAnnotationPresent(MappedWhen.class)) {
            MappedWhen annotation = field.getAnnotation(MappedWhen.class);
            if (annotation.value() == MappedWhen.MappingCondition.NOT_NULL) {
                result = newValue != null;
            } else if (annotation.value() == MappedWhen.MappingCondition.NULL) {
                result = newValue == null;
            }
        }
        return result;
    }

    public boolean hasField(Class<?> clazz, String field) {
        boolean result;
        try {
            clazz.getDeclaredField(field);
            result = true;
        } catch (NoSuchFieldException e) {
            result = false;
        }

        return result;
    }

    public <T> T generateInstance(Class<T> clazz, Logger logger) {
        T instance = null;
        Constructor<?> constructor = null;
        for (var con : clazz.getDeclaredConstructors()) {
            if (con.getParameterCount() == 0) {
                constructor = con;
            }
        }
        if (constructor != null) {
            constructor.setAccessible(true);
            try {
                instance = clazz.cast(constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
                // nothing to do
            }
            if (instance == null) {
                if (logger != null) {
                    logger.warn("Cannot create instance of class: " + clazz);
                }
            }
        } else {
            if (logger != null) {
                logger.warn("Cannot find constructor for class: " + clazz);
            }
        }
        return instance;
    }
}