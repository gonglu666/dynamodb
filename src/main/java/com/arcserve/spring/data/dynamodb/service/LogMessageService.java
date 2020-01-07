package com.arcserve.spring.data.dynamodb.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.arcserve.spring.data.dynamodb.Util.DynamoDBUtil;
import com.arcserve.spring.data.dynamodb.model.LogMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Lu.Gong
 * @date 1/6/2020
 */
@Service
public class LogMessageService extends DynamodbBaseService{

    @Autowired
    DynamoDBUtil dynamoDBUtil;

//    public CreateTableResult createTable(){
//        return  dynamoDBUtil.createTable(LogMessage.class);
//    }


}
