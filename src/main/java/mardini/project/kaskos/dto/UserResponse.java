package mardini.project.kaskos.dto;

import java.util.UUID;


public record UserResponse(
        UUID id,
        String name,
        String email
){}
