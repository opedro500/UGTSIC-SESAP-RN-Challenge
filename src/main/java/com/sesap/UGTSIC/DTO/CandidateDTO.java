package com.sesap.UGTSIC.DTO;

import com.sesap.UGTSIC.models.Education;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record CandidateDTO(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 10, max = 15)
        String phone,

        @NotBlank
        String desiredPosition,

        @NotNull
        Education education,

        String notes,

        MultipartFile file
) {
}
