package com.credit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="inquiry")
@EntityListeners(AuditingEntityListener.class)
public class Inquiry {
    @Id
    @Column(name="in_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  inId;
    @Column(name="app_id")
    private Integer appId;
    @Column(name="in_type")
    private String inType;
    @Column(name="in_scope")
    private String inScope;
    @Column(name="in_score")
    private BigDecimal inScore;
}
