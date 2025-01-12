package com.sedm.app.api;

import com.netflix.discovery.converters.Auto;
import com.sedm.app.bean.ReadingEP;
import com.sedm.app.bean.ResponseEP;
import com.sedm.app.entities.ReadingEPEntity;
import com.sedm.app.service.ReadingEPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ep/api")
public class ElectricityProviderController {

    @Autowired
    private ReadingEPService readingEPService;

    @RequestMapping("/test")
    public ResponseEntity<String> testPage(){

        return ResponseEntity.ok("Welcome to Electricy provider");
    }
    @RequestMapping("/save")
    public ResponseEntity<ResponseEP> saveReadings(@RequestBody ReadingEP readingEP){

        ReadingEP readingEP1 = readingEPService.storeReading(readingEP);
        if(readingEP1.getId()!=null){
            ResponseEP responseEP = new ResponseEP();
            responseEP.setId(readingEP1.getId());
            responseEP.setMessage("Success");
            responseEP.setStatus("201");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("STATUS","201");
            return ResponseEntity.ok().headers(responseHeaders).body(responseEP);

        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("STATUS","500");
        ResponseEP responseEP = new ResponseEP();
        responseEP.setId(readingEP1.getId());
        responseEP.setMessage("Failure");
        responseEP.setStatus("500");
        return ResponseEntity.ok().headers(responseHeaders).body(responseEP);
    }


    @RequestMapping("all")
    public ResponseEntity<List<ReadingEPEntity>> getAllReading(){

        List<ReadingEPEntity> readingEPList = readingEPService.getAllReading();

        return ResponseEntity.ok().body(readingEPList);
    }

    @RequestMapping("get")
    public ResponseEntity<ReadingEPEntity> getAllReading(@RequestParam String userId){

        ReadingEPEntity readingEPList = readingEPService.getReadingUserId(userId);

        return ResponseEntity.ok().body(readingEPList);
    }

}
