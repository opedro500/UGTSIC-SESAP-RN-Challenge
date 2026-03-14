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
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> response = new HashMap<>();

        try {
            String ipAddress = forwardedFor != null ? forwardedFor : request.getRemoteAddr();
            service.saveCandidate(candidateDTO, file, ipAddress);

            response.put("mensagem", "Candidatura realizada!");
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("mensagem", "Erro ao processar o arquivo: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        } catch (Exception e) {
            response.put("mensagem", "Ocorreu um erro interno no sistema. Tente novamente mais tarde.");
            return ResponseEntity.status(500).body(response);
        }
    }
}
