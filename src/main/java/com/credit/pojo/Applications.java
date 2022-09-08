package com.credit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="applications")
@EntityListeners(AuditingEntityListener.class)
public class Applications {
    @Id
    @Column(name="app_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appId;
    @Column(name="app_name")
    private String appName;
    @Column(name="app_sex")
    private String appSex;
    @Column(name="app_stuID")
    private String appStuID;
    @Column(name="app_major")
    private String appMajor;
    @Column(name="app_class")
    private String appClass;
    @Column(name="app_academic")
    private String appAcademic;
    @Column(name="app_type")
    private String appType;
    @Column(name="app_group")
    private String appGroup;
    @Column(name="app_group_count")
    private Integer appGroupCount;
    @Column(name="app_group_grade")
    private Integer appGroupGrade;
    @Column(name="app_score")
    private BigDecimal appScore;
    @Column(name="app_result")
    private BigDecimal appResult;
    @Column(name="app_recognize")
    private String appRecognize;
    @Column(name="app_content")
    private String appContent;
    @Column(name="app_user_name")
    private String appUserName;
    @Column(name="app_user_time")
    private String appUserTime;
    @Column(name="app_faculty_name")
    private String appFacultyName;
    @Column(name="app_faculty_time")
    private String appFacultyTime;
    @Column(name="app_class_name")
    private String appClassName;
    @Column(name="app_class_time")
    private String appClassTime;
    @Column(name="app_academic_score")
    private BigDecimal appAcademicScore;
    @Column(name="app_academic_name")
    private String appAcademicName;
    @Column(name="app_academic_time")
    private String appAcademicTime;
    @Column(name="app_pass")
    private Integer appPass;
    @Column(name="app_time")
    private String appTime;

    public Applications(String appName, String appSex, String appStuID, String appMajor,String appClass, String appAcademic, String appType, String appGroup, Integer appGroupCount, Integer appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime) {
        this.appName = appName;
        this.appSex = appSex;
        this.appStuID = appStuID;
        this.appClass = appClass;
        this.appAcademic = appAcademic;
        this.appType = appType;
        this.appMajor=appMajor;
        this.appGroup = appGroup;
        this.appGroupCount = appGroupCount;
        this.appGroupGrade = appGroupGrade;
        this.appScore = appScore;
        this.appResult = appResult;
        this.appRecognize = appRecognize;
        this.appContent = appContent;
        this.appUserName = appUserName;
        this.appUserTime = appUserTime;
        this.appFacultyName = appFacultyName;
        this.appFacultyTime = appFacultyTime;
        this.appClassName = appClassName;
        this.appClassTime = appClassTime;
        this.appAcademicScore = appAcademicScore;
        this.appAcademicName = appAcademicName;
        this.appAcademicTime = appAcademicTime;
    }

    public Applications(String appName, String appSex, String appStuID, String appMajor,String appClass, String appAcademic, String appType, String appGroup, Integer appGroupCount, Integer appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime, Integer appPass, String appTime) {
        this.appName = appName;
        this.appSex = appSex;
        this.appMajor=appMajor;
        this.appStuID = appStuID;
        this.appClass = appClass;
        this.appAcademic = appAcademic;
        this.appType = appType;
        this.appGroup = appGroup;
        this.appGroupCount = appGroupCount;
        this.appGroupGrade = appGroupGrade;
        this.appScore = appScore;
        this.appResult = appResult;
        this.appRecognize = appRecognize;
        this.appContent = appContent;
        this.appUserName = appUserName;
        this.appUserTime = appUserTime;
        this.appFacultyName = appFacultyName;
        this.appFacultyTime = appFacultyTime;
        this.appClassName = appClassName;
        this.appClassTime = appClassTime;
        this.appAcademicScore = appAcademicScore;
        this.appAcademicName = appAcademicName;
        this.appAcademicTime = appAcademicTime;
        this.appPass = appPass;
        this.appTime = appTime;
    }

    public Applications(String appName, String appSex, String appStuID,String appMajor, String appClass, String appAcademic, String appType, String appGroup, Integer appGroupCount, Integer appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime, String appTime) {
        this.appName = appName;
        this.appSex = appSex;
        this.appStuID = appStuID;
        this.appClass = appClass;
        this.appAcademic = appAcademic;
        this.appType = appType;
        this.appMajor=appMajor;
        this.appGroup = appGroup;
        this.appGroupCount = appGroupCount;
        this.appGroupGrade = appGroupGrade;
        this.appScore = appScore;
        this.appResult = appResult;
        this.appRecognize = appRecognize;
        this.appContent = appContent;
        this.appUserName = appUserName;
        this.appUserTime = appUserTime;
        this.appFacultyName = appFacultyName;
        this.appFacultyTime = appFacultyTime;
        this.appClassName = appClassName;
        this.appClassTime = appClassTime;
        this.appAcademicScore = appAcademicScore;
        this.appAcademicName = appAcademicName;
        this.appAcademicTime = appAcademicTime;
        this.appTime = appTime;
    }

    public Applications(Integer appId, String appName, String appSex, String appStuID,String appMajor, String appClass, String appAcademic, String appType, String appGroup, Integer appGroupCount, Integer appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime, String appTime) {
        this.appId = appId;
        this.appName = appName;
        this.appSex = appSex;
        this.appStuID = appStuID;
        this.appClass = appClass;
        this.appAcademic = appAcademic;
        this.appType = appType;
        this.appGroup = appGroup;
        this.appMajor=appMajor;
        this.appGroupCount = appGroupCount;
        this.appGroupGrade = appGroupGrade;
        this.appScore = appScore;
        this.appResult = appResult;
        this.appRecognize = appRecognize;
        this.appContent = appContent;
        this.appUserName = appUserName;
        this.appUserTime = appUserTime;
        this.appFacultyName = appFacultyName;
        this.appFacultyTime = appFacultyTime;
        this.appClassName = appClassName;
        this.appClassTime = appClassTime;
        this.appAcademicScore = appAcademicScore;
        this.appAcademicName = appAcademicName;
        this.appAcademicTime = appAcademicTime;
        this.appTime = appTime;
    }
}
