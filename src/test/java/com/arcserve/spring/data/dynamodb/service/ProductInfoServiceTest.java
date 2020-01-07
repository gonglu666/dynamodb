package com.arcserve.spring.data.dynamodb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.arcserve.DynamodbApplication;
import com.arcserve.spring.data.dynamodb.model.LogMessage;
import com.arcserve.spring.data.dynamodb.model.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Lu.Gong
 * @date 1/6/2020
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DynamodbApplication.class)
public class ProductInfoServiceTest {

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    private UUID jvmId = UUID.fromString("b5fdf42f-2eb3-4cb2-9662-5848bc6e9af6");



    /*
        {TableDescription:

            {AttributeDefinitions: [{AttributeName: id,AttributeType: S}],
            TableName: ProductInfo,
            KeySchema: [{AttributeName: id,KeyType: HASH}],
            TableStatus: ACTIVE,
            CreationDateTime: Mon Jan 06 14:52:21 CST 2020,
            ProvisionedThroughput: {LastIncreaseDateTime: Thu Jan 01 08:00:00 CST 1970,LastDecreaseDateTime: Thu Jan 01 08:00:00 CST 1970,NumberOfDecreasesToday: 0,ReadCapacityUnits: 1,WriteCapacityUnits: 1},
            TableSizeBytes: 0,
            ItemCount: 0,
     */
    @Test
    public void createTable() throws Exception {
        CreateTableResult table = productInfoService.createTable(ProductInfo.class);
        System.out.println(table);

    }

    @Test
    public void deleteTable() throws Exception {
        productInfoService.deleteTable(ProductInfo.class);
    }

    @Test
    public void listTables() {
        List<String> tables = productInfoService.listTables();
        System.out.println("Tables:"+tables);
    }

    @Test
    public void addEntity() throws Exception {
//        ProductInfo info = new ProductInfo("100","javascript");
//        ProductInfo productInfo = productInfoService.addEntity(info);

        ProductInfo info = new ProductInfo("1000","jvm");
        info.setId(jvmId);
        ProductInfo productInfo = productInfoService.addEntity(info);

        System.out.println(productInfo);
    }

    @Test
    public void addEntitys() throws Exception {
        List<ProductInfo> list = new ArrayList<ProductInfo>();
        list.add(new ProductInfo("300","ps"));
        list.add(new ProductInfo("400","angular"));
        productInfoService.addEntitys(list);

        System.out.println("Add entitys success");
    }

    @Test
    public void deleteEntity() throws Exception {
        ProductInfo info = new ProductInfo("500","jvm2");
        info.setId(jvmId);

        productInfoService.deleteEntity(info);

        System.out.println("Delete entity success");

    }

    @Test
    public void deleteEntitys() throws Exception {
        List<ProductInfo> list = new ArrayList<ProductInfo>();
        ProductInfo info1 = new ProductInfo("700","node");
        info1.setId(UUID.fromString("48f91fe7-a480-45a9-a388-5c7acb9b214c"));
        ProductInfo info2 = new ProductInfo("800","css");
        info2.setId(UUID.fromString("9330e8d3-7a62-4c5a-8119-72abaad84117"));
        list.add(info1);
        list.add(info2);
        productInfoService.addEntitys(list);

        //delete the entitys only by id
        List<ProductInfo> list2 = new ArrayList<ProductInfo>();
        list2.add(new ProductInfo(UUID.fromString("48f91fe7-a480-45a9-a388-5c7acb9b214c")));
        list2.add(new ProductInfo(UUID.fromString("9330e8d3-7a62-4c5a-8119-72abaad84117")));
        productInfoService.deleteEntitys(list2);


    }

    @Test
    public void findEntity() throws Exception {
        Optional<ProductInfo> entity = productInfoService.findEntity(jvmId);
        System.out.println(entity.get()!=null?entity.get():"none entity");

    }

    @Test
    public void queryAll() throws Exception {
        Iterable<ProductInfo> productInfos = productInfoService.queryAll();
        productInfos.forEach(s-> System.out.println(s));
    }

    @Test
    public void updateItem() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("cost","6666");
        map.put("other","note");
        productInfoService.updateItem(jvmId, map);

        System.out.println("update item success");

    }

    @Test
    public void queryItem() {
        GetItemResult getItemResult = productInfoService.queryItems(jvmId);
        getItemResult.getItem().forEach((k,v)->{
            System.out.println(k+"--"+v.getS());
        });
    }


    @Test
    public void queryByParameters() {
        List<JSONObject> list = productInfoService.queryFromParameters("jvm", "8888");
        list.forEach(s->{
            System.out.println(s);
        });
    }



}