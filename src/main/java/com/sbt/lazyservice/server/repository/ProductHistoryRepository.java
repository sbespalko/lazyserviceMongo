package com.sbt.lazyservice.server.repository;
import com.sbt.lazyservice.server.domain.ProductHistory;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ProductHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductHistoryRepository extends MongoRepository<ProductHistory, String> {

}
