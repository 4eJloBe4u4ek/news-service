package backend.newsservice.dto;

public record UserInfoResponse(
        Long id,
        String username,
        String email,
        Role role,
        String phone
) {}
