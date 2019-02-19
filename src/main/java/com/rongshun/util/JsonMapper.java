package com.rongshun.util;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMapper
{
    private static final Logger log = LoggerFactory.getLogger(JsonMapper.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    static
    {
        objectMapper.disable(new DeserializationConfig.Feature[] { DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES });
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    public static <T> String obj2String(T src)
    {
        if (src == null) {
            return null;
        }
        try
        {
            return (src instanceof String) ? (String)src : objectMapper.writeValueAsString(src);
        }
        catch (Exception e)
        {
            log.warn("parse object to String exception, error:{}", e);
        }
        return null;
    }

    public static <T> T string2Obj(String src, TypeReference<T> typeReference)
    {
        if ((src == null) || (typeReference == null)) {
            return null;
        }
        try
        {
//            return typeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, typeReference);
        }
        catch (Exception e)
        {
            log.warn("parse String to Object exception, String:{}, TypeReference<T>:{}, error:{}", new Object[] { src, typeReference.getType(), e });
        }
        return null;
    }
}
