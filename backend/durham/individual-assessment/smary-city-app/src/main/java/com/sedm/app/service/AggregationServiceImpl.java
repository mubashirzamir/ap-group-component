package com.sedm.app.service;

import com.sedm.app.entities.AggregationEntity;
import com.sedm.app.repository.AggregationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AggregationServiceImpl implements AggregationService{
    @Autowired
    private AggregationRepository aggregationRepository;
    @Override
    public AggregationEntity saveAggrigation(AggregationEntity aggregationEntity) {
        AggregationEntity ae = aggregationRepository.findByProviderName(aggregationEntity.getProviderName());
        if(ae!=null){
            ae.setTotalReadings(aggregationEntity.getTotalReadings());
            ae.setTotalActiveUser(aggregationEntity.getTotalActiveUser());
            ae.setTotalCount(aggregationEntity.getTotalCount());
            ae.setTotalConsumption(aggregationEntity.getTotalConsumption());
            return aggregationRepository.save(ae);
        }
        return aggregationRepository.save(aggregationEntity);
    }
}
