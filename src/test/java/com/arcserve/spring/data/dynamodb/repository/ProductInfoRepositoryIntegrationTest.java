package com.arcserve.spring.data.dynamodb.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.arcserve.DynamodbApplication;

import com.arcserve.spring.data.dynamodb.model.ProductInfo;
import com.arcserve.spring.data.dynamodb.repositories.ProductInfoRepository;
//import com.arcserve.spring.data.dynamodb.repository.rule.LocalDbCreationRule;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DynamodbApplication.class)
public class ProductInfoRepositoryIntegrationTest {



    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    ProductInfoRepository repository;

    private UUID productId = UUID.fromString("b5fdf42f-2eb3-4cb2-9662-5848bc6e9af6");


    @Before
    public void setup() throws Exception {

        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

    }

    @Test
    public void givenItemWithExpectedCost_whenRunFindAll_thenItemIsFound() {
        List<ProductInfo> result = (List<ProductInfo>) repository.findAll();

        System.out.println("success");
    }

    @Test
    public void createTable(){

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(ProductInfo.class);

        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        amazonDynamoDB.createTable(tableRequest);

        System.out.println("create table success");
    }

    @Test
    public void deleteTable(){

        DeleteTableRequest deleteTableRequest = dynamoDBMapper.generateDeleteTableRequest(ProductInfo.class);

        amazonDynamoDB.deleteTable(deleteTableRequest);

        System.out.println("delete table success");
    }

    @Test
    public void queryTable(){
        ListTablesResult listTablesResult = amazonDynamoDB.listTables();
        for (String tablename:listTablesResult.getTableNames()
             ) {
            System.out.println(tablename);
        }
    }

    @Test
    public void addItem(){


        PutItemRequest putItemRequest = new PutItemRequest();
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("id",new AttributeValue(productId.toString()));
        item.put("name",new AttributeValue("arcserve"));
        item.put("cost",new AttributeValue("100"));
        item.put("test",new AttributeValue("test"));
        putItemRequest.setTableName(ProductInfo.class.getSimpleName());
        putItemRequest.setItem(item);
        amazonDynamoDB.putItem(putItemRequest);

        System.out.println("add item success");


    }

    @Test
    public void queryItems(){

        Map<String, AttributeValue> itemRequest = new HashMap<String, AttributeValue>();
        itemRequest.put("id",new AttributeValue(productId.toString()));
//        itemRequest.put("name",new AttributeValue("arcserve"));
        GetItemResult item = amazonDynamoDB.getItem(ProductInfo.class.getSimpleName(),itemRequest);
        for(Map.Entry entry : item.getItem().entrySet()) {
            System.out.println(entry.getKey()+"--"+entry.getValue());
        }

        System.out.println("query item success");
    }

    @Test
    public void deleteItems(){

        Map<String, AttributeValue> itemDelete = new HashMap<String, AttributeValue>();
        itemDelete.put("id",new AttributeValue(productId.toString()));
        amazonDynamoDB.deleteItem(ProductInfo.class.getSimpleName(),itemDelete);

        System.out.println("delete item success");
    }

    @Test
    public void updateItems(){
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("id",new AttributeValue(productId.toString()));
        Map<String, AttributeValueUpdate> itemUpdate = new HashMap<String, AttributeValueUpdate>();
//        itemUpdate.put("id",new AttributeValueUpdate(new AttributeValue("123456"),"PUT"));
        itemUpdate.put("cost",new AttributeValueUpdate(new AttributeValue("200"),"PUT"));
        amazonDynamoDB.updateItem(ProductInfo.class.getSimpleName(),item,itemUpdate);
        System.out.println("update item success");
    }



    @Test
    public void test(){

        Iterable<ProductInfo> all = repository.findAll();
        Iterator<ProductInfo> iterator = all.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }







}
