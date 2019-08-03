package club.mastershu.ads.controller;

import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.service.IUserService;
import club.mastershu.ads.vo.CreateUserRequest;
import club.mastershu.ads.vo.CreateUserResponse;
import club.mastershu.ads.vo.CreativeResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserOPController {
    private final IUserService userService;

    public UserOPController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdsException {
        log.info("ad-sponsor: creatUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
