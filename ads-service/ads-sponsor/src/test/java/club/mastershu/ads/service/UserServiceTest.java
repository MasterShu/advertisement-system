package club.mastershu.ads.service;

import club.mastershu.ads.Application;
import club.mastershu.ads.entity.User;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.vo.CreateUserRequest;
import club.mastershu.ads.vo.CreateUserResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void testCreateUser() throws AdsException {
        UserData userData = new UserData();
        CreateUserResponse user = userService.createUser(new CreateUserRequest(userData.name));
        log.info(String.valueOf(user));
    }

    @Data
    private static class UserData{
        private Long id;
        private String name;

        UserData () {
            id = 15L;
            name = "karis-001";
        }
    }
}
