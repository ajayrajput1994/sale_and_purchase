package com.olxseller.olx.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

  public HashMap<String,Object> jsonCategoryResponse(String status,String msg,List<Category> obj){
    HashMap<String,Object> map=new HashMap<>();
    List<HashMap<String,String>> catmap=new ArrayList<HashMap<String,String>>();
    HashMap<String,String> value=new HashMap<>();
    obj.forEach(e->{
      value.put("status", e.toString());
    });
    catmap.add(value);
    map.put("responseText", status);
    map.put("message", msg);
    map.put("data", catmap);
    return map;
  }
}
