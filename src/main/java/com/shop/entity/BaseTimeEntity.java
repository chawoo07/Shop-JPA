package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})//Auditing適用
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate            //Entityが生成して貯蔵する時自動で貯蔵する
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate       //Entityの値を変える時時間を自動で貯蔵
    private LocalDateTime updateTme;
}
