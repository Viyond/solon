package org.noear.nami.coder.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.noear.nami.Decoder;
import org.noear.nami.NamiConfig;
import org.noear.nami.common.Constants;
import org.noear.nami.common.Result;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author noear 2021/1/17 created
 */
public class JacksonDecoder implements Decoder {
    public static final JacksonDecoder instance = new JacksonDecoder();


    ObjectMapper mapper = new ObjectMapper();

    public JacksonDecoder(){
        mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public String enctype() {
        return Constants.ct_json;
    }

    @Override
    public <T> T decode(Result rst, Type type) {
        String str = rst.bodyAsString();

        Object returnVal = null;
        try {
            if (str == null) {
                return (T) str;
            }
            returnVal = mapper.readValue(str, new TypeReferenceImp(type));

        } catch (Throwable ex) {
            returnVal = ex;
        }

        if (returnVal != null && Throwable.class.isAssignableFrom(returnVal.getClass())) {
            if (returnVal instanceof RuntimeException) {
                throw (RuntimeException) returnVal;
            } else {
                throw new RuntimeException((Throwable) returnVal);
            }
        } else {
            return (T) returnVal;
        }
    }

    @Override
    public void filter(NamiConfig cfg, String action, String url, Map<String, String> headers, Map<String, Object> args) {
        headers.put(Constants.h_serialization, Constants.at_type_json);
        headers.put(Constants.h_accept, Constants.ct_json);
    }
}