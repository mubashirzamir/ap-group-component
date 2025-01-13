package com.sedm.app.service;

import com.sedm.app.bean.Reading;
import com.sedm.app.entities.ReadingEntity;

import java.util.List;

public interface ReadingService {

    Reading createReading(Reading reading);
    Reading updateReading(Reading reading);

    ReadingEntity getReadingByUserId(String userId);

}
