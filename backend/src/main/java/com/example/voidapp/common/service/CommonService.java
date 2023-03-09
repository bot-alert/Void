package com.example.voidapp.common.service;

import java.util.List;

public interface CommonService<T> {
  T getById(String id);
  List<T> getAll();
}
