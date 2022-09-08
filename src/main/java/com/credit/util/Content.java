package com.credit.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private Integer staId;

    private String staName;

    private Integer staFatherid;

    private BigDecimal staCredit;

    private String staRemark;
}
