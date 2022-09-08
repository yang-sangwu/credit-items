package com.credit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "standard")
public class Standard {
    @Id
    @Column(name="sta_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staId;
    @Column(name="sta_name")
    private String staName;
    @Column(name="sta_grade")
    private Integer staGrade;
    @Column(name="sta_fatherid")
    private Integer staFatherid;
    @Column(name="sta_credit")
    private BigDecimal staCredit;
    @Column(name="sta_remark")
    private String staRemark;
}
