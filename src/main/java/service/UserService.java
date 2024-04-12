package service;

import db.DataBase;
import dto.CreateUserDto;
import dto.LoginDto;
import model.User;

import java.util.Collection;
import java.util.UUID;

public class UserService {
    public void createUser(final CreateUserDto createUserDto) {
        final User user = new User(createUserDto.getUserId(),
                createUserDto.getPassword(),
                createUserDto.getName(),
                createUserDto.getEmail());

        if (DataBase.findUserById(user.getUserId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        DataBase.addUser(user);
    }

    public User getUser(final String testId) {
        return DataBase.findUserById(testId);
    }

    public String login(final LoginDto loginDto) {
        final User user = DataBase.findUserById(loginDto.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return UUID.randomUUID().toString();
    }

    public Collection<User> findAll() {
        return DataBase.findAll();
    }

    public boolean isLogin(String jsessionid) {
        return jsessionid != null;
    }
}
