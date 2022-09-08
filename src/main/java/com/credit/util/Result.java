package com.credit.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {


    private String appName;

    private String appStuID;

    private String userGrade;

    private String appMajor;

    private String appClass;

    private String appType;

    private BigDecimal appScore;

    private BigDecimal appResult;

    private BigDecimal appRecognize;

    private BigDecimal userRemark;

}
