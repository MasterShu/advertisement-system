package club.mastershu.ads.service.impl;

import club.mastershu.ads.dao.CreativeRepository;
import club.mastershu.ads.entity.Creative;
import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.service.ICreativeService;
import club.mastershu.ads.vo.CreativeRequest;
import club.mastershu.ads.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreativeService implements ICreativeService {
    private final CreativeRepository creativeRepository;

    public CreativeService(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) throws AdsException {
        Creative creative = creativeRepository.save(request.convertToEntity());
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
