package com.sedm.app.service;

import com.sedm.app.bean.ReadingEP;
import com.sedm.app.entities.PowerConsumptionEP;
import com.sedm.app.entities.ReadingEPEntity;
import com.sedm.app.repository.ReadingEPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadingEPServiceImpl implements ReadingEPService{


    @Autowired
    private ReadingEPRepository readingEPRepository;

    @Override
    public ReadingEP storeReading(ReadingEP reading) {

        ReadingEPEntity readingPojo = readingEPRepository.findByUserId(reading.getUserId());
        if(readingPojo!=null){
            List<PowerConsumptionEP> consumptions = readingPojo.getPowerConsumptions();

            PowerConsumptionEP powerConsumption = new PowerConsumptionEP();
            powerConsumption.setPowerConsumption(reading.getPowerConsumption());
            powerConsumption.setReadingDate(reading.getReadingDate());
            powerConsumption.setManual(reading.isManual());
            powerConsumption.setFromDate(reading.getFromDate());
            powerConsumption.setToDate(reading.getToDate());

            consumptions.add(powerConsumption);
            readingPojo.setPowerConsumptions(consumptions);
        }else {
            readingPojo = new ReadingEPEntity();
            readingPojo.setUserId(reading.getUserId());
            readingPojo.setMeterId(reading.getMeterId());
            readingPojo.setProviderId(reading.getProviderId());
            readingPojo.setProviderName(reading.getProviderName());
            readingPojo.setUserName(reading.getUserName());

            PowerConsumptionEP powerConsumption = new PowerConsumptionEP();
            powerConsumption.setPowerConsumption(reading.getPowerConsumption());
            powerConsumption.setReadingDate(reading.getReadingDate());
            powerConsumption.setManual(reading.isManual());
            powerConsumption.setFromDate(reading.getFromDate());
            powerConsumption.setToDate(reading.getToDate());

            List<PowerConsumptionEP> consumptions = new ArrayList<>();
            consumptions.add(powerConsumption);

            readingPojo.setPowerConsumptions(consumptions);


        }
        readingPojo = readingEPRepository.save(readingPojo);

        reading.setId(readingPojo.getId());
        return reading;
    }

    @Override
    public ReadingEPEntity getReadingUserId(String userId) {
        return readingEPRepository.findByUserId(userId);
    }

    @Override
    public List<ReadingEPEntity> getAllReading() {

        List<ReadingEPEntity> list = readingEPRepository.findAll();


        return list;
    }
}
