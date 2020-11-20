package com.zetcode.repository;

import com.zetcode.model.Car;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface ICarRepository {

    void saveCar(Car car);
    Car findCarByName(String name);
    List<Car> findAll();
    ConcurrentHashMap<String,Car> selectAll();
}
