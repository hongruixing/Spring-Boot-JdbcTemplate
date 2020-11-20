package com.zetcode.repository;

import com.zetcode.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CarRepository implements ICarRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveCar(Car car) {

        var sql = "INSERT INTO cars(name, price) VALUES (?, ?)";
        Object[] params = new Object[] {car.getName(), car.getPrice()};

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Car findCarByName(String name) {

        var sql = "SELECT * FROM cars WHERE name = ?";
        Object[] param = new Object[] {name};

        return jdbcTemplate.queryForObject(sql, param,
                BeanPropertyRowMapper.newInstance(Car.class));
    }

    @Override
    public List<Car> findAll() {

        var sql = "SELECT * FROM cars";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Car.class));
    }
    public ConcurrentHashMap<String,Car> selectAll(){
        ConcurrentHashMap<String,Car> map = new ConcurrentHashMap<>();
        jdbcTemplate.query("SELECT * FROM cars ", new ResultSetExtractor<ConcurrentHashMap<String,Car>>(){
            @Override
            public ConcurrentHashMap<String,Car> extractData(
                    ResultSet rs) throws SQLException, DataAccessException {
                while(rs.next()){
                    long id = rs.getLong("id");
                    AtomicLong atomicLong = new AtomicLong();
                    atomicLong.set(0);
                    map.put(rs.getString("hashStr"),new Car(id, atomicLong.intValue()));
                }
                return map;
            }
        });
        return map;
    }
}
