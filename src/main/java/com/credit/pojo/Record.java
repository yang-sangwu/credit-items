package com.credit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "record")
@EntityListeners(AuditingEntityListener.class)
public class Record {
    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;
    @Column(name = "record_uid")
    private Integer recordUid;
    @Column(name = "record_appid")
    private Integer recordAppid;

}

