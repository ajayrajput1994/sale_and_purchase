package com.olxseller.olx.helper;
import java.util.HashMap;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olxseller.olx.model.Blog;

@Component
public class ResponseData { 

  public String jsonSimpleResponse(String status, String msg,String action, Object obj) {
    HashMap<String, Object> map = new HashMap<>();
    String data="";
    ObjectMapper mapper=new ObjectMapper();
    map.put("responseText", status);
    map.put("message", msg);
    map.put("action", action);
    map.put("data", obj); 
    try {
      data=mapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      System.out.println("Error:jsonDataResponse,"+e);
      e.printStackTrace();
    }
    return data;
  }
  public String jsonSimpleBlogResponse(String status, String msg,String action, Blog obj) {
    HashMap<String, Object> map = new HashMap<>();
    String data="";
    ObjectMapper mapper=new ObjectMapper();
    map.put("responseText", status);
    map.put("message", msg);
    map.put("action", action);
    map.put("data", obj); 
    map.put("userid", obj.getUser().getId()); 
    try {
      data=mapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      System.out.println("Error:jsonDataResponse,"+e);
      e.printStackTrace();
    }
    return data;
  }

  public String jsonDataResponse(String status,String msg,Object obj){
    HashMap<String,Object> map=new HashMap<>();
    String data="";
    ObjectMapper mapper=new ObjectMapper();
    map.put("responseText", status);
    map.put("message", msg);
    map.put("data", obj);
    try {
      data=mapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      System.out.println("Error:jsonDataResponse,"+e);
      e.printStackTrace();
    }
    return data;
  }
}
