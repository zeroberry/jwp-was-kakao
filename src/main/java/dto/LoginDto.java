package dto;

public class LoginDto {
    private final String userId;
    private final String password;

    public LoginDto(final String userId, final String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
