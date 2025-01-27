package com.sesap.UGTSIC.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidates")
public class CandidateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_candidate;

    private String name;
    private String email;
    private String phone;
    private String desiredPosition;

    @Enumerated(EnumType.STRING)
    private Education education;

    private String notes;
    private String filePath;
    private String ipAddress;
    private LocalDateTime submissionDate;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDesiredPosition(String desiredPosition) {
        this.desiredPosition = desiredPosition;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }
}
