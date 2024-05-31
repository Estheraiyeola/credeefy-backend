package com.semicolon.credeefy.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialId;
    @OneToOne
    private User user;
    @ManyToOne
    private Institution institution;
    private String credentialType;
    private String issueDate;
}
