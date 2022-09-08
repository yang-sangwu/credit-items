package com.credit.pojo;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name="user_name")
    private String userName;
    @Column(name="user_sex")
    private String userSex;
    @Column(name="user_code")
    private String userCode;
    @Column(name="user_password")
    private String userPassword;
    @Column(name="user_faculty")
    private String userFaculty;
    @Column(name="user_grade")
    private String userGrade;
    @Column(name="user_major")
    private String userMajor;
    @Column(name="user_class")
    private String userClass;
    @Column(name="user_status")
    private Integer userStatus;
    @Column(name="user_application")
    private BigDecimal userApplication;
    @Column(name="user_result")
    private BigDecimal userResult;
    @Column(name="user_recognize")
    private String userRecognize;
    @Column(name="user_remark")
    private String userRemark;

    public User() {
    }

    public User(String userName,String userSex, String userCode, String userPassword, String userFaculty, String userGrade, String userMajor, String userClass, int userStatus, BigDecimal userApplication, BigDecimal userResult, String userRecognize, String userRemark) {
        this.userName = userName;
        this.userCode = userCode;
        this.userPassword = userPassword;
        this.userFaculty = userFaculty;
        this.userGrade = userGrade;
        this.userMajor = userMajor;
        this.userSex=userSex;
        this.userClass = userClass;
        this.userStatus = userStatus;
        this.userApplication = userApplication;
        this.userResult = userResult;
        this.userRecognize = userRecognize;
        this.userRemark = userRemark;
    }

    public User(int userId, String userName,String userSex, String userCode, String userPassword, String userFaculty, String userGrade, String userMajor, String userClass, int userStatus, BigDecimal userApplication, BigDecimal userResult, String userRecognize, String userRemark) {
        this.userId = userId;
        this.userName = userName;
        this.userSex=userSex;
        this.userCode = userCode;
        this.userPassword = userPassword;
        this.userFaculty = userFaculty;
        this.userGrade = userGrade;
        this.userMajor = userMajor;
        this.userClass = userClass;
        this.userStatus = userStatus;
        this.userApplication = userApplication;
        this.userResult = userResult;
        this.userRecognize = userRecognize;
        this.userRemark = userRemark;
    }

    public User(String userName, String userSex,String userCode, String userPassword, String userFaculty, String userGrade, String userMajor, String userClass, int userStatus) {
        this.userName = userName;
        this.userCode = userCode;
        this.userSex=userSex;
        this.userPassword = userPassword;
        this.userFaculty = userFaculty;
        this.userGrade = userGrade;
        this.userMajor = userMajor;
        this.userClass = userClass;
        this.userStatus = userStatus;
    }
}
