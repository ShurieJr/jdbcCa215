package com.jdbcCA215;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    String sqlQuery ="";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedPJdbc;

    public CustomerService(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedPJdbc) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedPJdbc = namedPJdbc;
    }

    //operations
    //get all customers
    public List<Customer> getAllCustomers() {
        sqlQuery = "select * from customer";
        return jdbcTemplate.query(sqlQuery,
                new BeanPropertyRowMapper<>(Customer.class));
    }

    //get by id
//    public Customer getCustomerById(int id) {
//        sqlQuery = "select * from customer  where id = ?";
//        return jdbcTemplate.queryForObject(sqlQuery,
//                new Object[]{id},
//                new BeanPropertyRowMapper<>(Customer.class)
//                );
//    }
    public Customer getCustomerById(int id) {
        sqlQuery = "select * from customer  where id = :id";
        MapSqlParameterSource parmameters = new MapSqlParameterSource()
                .addValue("id", id);
        return namedPJdbc.queryForObject(sqlQuery,
                parmameters,
                new BeanPropertyRowMapper<>(Customer.class)
                );
    }
    //insert customer
    public void saveCustomer(Customer customer) {
        sqlQuery = "insert into customer values (? , ?)";
        jdbcTemplate.update(sqlQuery, customer.getId(), customer.getName());
    }
    //update
//    public void updateCustomer(Customer customer){
//        sqlQuery = "update customer set name = ? where id = ?";
//        jdbcTemplate.update(sqlQuery , customer.getName(),customer.getId());
//    }
    public void updateCustomer(Customer customer){
        sqlQuery = "update customer set name = :name where id = :id";
       MapSqlParameterSource source = new MapSqlParameterSource()
               .addValue("id" , customer.getId())
               .addValue("name" , customer.getName());
       namedPJdbc.update(sqlQuery , source);

    }
    //delete customer by id
//    public void deleteCustomer(int customerId){
//        sqlQuery = "delete from customer where id = ?";
//        jdbcTemplate.update(sqlQuery, customerId);
//    }
    public void deleteCustomer(int customerId){
        sqlQuery = "delete from customer where id = :id";
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("id" , customerId);
        namedPJdbc.update(sqlQuery, source);
    }
}
