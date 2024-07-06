package utils.json_utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.file_helper.FileHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        mapper.registerModule(new JavaTimeModile());
    }

    public static void configureDeserializationFeature(DeserializationFeature feature, Boolean state) {
        mapper.configure(feature,state);
    }

    public static void configureSerializationInclusion(JsonInclude.Include inclusion) {
        mapper.setSerializationInclusion(inclusion);
    }

    public static <T> List<T> deserializeListFromJson(String json, Class clazz) {
        try {
            List preDeserialisedList = mapper.readValue(json, new TypeReference<List>() {
            });
            List deserialisedList = new ArrayList<>();
            for (Object i : preDeserialisedList) {
                deserialisedList.add(mapper.convertValue(i, clazz));
            }
            return deserialisedList;
        } catch (IOException e) {
            log.error("Не удалось десериализовать Java-объект " + clazz + " из JSON формата", e);
            return null;
        }
    }

    public static <T> List<T> deserializeListFromJson (Path json, Class clazz) {
        return deserializeListFromJson(FileHelper.readFileAsString(json.toFile()),clazz);
    }

    public static <T> T deserializeFromJson(String json, Class clazz) {
        try {
            return (T) mapper.readValue(json,clazz);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось десериализовать Java-объект " + clazz + " из JSON формата", e);
        }
    }

    private static String serializeJson(Object json, boolean prettyPrint) {
        try {
            if (prettyPrint) return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(json);
            else return mapper.writer().writeValueAsString(json);
        } catch (IOException e) {
            log.error("Не удалось сериализовать Java-объект " + json, e);
            return null;
        }
    }

    public static String serializeJson(Object json) {
        return serializeJson(json,true);
    }
}
