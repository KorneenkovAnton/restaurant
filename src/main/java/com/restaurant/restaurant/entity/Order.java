package com.restaurant.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "order")
@Data
public class Order extends BaseEntity {
    private Date date;
    private Integer amount;
    private String status;
    private String info;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private com.restaurant.restaurant.entity.Table table;

    
    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_details",
    joinColumns = {@JoinColumn(name = "order_id",referencedColumnName = "id")},
    inverseJoinColumns ={@JoinColumn(name = "dish_id",referencedColumnName = "id")})
    List<Dish> dishes;

    @Transient
    private Map<Dish,Integer> getCountOfDishes(){
        Map<Dish,Integer>dishMap = new HashMap<>();
        for (Dish dish:dishes
             ) {
            dishMap.compute(dish,(key,val)->(val == null)?1: val + 1);
        }
        return dishMap;
    }*/


}
