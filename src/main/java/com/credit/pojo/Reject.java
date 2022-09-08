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
@Table(name="reject")
@EntityListeners(AuditingEntityListener.class)
public class Reject {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="app_id")
    private Integer appId;
    @Column(name="code")
    private String code;
    @Column(name="reason")
    private String reason;
    @Column(name="times")
    private String times;

    public Reject(Integer appId, String code, String reason, String times) {
        this.appId = appId;
        this.code = code;
        this.reason = reason;
        this.times = times;
    }
}
