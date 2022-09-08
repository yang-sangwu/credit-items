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
@Table(name="paths")
@EntityListeners(AuditingEntityListener.class)
public class Paths {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="app_id")
    private Integer appId;
    @Column(name="visit_path")
    private String visitPath;
    @Column(name = "path")
    private String path;

    public Paths(Integer appId, String visitPath) {
        this.appId = appId;
        this.visitPath = visitPath;
    }
}
