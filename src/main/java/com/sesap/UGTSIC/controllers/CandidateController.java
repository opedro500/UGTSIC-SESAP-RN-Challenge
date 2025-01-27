package com.sesap.UGTSIC.controllers;

import com.sesap.UGTSIC.DTO.CandidateDTO;
import com.sesap.UGTSIC.services.CandidateService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/selecao")
public class CandidateController {
    @Autowired
    private CandidateService service;

    @PostMapping("/candidatar")
    public ResponseEntity<Object> postCandidate(@ModelAttribute @Valid CandidateDTO candidateDTO,
                                                @RequestParam("file") MultipartFile file,
                                                @RequestHeader(value = "X-Forwarded-For", required = false) String forwardedFor,
                                                HttpServletRequest request) {
        try {
            String ipAddress = forwardedFor != null ? forwardedFor : request.getRemoteAddr();

            service.saveCandidate(candidateDTO, file, ipAddress);
            return ResponseEntity.ok("Candidatura realizada com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao processar o arquivo: " + e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
