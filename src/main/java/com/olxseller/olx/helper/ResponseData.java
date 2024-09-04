package com.olxseller.olx.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.repository.BlogRepository;

@Component
public class ResponseData {
  @Autowired
  private BlogRepository blogRepo;

  public HashMap<String, Object> jsonSimpleResponse(String status, String msg,String action, Object obj) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("responseText", status);
    map.put("message", msg);
    map.put("action", action);
    map.put("data", obj);
    return map;
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
