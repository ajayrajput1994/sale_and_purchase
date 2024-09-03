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

  public HashMap<String,Object> jsonCategoryResponse(String status,String msg,List<MainCategory> obj){
    HashMap<String,Object> map=new HashMap<>();
    List<Object> list=new ArrayList<>();
    // List<HashMap<String,String>> catmap=new ArrayList<HashMap<String,String>>();
    // HashMap<String,Object> value=new HashMap<>();
    ObjectMapper mapper=new ObjectMapper();
    obj.forEach(e->{
      // value.put("id",e.getMainId());
      // value.put("cat",e.getMainCatalog());
      // value.put("title",e.getTitle());
      // value.put("keyword",e.getKeyword());
      // value.put("desc",e.getDescription());
      // value.put("img",e.getImage());
      try {
        list.add(mapper.writeValueAsString(e));
      } catch (JsonProcessingException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      // list.add(value);
    });
    map.put("responseText", status);
    map.put("message", msg);
    map.put("data", list);
    return map;
  }
}
