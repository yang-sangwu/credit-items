package com.credit.pojo;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name="messages")
@EntityListeners(AuditingEntityListener.class)
public class Messages {
    @Id
    @Column(name="message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;
    @Column(name="organize")
    private String organize;
    @Column(name="basic_information")
    private String basicInformation;

    public Messages() {
    }

    public Messages(int messageId, String organize, String basicInformation) {
        this.messageId = messageId;
        this.organize = organize;
        this.basicInformation = basicInformation;
    }
}
