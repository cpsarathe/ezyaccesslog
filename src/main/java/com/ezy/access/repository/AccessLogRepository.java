package com.ezy.access.repository;

import com.ezy.access.model.AccessLogModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessLogRepository extends CrudRepository<AccessLogModel, Long> {

    List<AccessLogModel> findAll();
}
