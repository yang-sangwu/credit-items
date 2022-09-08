package com.credit.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordUtil {

    private Integer recordId;

    private Integer recordAppid;

    private String appName;

    private String appStuID;

    private String appClass;

    private String appType;

    private String appGroup;

    private Integer appGroupCount;

    private Integer appGroupGrade;

    private BigDecimal appScore;

    private String appTime;

    private Integer appPass;

}
