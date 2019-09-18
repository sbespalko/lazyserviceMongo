package com.sbt.lazyservice.server.repository;
import com.sbt.lazyservice.server.domain.History;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the History entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoryRepository extends MongoRepository<History, String> {

}
