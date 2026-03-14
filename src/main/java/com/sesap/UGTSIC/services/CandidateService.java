package com.sesap.UGTSIC.services;

import com.sesap.UGTSIC.DTO.CandidateDTO;
import com.sesap.UGTSIC.models.CandidateModel;
import com.sesap.UGTSIC.models.Education;
import com.sesap.UGTSIC.repositories.CandidateRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CandidateService {
    @Value("${upload.path}")
    private String uploadDir;

    @Autowired
    private CandidateRepository repository;

    @Autowired
    private EmailService emailService;

    public CandidateModel saveCandidate(CandidateDTO candidateDTO, MultipartFile file, String ipAddress) throws IOException, MessagingException {
        ensureUploadDirectoryExists();

        Path filePath = saveUploadedFile(file);

        CandidateModel candidateModel = mapToCandidateModel(candidateDTO, filePath.toString(), ipAddress);
        CandidateModel savedCandidate = repository.save(candidateModel);

        sendConfirmationEmail(candidateModel, filePath, ipAddress);

        return savedCandidate;
    }

    private void ensureUploadDirectoryExists() {
        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private Path saveUploadedFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        int counter = 1;
        while (Files.exists(filePath)) {
            String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
            String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
            filePath = Paths.get(uploadDir, baseName + counter + extension);
            counter++;
        }

        file.transferTo(filePath);
        return filePath;
    }

    private CandidateModel mapToCandidateModel(CandidateDTO candidateDTO, String filePath, String ipAddress) {
        CandidateModel candidateModel = new CandidateModel();

        String formattedName = candidateDTO.name() != null ? candidateDTO.name().trim().toUpperCase() : null;
        String formattedDesiredPosition = candidateDTO.desiredPosition() != null ? candidateDTO.desiredPosition().trim().toUpperCase() : null;
        String formattedNotes = candidateDTO.notes() != null ? candidateDTO.notes().trim() : null;

        candidateModel.setName(formattedName);
        candidateModel.setDesiredPosition(formattedDesiredPosition);
        candidateModel.setNotes(formattedNotes);

        candidateModel.setEmail(candidateDTO.email());
        candidateModel.setPhone(candidateDTO.phone());
        candidateModel.setEducation(candidateDTO.education());
        candidateModel.setFilePath(filePath);
        candidateModel.setIpAddress(ipAddress);
        candidateModel.setSubmissionDate(LocalDateTime.now());

        return candidateModel;
    }

    private void sendConfirmationEmail(CandidateModel candidateModel, Path filePath, String ipAddress) throws MessagingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        String formattedDateTime = candidateModel.getSubmissionDate().format(formatter);

        String emailBody = String.format(
                "Candidatura recebida com sucesso!\n\nNome: %s\nE-mail: %s\nTelefone: %s\nCargo Desejado: %s\nEscolaridade: %s\nIP: %s\nData/Hora: %s",
                candidateModel.getName(),
                candidateModel.getEmail(),
                candidateModel.getPhone(),
                candidateModel.getDesiredPosition(),
                translateEducationToPortuguese(candidateModel.getEducation()),
                ipAddress,
                formattedDateTime
        );

        emailService.sendEmailWithAttachment(
                candidateModel.getEmail(),
                "Candidatura recebida com sucesso!",
                emailBody,
                filePath.toFile()
        );
    }

    private String translateEducationToPortuguese(Education education) {
        return switch (education) {
            case INCOMPLETE_HIGH_SCHOOL -> "Ensino Médio Incompleto";
            case COMPLETE_HIGH_SCHOOL -> "Ensino Médio Completo";
            case INCOMPLETE_COLLEGE -> "Ensino Superior Incompleto";
            case COMPLETE_COLLEGE -> "Ensino Superior Completo";
            case TECHNICAL -> "Técnico";
            case MASTERS -> "Mestrado";
            case DOCTORATE -> "Doutorado";
            default -> "Não especificado";
        };
    }
}
