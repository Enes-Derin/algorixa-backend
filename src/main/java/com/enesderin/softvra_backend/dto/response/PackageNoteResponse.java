// PackageNoteResponse.java
package com.enesderin.softvra_backend.dto.response;

import com.enesderin.softvra_backend.model.NoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageNoteResponse {
    private Long id;
    private NoteType noteType;
    private String noteText;
}