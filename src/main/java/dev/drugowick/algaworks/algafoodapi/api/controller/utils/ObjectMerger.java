package dev.drugowick.algaworks.algafoodapi.api.controller.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper method to merge the request body (a Map<String, Object>) to an Object to be updated via a Patch HTTP request.
 * <p>
 * Uses ReflectionUtils from Spring Framework to set values to the Object.
 *
 * @param <T>
 */
public class ObjectMerger<T> {

    /**
     * Determines if a Map cache will be used for ObjectMergers.
     */
    private static boolean cacheEnabled = true;
    private static Map<Object, ObjectMerger> objectMergerCache = new HashMap<>();

    private static ObjectMapper objectMapper;
    private Class<T> type;


    private ObjectMerger(Class<T> type) {
        objectMapper = new ObjectMapper();
        this.type = type;
    }

    /**
     * Returns a new instance of ObjectMerger<T> of the given type as parameter OR
     * an existing instance created before.
     *
     * @param type a class to be merged with a Map of <String, Object>.
     * @return
     */
    public static ObjectMerger of(Class type) {

        if (!cacheEnabled) {
            // Cache is not enabled. A new instance is always created.
            return new ObjectMerger(type);
        }

        if (!objectMergerCache.containsKey(type)) {
            ObjectMerger objectMerger = new ObjectMerger(type);
            objectMergerCache.put(type, objectMerger);
            // Cache enabled. Instance created (first request).
            return objectMerger;
        }

        // Cache enabled. Returning existing instance.
        return objectMergerCache.get(type);
    }

    /**
     * Updates an objectToUpdate according to data from a Map<String, Object>.
     *
     * @param objectMap      a Map of <String, Object> with data to update destination object. The string values
     *                       must match fields on the destination object.
     * @param objectToUpdate the object to update.
     */
    public void mergeRequestBodyToGenericObject(Map<String, Object> objectMap, T objectToUpdate) {
        T newObject = objectMapper.convertValue(objectMap, type);

        objectMap.forEach((fieldProp, valueProp) -> {
            Field field = ReflectionUtils.findField(type, fieldProp);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, newObject);

            ReflectionUtils.setField(field, objectToUpdate, newValue);
        });
    }

}
