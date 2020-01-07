package com.arcserve.spring.data.dynamodb.repositories;

import com.arcserve.spring.data.dynamodb.model.ProductInfo;
//import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@EnableScan
public interface ProductInfoRepository extends CrudRepository<ProductInfo, UUID>{

}
