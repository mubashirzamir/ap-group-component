package com.sedm.app.service;

import com.sedm.app.bean.ReadingSC;
import com.sedm.app.entities.ReadingSCEntity;

public interface ReadingSCService {

    ReadingSCEntity storeReadingFromEP(ReadingSCEntity readingSC);
}
