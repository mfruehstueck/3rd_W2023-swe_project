package at.onlyquiz.util.jsonParser;

import at.debugtools.DebugTools;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSON_Parser {
  private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

  public static <T> void write(File jsonFile, T jsonObject) {
    try {
      List<T> existingJsonObjects = mapper.readValue(
              jsonFile,
              mapper.getTypeFactory().constructCollectionType(List.class, jsonObject.getClass())
      );

      existingJsonObjects.add(jsonObject);

      mapper.writeValue(jsonFile, existingJsonObjects);
    } catch (IOException e) {
      System.out.println(DebugTools.debugLine(new Throwable()));
    }
  }

  public static <T> List<T> read(File jsonFile, Class<T> clazz) {
    try {
      return mapper.readValue(
              jsonFile,
              mapper.getTypeFactory().constructCollectionType(List.class, clazz)
      );
    } catch (IOException e) {
      System.out.println(DebugTools.debugLine(new Throwable()));
      return new ArrayList<>();
    }
  }
}
