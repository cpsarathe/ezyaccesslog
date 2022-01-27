package com.ezy.access.repository;

import com.ezy.access.model.AccessLogErrorModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessLogErrorRepository extends CrudRepository<AccessLogErrorModel, Long> {

    List<AccessLogErrorModel> findAll();
}
