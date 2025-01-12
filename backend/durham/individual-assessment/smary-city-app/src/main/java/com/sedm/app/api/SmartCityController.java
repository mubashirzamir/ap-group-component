package com.sedm.app.api;

import com.sedm.app.bean.ReadingSC;
import com.sedm.app.dto.MonthlyConsumptionDTO;
import com.sedm.app.dto.ProviderAggregationDTO;
import com.sedm.app.entities.AggregationEntity;
import com.sedm.app.entities.PowerConsumptionSC;
import com.sedm.app.entities.ReadingSCEntity;
import com.sedm.app.service.AggregationService;
import com.sedm.app.service.ReadingAggregationService;
import com.sedm.app.service.ReadingSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


@RestController
@CrossOrigin("*")
@RequestMapping("/sc/api")
public class SmartCityController {

    @Autowired
    private ReadingSCService readingSCService;

    @Autowired
    private AggregationService aggregationService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ReadingAggregationService readingAggregationService;

    @Value("${api.gateway.url:''}")
    private String apiGatewayUrl;
    @Value("${ep.path.base:''}")
    private String baseUrl;

    /**
     * 1) Aggregations by provider
     * GET /sc/api/aggregations/provider
     */
    @GetMapping("/aggregations/provider")
    public ResponseEntity<List<ProviderAggregationDTO>> getAggregationsByProvider() {
        List<ProviderAggregationDTO> result = readingAggregationService.getAggregationsByProvider();
        return ResponseEntity.ok(result);
    }

