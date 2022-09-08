package com.credit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorization")
@EntityListeners(AuditingEntityListener.class)
public class Authorization {
    @Id
    @Column(name = "auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authId;

    @Column(name = "auth_userid")
    private Integer authUserid;

    @Column(name = "auth_message")
    private String authMessage;

    @Column(name = "auth_status")
    private Integer authStatus;


}
