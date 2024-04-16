package dto;

import java.util.Map;

public class CreateUserDto {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;


    public CreateUserDto(final String userId, final String password, final String name, final String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static CreateUserDto of(final Map<String, String> values) {
        return new CreateUserDto(values.get("userId"), values.get("password"), values.get("name"), values.get("email"));
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
