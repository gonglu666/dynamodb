package com.arcserve.spring.data.dynamodb.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;


@Configuration
@EnableDynamoDBRepositories(basePackages = "com.arcserve.spring.data.dynamodb.repositories")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;
    
    @Autowired
    private ApplicationContext context;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());
        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }



//        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
//                .withClientConfiguration(new ClientConfiguration().withRequestTimeout(5000))
//                .withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials()))
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint,Regions.US_EAST_1.getName()))
//                .build();


        return amazonDynamoDB;

    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

//    @Bean
//    public DynamoDBMapper getDynamoDBMapper(){
//        DynamoDBMapper dynamoDBMapper =  new DynamoDBMapper((AmazonDynamoDB)context.getBean("amazonDynamoDB"));
//        return dynamoDBMapper;
//    }

//  @Bean(name = "mvcHandlerMappingIntrospector")
//	public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
//		return new HandlerMappingIntrospector(context);
//	}
}
