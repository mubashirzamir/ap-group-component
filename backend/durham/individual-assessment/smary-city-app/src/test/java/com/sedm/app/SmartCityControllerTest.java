package com.sedm.app;


import com.sedm.app.api.SmartCityController;
import com.sedm.app.bean.ReadingSC;
import com.sedm.app.entities.ReadingSCEntity;
import com.sedm.app.service.ReadingSCService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class SmartCityControllerTest {

    @InjectMocks
    SmartCityController smartCityController;

    @Mock
    RestTemplate restTemplate;

    @Mock
    ReadingSCService readingSCService;

    @Test
    public void testReading(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ReadingSCEntity reading = new ReadingSCEntity();

        //when(restTemplate.exchange(any(String.class),any(String.class),any(HttpEntity.class),any(ReadingSCEntity.class))).thenReturn(reading);
       // when(readingSCService.storeReadingFromEP(any(ReadingSCEntity.class))).thenReturn(reading);


        ResponseEntity<List<ReadingSCEntity>> responseEntity = smartCityController.getReading();

        assertThat(responseEntity.getHeaders().get("STATUS")).isEqualTo(null);
        //Assertions.assertNotNull(responseEntity);
        //assertThat(responseEntity.getBody()).isNull();
        //assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }
}
