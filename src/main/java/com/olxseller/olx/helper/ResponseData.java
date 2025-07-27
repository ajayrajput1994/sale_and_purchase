package com.olxseller.olx.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.olxseller.olx.model.Blog;

@Component
public class ResponseData {

  public String jsonSimpleResponse(String status, String msg, String action, Object obj) {
    HashMap<String, Object> map = new HashMap<>();
    String data = "";
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    map.put("responseText", status);
    map.put("message", msg);
    map.put("action", action);
    map.put("data", obj);
    try {
      data = mapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      System.out.println("Error:jsonDataResponse," + e);
      e.printStackTrace();
    }
    return data;
  }

  public String jsonSimpleBlogResponse(String status, String msg, String action, Blog obj) {
    HashMap<String, Object> map = new HashMap<>();
    String data = "";
    ObjectMapper mapper = new ObjectMapper();
    map.put("responseText", status);
    map.put("message", msg);
    map.put("action", action);
    map.put("data", obj);
    map.put("userid", obj.getUser().getId());
    try {
      data = mapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      System.out.println("Error:jsonDataResponse," + e);
      e.printStackTrace();
    }
    return data;
  }

  public String jsonDataResponse(String status, String msg, Object obj) {
    HashMap<String, Object> map = new HashMap<>();
    String data = "";

    // Create ObjectMapper and register JavaTimeModule
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    map.put("responseText", status);
    map.put("message", msg);
    map.put("data", obj);

    try {
      data = mapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      System.out.println("Error: jsonDataResponse, " + e);
      e.printStackTrace();
    }
    return data;
  }

  public Object getObjectFromJson(String jsonString) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);
      if (jsonNode.isObject()) {
        return objectMapper.convertValue(jsonNode, Map.class);
      } else if (jsonNode.isArray()) {
        return objectMapper.convertValue(jsonNode, List.class);
      } else {
        System.out.println("Input is neither a Map nor a List.");
        System.out.println("Raw Value: " + jsonNode.toString());
        return jsonNode.toString();
      }
    } catch (Exception e) {
      return null;
    }
  }

  public String getJsonStringFromObject(Object obj) {
    String str = "";
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      str = objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return str;
  }

  public List<Integer> getIntKeysFromMap(String jsoString) {
    List<Integer> list = new ArrayList<>();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsoString);
      Map<String, Object> map = objectMapper.convertValue(jsonNode, Map.class);
      if (map.containsKey("default")) {
        list = (List<Integer>) map.get("default");
      } else {
        for (String key : map.keySet()) {
          // System.out.println("Key: " + key);
          try {
            Integer intKey = Integer.valueOf(key);
            list.add(intKey);
          } catch (NumberFormatException e) {
            System.err.println("Invalid key format: " + key);
          }
        }

      }
      // System.out.println("Final List: " + list);
    } catch (Exception e) {
      e.printStackTrace();
      return list;
    }
    return list;

  }

  public List<Integer> getItemIdsFromOrdsetItemsString(String jsonString) {
    List<Integer> list = new ArrayList<>();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      List<Map<String, Object>> jsonList = objectMapper.readValue(jsonString,
          new TypeReference<List<Map<String, Object>>>() {
          });
      for (Map<String, Object> item : jsonList) {
        try {
          Integer intKey = Integer.valueOf(item.get("id").toString());
          list.add(intKey);
        } catch (NumberFormatException e) {
          System.err.println("Invalid key format: " + item.get("id"));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

}
