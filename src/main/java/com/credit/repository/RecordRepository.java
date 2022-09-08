package com.credit.repository;

import com.credit.pojo.Record;
import com.credit.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface RecordRepository extends JpaRepository<Record, Integer>, JpaSpecificationExecutor {


}
