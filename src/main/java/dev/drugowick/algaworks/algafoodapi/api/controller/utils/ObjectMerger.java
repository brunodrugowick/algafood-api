package dev.drugowick.algaworks.algafoodapi.api.controller.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Helper method to merge the request body (a Map<String, Object>) to an Object to be updated via a Patch HTTP request.
 * <p>
 * Uses ReflectionUtils from Spring Framework to set values to the Object.
 *
 * @param <T>
 */
public class ObjectMerger<T> {

    private ObjectMapper objectMapper;
    private Class<T> type;

    public ObjectMerger(Class<T> type) {
        this.objectMapper = new ObjectMapper();
        this.type = type;
    }

    public T mergeRequestBodyToGenericObject(Map<String, Object> objectMap, T objectToUpdate) {
        T newObject = objectMapper.convertValue(objectMap, type);

        objectMap.forEach((fieldProp, valueProp) -> {
            Field field = ReflectionUtils.findField(type, fieldProp);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, newObject);

            ReflectionUtils.setField(field, objectToUpdate, newValue);
        });

        return objectToUpdate;
    }
}
