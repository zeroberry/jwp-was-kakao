package dto;

import java.util.Map;

public class CreateUserDto {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public CreateUserDto(final Map<String, String> queryParameters) {
        validate(queryParameters);
        this.userId = queryParameters.get("userId");
        this.password = queryParameters.get("password");
        this.name = queryParameters.get("name");
        this.email = queryParameters.get("email");
    }

    private void validate(final Map<String, String> queryParameters) {
        if (queryParameters.get("userId") == null ||
                queryParameters.get("password") == null ||
                queryParameters.get("name") == null ||
                queryParameters.get("email") == null) {
            throw new IllegalArgumentException("입력값이 올바르지 않습니다.");
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
