package com.sedm.app.service;

import com.sedm.app.bean.Reading;
import com.sedm.app.entities.PowerConsumption;
import com.sedm.app.entities.ReadingEntity;
import com.sedm.app.repository.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReadingServiceImpl implements ReadingService{

    @Autowired
    private ReadingRepository readingRepository;
    @Override
    public Reading createReading(Reading reading) {

        ReadingEntity readingPojo = new ReadingEntity();
        readingPojo.setUserId(reading.getUserId());
        readingPojo.setMeterId(reading.getMeterId());
        readingPojo.setProviderId(reading.getProviderId());
        readingPojo.setProviderName(reading.getProviderName());
        readingPojo.setUserName(reading.getUserName());


        PowerConsumption powerConsumption = new PowerConsumption();
        powerConsumption.setPowerConsumption(reading.getPowerConsumption());
        powerConsumption.setReadingDate(reading.getReadingDate());
        powerConsumption.setManual(reading.isManual());
        powerConsumption.setFromDate(reading.getFromDate());
        powerConsumption.setToDate(reading.getToDate());
        List<PowerConsumption> consumptions = new ArrayList<>();
        consumptions.add(powerConsumption);
        readingPojo.setPowerConsumptions(consumptions);

        readingPojo = readingRepository.insert(readingPojo);
        System.out.println(readingPojo.getId());

        reading.setId(readingPojo.getId());

        return reading;
    }

    @Override
    public Reading updateReading(Reading reading) {


        Optional<ReadingEntity> entity1 = readingRepository.findByUserId(reading.getUserId());
        if(entity1!=null && entity1.isPresent() ){
            ReadingEntity entity = entity1.get();
            List<PowerConsumption> consumptions = entity.getPowerConsumptions();

            PowerConsumption powerConsumption = new PowerConsumption();
            powerConsumption.setPowerConsumption(reading.getPowerConsumption());
            powerConsumption.setReadingDate(reading.getReadingDate());
            powerConsumption.setManual(reading.isManual());
            powerConsumption.setFromDate(reading.getFromDate());
            powerConsumption.setToDate(reading.getToDate());

            consumptions.add(powerConsumption);
            entity.setPowerConsumptions(consumptions);
            readingRepository.save(entity);
            reading.setId(entity.getId());
        }

        return reading;
    }

    @Override
    public ReadingEntity getReadingByUserId(String userId) {
        Optional<ReadingEntity> entity = readingRepository.findByUserId(userId);
        if(entity.isPresent()) {
            return entity.get();
        }
        return new ReadingEntity();
    }
}
