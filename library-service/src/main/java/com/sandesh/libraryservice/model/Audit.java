package com.sandesh.libraryservice.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class Audit implements Serializable {

    @CreatedDate
    private Date createdAt;
    @CreatedBy
    private Integer createdBy;
    @LastModifiedDate
    private Date updatedAt;
    @LastModifiedBy
    private Integer updatedBy;
}
