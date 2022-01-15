package com.ezy.access.repository;

import com.ezy.access.model.AccessLogModelV2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogV2Repository extends CrudRepository<AccessLogModelV2, Long> {

}
