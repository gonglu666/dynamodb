package com.arcserve.spring.data.dynamodb.service;

import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.arcserve.DynamodbApplication;
import com.arcserve.spring.data.dynamodb.model.LogMessage;
import com.arcserve.spring.data.dynamodb.model.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Lu.Gong
 * @date 1/6/2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DynamodbApplication.class)
public class LogMessageServiceTest {

    @Autowired
    private LogMessageService logMessageService;

    @Test
    public void createTable() throws Exception {


    }
}