package club.mastershu.ads.controller;

import club.mastershu.ads.dao.CreativeRepository;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.service.ICreativeService;
import club.mastershu.ads.vo.CreativeRequest;
import club.mastershu.ads.vo.CreativeResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreativeOPController {
    private final ICreativeService creativeService;

    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdsException {
        log.info("ads-sponsor: createCreative -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
