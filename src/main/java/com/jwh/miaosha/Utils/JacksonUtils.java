package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


/**
 * @author ：xxx
 * @description：TODO
 * @date ：2022/2/12 20:59
 */
@Slf4j
public class JacksonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T reverseSer(String json, Class<T> className) throws IOException {
        try {
            T res = objectMapper.readValue(json, className);
            return res;
        } catch (Exception e) {
            log.info(json + ":{}", "jsonReverseSerError");
            throw e;
        }
    }

    public static <T> String transform(T object) throws JsonProcessingException{
        try {
            String string = objectMapper.writeValueAsString(object);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }

    }
}
