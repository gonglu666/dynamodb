package com.arcserve.spring.data.dynamodb.contrallor;

import com.arcserve.spring.data.dynamodb.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kaonglu
 * 2020/1/5
 */
@RestController
public class DynamodbApi {

    @Autowired
    ProductInfoService productInfoService;

    @RequestMapping("/api/add")
    public void addEntity(){
    }

    @RequestMapping("/api/delete")
    public void deleteEntity(){
    }

    @RequestMapping("/api/update")
    public void updateEntity(){
    }

    @RequestMapping("/api/query")
    public void queryEntity(){
    }

    @RequestMapping("/api/createtable")
    public void createTable(){

    }

}
