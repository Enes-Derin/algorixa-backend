// PackageNoteRequest.java
package com.enesderin.softvra_backend.dto.request;

import com.enesderin.softvra_backend.model.NoteType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageNoteRequest {

    private NoteType noteType = NoteType.NEUTRAL;

    @NotBlank(message = "Not metni gereklidir")
    private String noteText;
}