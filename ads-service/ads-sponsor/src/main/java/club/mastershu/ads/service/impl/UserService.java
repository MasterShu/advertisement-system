package club.mastershu.ads.service.impl;

import club.mastershu.ads.constant.Constants;
import club.mastershu.ads.dao.UserRepository;
import club.mastershu.ads.entity.User;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.service.IUserService;
import club.mastershu.ads.utils.CommonUtils;
import club.mastershu.ads.vo.CreateUserRequest;
import club.mastershu.ads.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdsException {
        if (!request.validate()) {
            throw new AdsException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        User oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdsException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        User newUser = userRepository.save(new User(
                request.getUsername(), CommonUtils.md5(request.getUsername())
        ));
        return new CreateUserResponse(
                newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreatedTime(), newUser.getUpdatedTime()
        );
    }
}
