package com.sedm.app.service;

import com.sedm.app.bean.ReadingEP;
import com.sedm.app.entities.ReadingEPEntity;

import java.util.List;

public interface ReadingEPService {
    ReadingEP storeReading(ReadingEP readingEP);
    ReadingEPEntity getReadingUserId(String userId) ;
    List<ReadingEPEntity> getAllReading() ;
}
