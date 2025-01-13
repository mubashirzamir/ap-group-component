package com.sedm.app;

import com.sedm.app.api.CitizenController;
import com.sedm.app.bean.Reading;
import com.sedm.app.entities.ReadingEntity;
import com.sedm.app.service.ReadingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CitizenControllerTest {

    @InjectMocks
    CitizenController citizenController;

    @Mock
    ReadingService readingService;

    @Mock
    RestTemplate restTemplate;

    @Test
    public void testReading(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(readingService.createReading(any(Reading.class))).thenReturn(new Reading());

        Reading reading = new Reading();
        ResponseEntity<Reading> responseEntity = citizenController.consumeReading(reading);

        assertThat(responseEntity.getHeaders().get("STATUS").get(0)).isEqualTo("201");
        //assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }
}
