package club.mastershu.ads.service;

import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.vo.CreativeRequest;
import club.mastershu.ads.vo.CreativeResponse;

public interface ICreativeService {
    CreativeResponse createCreative(CreativeRequest request) throws AdsException;
}
