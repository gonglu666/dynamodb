package com.arcserve.spring.data.dynamodb.repositories;

import com.arcserve.spring.data.dynamodb.model.LogMessage;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Lu.Gong
 * @date 1/6/2020
 */
@EnableScan
public interface LogMessageRepository extends CrudRepository<LogMessage, UUID> {

}
