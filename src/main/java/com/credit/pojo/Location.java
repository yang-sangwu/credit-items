package com.credit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="location")
@EntityListeners(AuditingEntityListener.class)
public class Location {
    @Id
    @Column(name="location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationId;
    @Column(name="location_name")
    private String locationName;
    @Column(name="location_fatherid")
    private Integer locationFatherid;
    @Column(name="location_grade")
    private Integer locationGrade;


}