    /**
     * 2) Aggregation for the entire dataset
     * GET /sc/api/aggregations/all
     */
    @GetMapping("/aggregations/all")
    public ResponseEntity<ProviderAggregationDTO> getAggregationsAll() {
        ProviderAggregationDTO result = readingAggregationService.getTotalAggregation();
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint #1: Monthly consumption for a single provider
     * GET /sc/api/aggregations/monthly-by-provider?providerName=XYZ
     */
    @GetMapping("/aggregations/monthly-by-provider")
    public ResponseEntity<List<MonthlyConsumptionDTO>> getMonthlyStatsByProvider(
            @RequestParam("providerName") String providerName) {

        List<MonthlyConsumptionDTO> stats =
                readingAggregationService.getMonthlyConsumptionByProvider(providerName);
        return ResponseEntity.ok(stats);
    }

    /**
     * Endpoint #2: Monthly consumption for the entire city (all providers)
     * GET /sc/api/aggregations/monthly-city
     */
    @GetMapping("/aggregations/monthly-city")
    public ResponseEntity<List<MonthlyConsumptionDTO>> getMonthlyStatsCityLevel() {
        List<MonthlyConsumptionDTO> stats =
                readingAggregationService.getMonthlyConsumptionCityWide();
        return ResponseEntity.ok(stats);
    }

    /**
     * GET /sc/api/aggregations/monthly-all-providers
     *
     * Returns JSON in the shape:
     * {
     *   "ProviderA": { "2024-01": 12000, "2024-02": 9000 },
     *   "ProviderB": { "2024-01": 10500, "2024-02": 13000 }
     * }
     */
    @GetMapping("/aggregations/monthly-all-providers")
    public ResponseEntity<Map<String, Map<String, Long>>> getMonthlyConsumptionAllProviders() {
        Map<String, Map<String, Long>> stats =
                readingAggregationService.getMonthlyConsumptionForAllProviders();
        return ResponseEntity.ok(stats);
    }

    @RequestMapping("/test")
    public ResponseEntity<String> testPage(){

        return ResponseEntity.ok("Welcome to smart city");
    }

    @GetMapping("/readings")
    public ResponseEntity<List<ReadingSCEntity>> getReading(){
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<ReadingSCEntity[]> readingSCSList = restTemplate.exchange(apiGatewayUrl+baseUrl, HttpMethod.GET, new HttpEntity<>(headers), ReadingSCEntity[].class);
        if(readingSCSList!=null) {
            for (ReadingSCEntity readingSC : readingSCSList.getBody()) {
                readingSC = readingSCService.storeReadingFromEP(readingSC);
            }
        }
        headers.set("STATUS","200");
        return ResponseEntity.ok().headers(headers).body(new ArrayList<>());
    }

    @GetMapping("/aggregations")
    public ResponseEntity<List<AggregationEntity>> createAggregation(){
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<ReadingSCEntity[]> readingSCSList = restTemplate.exchange(apiGatewayUrl+baseUrl, HttpMethod.GET, new HttpEntity<>(headers), ReadingSCEntity[].class);
        Map<String, AggregationEntity> map1 = new HashMap<>();
        for(Object readingSCObj:readingSCSList.getBody()){
            if(readingSCObj instanceof  ReadingSCEntity) {
                ReadingSCEntity readingSC = (ReadingSCEntity)readingSCObj;
                AggregationEntity ep = map1.get(readingSC.getProviderName());
                if (ep != null) {
                    ep.setTotalCount(ep.getTotalCount() + 1);
                    ep.setTotalReadings(ep.getTotalReadings() + readingSC.getPowerConsumptions().size());
                    ep.setTotalActiveUser(ep.getTotalActiveUser() + 1);
                    ep.setTotalConsumption(ep.getTotalConsumption() + getTotalConsumptions(readingSC.getPowerConsumptions()));
                    map1.put(readingSC.getProviderName(), ep);
                } else {
                    ep = new AggregationEntity();
                    ep.setProviderId(readingSC.getProviderId());
                    ep.setProviderName(readingSC.getProviderName());
                    ep.setTotalCount(1);
                    ep.setTotalReadings(readingSC.getPowerConsumptions().size());
                    ep.setTotalConsumption(getTotalConsumptions(readingSC.getPowerConsumptions()));
                    ep.setTotalActiveUser(1);

                    map1.put(readingSC.getProviderName(), ep);
                }
            }


        }
        List<AggregationEntity> li = new ArrayList<>();
        for(Map.Entry<String,AggregationEntity> entry: map1.entrySet()){
            AggregationEntity ae = aggregationService.saveAggrigation(entry.getValue());
            li.add(ae);
        }
        return ResponseEntity.ok().body(li);
    }

    @GetMapping("/aggregations/get")
    public ResponseEntity< Map<String,Long>> createAggregationByUserId(@RequestParam String userId){
        HttpHeaders headers = new HttpHeaders();
        System.out.println(userId);
        ResponseEntity<ReadingSCEntity> readingSCSList = restTemplate.exchange(apiGatewayUrl+"/ep/api/get?userId="+userId, HttpMethod.GET, new HttpEntity<>(headers), ReadingSCEntity.class);
        Map<String, AggregationEntity> map1 = new HashMap<>();
        Map<String,Long> month = new HashMap<>();
        ReadingSCEntity readingSC=readingSCSList.getBody();
            /*AggregationEntity ep = map1.get(readingSC.getProviderName());
                if (ep != null) {
                    ep.setTotalCount(ep.getTotalCount() + 1);
                    ep.setTotalReadings(ep.getTotalReadings() + readingSC.getPowerConsumptions().size());
                    ep.setTotalActiveUser(ep.getTotalActiveUser() + 1);
                    ep.setTotalConsumption(ep.getTotalConsumption() + getTotalConsumptions(readingSC.getPowerConsumptions()));
                    map1.put(readingSC.getProviderName(), ep);
                } else {
                    ep = new AggregationEntity();
                    ep.setProviderId(readingSC.getProviderId());
                    ep.setProviderName(readingSC.getProviderName());
                    ep.setTotalCount(1);
                    ep.setTotalReadings(readingSC.getPowerConsumptions().size());
                    ep.setTotalConsumption(getTotalConsumptions(readingSC.getPowerConsumptions()));
                    ep.setUserId(readingSC.getUserId());
                    ep.setTotalActiveUser(1);

                    map1.put(readingSC.getProviderName(), ep);
                }*/
        if(readingSC==null){
            return ResponseEntity.ok().body(month);
        }
        System.out.println(readingSC.getProviderName());
        for(int i=1;i<13;i++) {
            if(i<10) {
                month.put("0"+i , 0L);
            }else{
                month.put(i+"" , 0L);
            }
        }
        System.out.println(month);
                for(PowerConsumptionSC powerConsumptionSC: readingSC.getPowerConsumptions()){
                    System.out.println(powerConsumptionSC.getFromDate());
                    for(int i=1;i<13;i++) {
                        String v= "";
                        if(i<10) {
                            v="0"+i;
                        }else{
                            v=i+"";
                        }
                        if (powerConsumptionSC.getFromDate().contains("-" + v + "-")) {
                            Long val = month.get(v+ "");
                            if (val != null ) {
                                month.put(v + "", val + powerConsumptionSC.getPowerConsumption());
                            } else {
                                month.put(v + "", powerConsumptionSC.getPowerConsumption());
                            }

                        }
                    }
                }




        List<AggregationEntity> li = new ArrayList<>();
        for(Map.Entry<String,AggregationEntity> entry: map1.entrySet()){
            AggregationEntity ae = aggregationService.saveAggrigation(entry.getValue());
            li.add(ae);
        }
        return ResponseEntity.ok().body(month);
    }

    public Long getTotalConsumptions(List<PowerConsumptionSC> powerConsumptionSCS){
        Long value=0L;
        for(PowerConsumptionSC powerConsumptionSC:powerConsumptionSCS){

                value = value+powerConsumptionSC.getPowerConsumption();

        }
        return value;
    }
}
