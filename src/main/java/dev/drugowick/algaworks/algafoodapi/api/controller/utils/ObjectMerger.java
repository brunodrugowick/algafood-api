package dev.drugowick.algaworks.algafoodapi.api.controller.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Helper class merge the request body (a Map<String, Object>) to an Object to be updated via a Patch HTTP request.
 * <p>
 * Uses ReflectionUtils from Spring Framework to set values to the Object.
 */
public class ObjectMerger {

    /**
     * Updates an objectToUpdate of type type according to data from a Map<String, Object>.
     *
     * @param objectMap      a Map of <String, Object> with data to update destination object. The string values
     *                       must match fields on the destination object.
     * @param objectToUpdate the object to update.
     *
     * @param type           the type of the objectToUpdate.
     */
    public static void mergeRequestBodyToGenericObject(Map<String, Object> objectMap, Object objectToUpdate, Class type) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        Object newObject = objectMapper.convertValue(objectMap, type);

        objectMap.forEach((fieldProp, valueProp) -> {
            Field field = ReflectionUtils.findField(type, fieldProp);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, newObject);

            ReflectionUtils.setField(field, objectToUpdate, newValue);
        });
    }
}
