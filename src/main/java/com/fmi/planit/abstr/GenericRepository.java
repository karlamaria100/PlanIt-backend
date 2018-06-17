package com.fmi.planit.abstr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

public interface GenericRepository<T, PK extends Serializable>
        extends JpaRepository<T, PK>,
        CrudRepository<T, PK>,
        PagingAndSortingRepository<T, PK>,
        JpaSpecificationExecutor<T> {


}