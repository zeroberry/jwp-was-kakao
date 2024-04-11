package service;

import db.DataBase;
import dto.CreateUserDto;
import model.User;

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
}
