package com.credit.repository;

import com.credit.pojo.Authorization;
import com.credit.pojo.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocationRepository  extends JpaRepository<Location, Integer>, JpaSpecificationExecutor {
}
