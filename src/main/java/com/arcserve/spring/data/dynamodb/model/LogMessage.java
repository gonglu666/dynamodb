package com.arcserve.spring.data.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;

/**
 * @author Lu.Gong
 * @date 1/6/2020
 */
@DynamoDBTable(tableName = "LogMessage")
public class LogMessage {

    private UUID id;
    private String content;

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}