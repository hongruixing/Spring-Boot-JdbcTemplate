package com.zetcode.service;

import com.zetcode.model.Car;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface ICarService {

    Car findByName(String name);
    List<Car> findAll();
    ConcurrentHashMap<String,Car> selectAll();
}
