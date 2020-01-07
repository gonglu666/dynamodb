package com.arcserve.spring.data.dynamodb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.arcserve.spring.data.dynamodb.model.ProductInfo;
import com.arcserve.spring.data.dynamodb.repositories.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author Lu.Gong
 * @date 1/6/2020
 */
@Service
public class ProductInfoService extends DynamodbBaseService{



    @Autowired
    private ProductInfoRepository repository;


    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

    private DynamoDB dynamoDB ;

    private Table table ;

    @PostConstruct
    public void init(){

        dynamoDB = new DynamoDB(amazonDynamoDB);

        table = dynamoDB.getTable("ProductInfo");
    }


    /*
     *  create table
     */
    public CreateTableResult createTable(Class<?> type) {
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(type);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        CreateTableResult table = amazonDynamoDB.createTable(tableRequest);
        return table;
    }

    /*
     *  delete table
     */
    public void deleteTable(Class<?> type) {
        DeleteTableRequest deleteTableRequest = dynamoDBMapper.generateDeleteTableRequest(type);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }

    public ProductInfo addEntity(ProductInfo productInfo) {
        ProductInfo save = repository.save(productInfo);
        return save;
    }

    public void addEntitys(List<ProductInfo> list) {
        repository.saveAll(list);
    }

    public void deleteEntity(ProductInfo productInfo) {
        repository.delete(productInfo);
    }

    public void deleteEntitys(List<ProductInfo> list) {
        repository.deleteAll(list);
    }

    public Optional<ProductInfo> findEntity(UUID id) {
        Optional<ProductInfo> byId = repository.findById(id);
        return byId;
    }



    public Iterable<ProductInfo> queryAll(){
        Iterable<ProductInfo> all = repository.findAll();
        return all;
    }

    public List<String> listTables() {
        ListTablesResult listTablesResult = amazonDynamoDB.listTables();
        return listTablesResult.getTableNames();
    }

    public UpdateItemResult updateItem(UUID id,Map<String,String> map) {
        if(id==null) {
            // TODO
            return null;
        }
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("id",new AttributeValue(id.toString()));
        Map<String, AttributeValueUpdate> itemUpdate = new HashMap<String, AttributeValueUpdate>();
        map.forEach((k,v)->{itemUpdate.put(k,new AttributeValueUpdate(new AttributeValue(v),"PUT"));});

        UpdateItemResult updateItemResult = amazonDynamoDB.updateItem(ProductInfo.class.getSimpleName(), item, itemUpdate);
        return updateItemResult;
    }


    public GetItemResult queryItems(UUID id){

        Map<String, AttributeValue> itemRequest = new HashMap<String, AttributeValue>();
        itemRequest.put("id",new AttributeValue(id.toString()));
        GetItemResult item = amazonDynamoDB.getItem(ProductInfo.class.getSimpleName(),itemRequest);

        return item;

    }



    public List<JSONObject> queryFromParameters(String name, String cost) {
        QuerySpec querySpec = new QuerySpec();

        querySpec
                .withKeyConditionExpression("name = :name")
                .withFilterExpression("#cost = :cost")
                .withNameMap(new NameMap().with("#cost", "cost"))
                .withValueMap(new ValueMap()
                        .withString(":name", name)
                        .withString(":cost", cost)
                )
                .withScanIndexForward(false)
                .withMaxPageSize(10);

        return handleResultForIndex(this.table.getIndex("name"), querySpec);
    }

    private List<JSONObject> handleResultForIndex(Index index, QuerySpec querySpec) {
        try {
            List<JSONObject> result = new ArrayList<JSONObject>();

            ItemCollection<QueryOutcome> items = index.query(querySpec);

            Iterator<Item> iterator = items.iterator();

            while (iterator.hasNext()) {
                System.out.println(iterator.next().toJSONPretty());

                result.add(JSON.parseObject(iterator.next().toJSON()));
            }

            return result;
        } catch (Exception e) {
            System.err.println("Unable to query the table:");
            System.err.println(e.getMessage());

            return new ArrayList<JSONObject>();
        }

    }






}
