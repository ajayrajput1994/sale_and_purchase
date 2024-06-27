package com.olxseller.olx.service;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface GenricService<T>{
  T create(T ob);
  T getByID(int id);
  T update(T ob,int id);
  void delete(int id);
  List<T> getAll();
}
