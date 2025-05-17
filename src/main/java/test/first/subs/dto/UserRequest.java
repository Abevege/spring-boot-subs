package test.first.subs.dto;

public record UserRequest(
        String email,
        String password,
        String name,
        String surname
) {
}
