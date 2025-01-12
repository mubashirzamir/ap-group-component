package com.sedm.app.service;

import com.sedm.app.bean.ReadingSC;
import com.sedm.app.entities.ReadingSCEntity;
import com.sedm.app.repository.ReadingSCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


@Service
public class ReadingSCServiceImpl implements ReadingSCService{

    @Autowired
    private ReadingSCRepository readingSCRepository;

    @Override
    public ReadingSCEntity storeReadingFromEP(ReadingSCEntity readingSC) {
        ReadingSCEntity readingPojo = new ReadingSCEntity();
        readingPojo.setUserId(readingSC.getUserId());
        readingPojo.setMeterId(readingSC.getMeterId());
         readingPojo.setProviderId(readingSC.getProviderId());
        readingPojo.setProviderName(readingSC.getProviderName());
        readingPojo.setPowerConsumptions(readingSC.getPowerConsumptions());
        readingPojo.setUserName(readingSC.getUserName());
        System.out.println(readingPojo.getUserId()+"---"+readingPojo.getUserName());
        readingPojo = readingSCRepository.insert(readingPojo);
        System.out.println(readingPojo.getId());

        readingSC.setId(readingPojo.getId());
        return readingSC;
    }
}
