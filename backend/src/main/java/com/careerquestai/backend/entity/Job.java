
package com.careerquestai.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "employer_id", nullable = false)
private User employer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String location;

    @Column(length = 5000)
    private String description;

    @Column(nullable = false)
    private String employmentType;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false)
    private String experience;

    @Column(length = 500)
    private String skills;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (active == null) {
            active = true;
        }
    }
}

