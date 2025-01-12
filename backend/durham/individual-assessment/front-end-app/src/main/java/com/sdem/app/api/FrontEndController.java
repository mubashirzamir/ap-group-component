package com.sdem.app.api;

import com.sdem.app.bean.ErrorMsg;
import com.sdem.app.bean.ReadingInfo;
import com.sdem.app.bean.ReadingResponse;
import com.sdem.app.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class FrontEndController {

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/")
    public String getHome(){
        return "register";
    }

    @RequestMapping("/home")
    public String getHomePage(@RequestParam String userId, Model model){
        ResponseEntity<User> userFormEntity = restTemplate.getForEntity("http://localhost:9191/citizen/api/user/get?userId="+userId,User.class);
        ResponseEntity<ReadingResponse> readingInfoEnt = restTemplate.getForEntity("http://localhost:9191/citizen/api/reading?userId="+userId,ReadingResponse.class);
        ResponseEntity<Map> readingInfoEnt1 = restTemplate.getForEntity("http://localhost:9191/sc/api/aggregations/get?userId="+userId, Map.class);
        System.out.println(readingInfoEnt1.getBody());
        String val = "";
            if(readingInfoEnt1.getBody() instanceof Map){
                Map<String,Long> val1 = (Map<String, Long>) readingInfoEnt1.getBody();
                for(int i=1;i<13;i++){
                    if(i<10){
                        val = val+val1.get("0"+i)+",";
                    }else{
                        val = val+val1.get(i+"")+",";
                    }

                }
            }
            /*for (Object mapObj : readingInfoEnt1.getBody().entrySet()) {
                if (mapObj instanceof Map.Entry) {
                    Map.Entry<String, Long> mp = (Map.Entry<String, Long>) mapObj;
                    val = val+mp.getValue()+",";
                }

            }*/

        val = val.substring(0,val.lastIndexOf(","));
        model.addAttribute("dataVal",val);
        User user = userFormEntity.getBody();
        if(user!=null){
            model.addAttribute("user",user);
            model.addAttribute("readingInfo",readingInfoEnt.getBody());
        }
        return "home";
    }

    @RequestMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @RequestMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String checkLoginPage(@ModelAttribute("userForm") User userForm, Model model){
        ResponseEntity<User> userFormEntity = restTemplate.postForEntity("http://localhost:9191/citizen/api/user/validate",userForm,User.class);
        if(userFormEntity ==null || userFormEntity.getBody()== null ||userFormEntity.getBody() .getUserId() ==null ){
            //throw new RuntimeException("Invalid username and password. try again later!");
            ErrorMsg errorMsg = new ErrorMsg("Invalid username and password. try again later!","01");
            model.addAttribute("error",errorMsg);
            return "login";
        }
        System.out.println(userFormEntity.getBody().getUserId());

        return "redirect:/home?userId="+userFormEntity.getBody().getUserId();
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("userForm") User userForm, Model model){

        System.out.println(userForm.getName());
        System.out.println(userForm.getEmailId());
        if(!userForm.getCpasswd().equals(userForm.getPasswd())){
            ErrorMsg errorMsg = new ErrorMsg("Password and confirm password is not matched","02");
            model.addAttribute("error",errorMsg);
           // model.addAttribute("error", "Password and confirm password is not matched");
            //throw new RuntimeException("Password and confirm password is not matched");
            return "register";
        }

        ResponseEntity<User> userFormEntity = restTemplate.postForEntity("http://localhost:9191/citizen/api/user/create",userForm,User.class);
        System.out.println(userFormEntity.getBody().getUserId());

        return  "redirect:/login";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("userForm") User userForm, Model model){

        ResponseEntity<User> userFormEntity = restTemplate.postForEntity("http://localhost:9191/citizen/api/user/update",userForm,User.class);
        System.out.println(userFormEntity.getBody().getUserId());
        model.addAttribute("user",userFormEntity.getBody());
        return  "redirect:/home?userId="+userFormEntity.getBody().getUserId();
    }

    @PostMapping("/saveReading")
    public String submitReading(@ModelAttribute("readingInfo") ReadingInfo readingInfo, Model model){
        System.out.println(readingInfo.getUserId()+"--"+readingInfo.getUserName()+"---"+readingInfo.getPowerConsumption());
        System.out.println(readingInfo.getFromDate()+"---"+readingInfo.getToDate());
        readingInfo.setProviderName(readingInfo.getProviderId()==1?"A":readingInfo.getProviderId()==2?"B":"C");
        if(readingInfo.getId()!=null && readingInfo.getId().length()>0){
            ResponseEntity<ReadingInfo> readingInfo1ent = restTemplate.postForEntity("http://localhost:9191/citizen/api/update", readingInfo, ReadingInfo.class);
        }else {
            ResponseEntity<ReadingInfo> readingInfo1ent = restTemplate.postForEntity("http://localhost:9191/citizen/api/reading", readingInfo, ReadingInfo.class);
        }


        return "redirect:/home?userId="+readingInfo.getUserId();
    }

    @GetMapping("/aggregation")
    public String getAggregationData(@RequestParam String userId, Model model){
        //User us = model.getAttribute("user");
        ResponseEntity<Map> readingInfoEnt = restTemplate.getForEntity("http://localhost:9191/sc/api/aggregations/userId?userId="+userId, Map.class);
        for(Object mapObj:readingInfoEnt.getBody().entrySet()){
            if(mapObj instanceof Map.Entry){
                Map.Entry<String,Long> mp = (Map.Entry<String,Long>)mapObj;
                System.out.println(mp.getKey());
            }

        }
        model.addAttribute("mapObj",readingInfoEnt);
        return "redirect:/home?userId="+userId;
    }
    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }


}
