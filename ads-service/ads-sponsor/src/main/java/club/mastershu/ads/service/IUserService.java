package club.mastershu.ads.service;

import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.vo.CreateUserRequest;
import club.mastershu.ads.vo.CreateUserResponse;

public interface IUserService {
    CreateUserResponse createUser(CreateUserRequest request) throws AdsException;
}
