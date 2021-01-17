package org.noear.nami.coder.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.noear.nami.Encoder;
import org.noear.nami.common.Constants;

/**
 * @author noear 2021/1/17 created
 */
public class JacksonTypeEncoder implements Encoder {
    public static final JacksonTypeEncoder instance = new JacksonTypeEncoder();

    ObjectMapper mapper_type = new ObjectMapper();

    public JacksonTypeEncoder() {
        mapper_type.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper_type.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper_type.activateDefaultTypingAsProperty(
                mapper_type.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, "@type");
    }

    @Override
    public String enctype() {
        return Constants.ct_json;
    }

    @Override
    public byte[] encode(Object obj) {
        try {
            return mapper_type.writeValueAsBytes(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}