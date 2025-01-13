package com.sedm.app;

import com.sedm.app.api.ElectricityProviderController;
import com.sedm.app.bean.ReadingEP;
import com.sedm.app.bean.ResponseEP;
import com.sedm.app.service.ReadingEPService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElectricityProviderTest {

    @InjectMocks
    ElectricityProviderController electricityProviderController;

    @Mock
    ReadingEPService readingEPService;

    @Test
    public void testStoreReading(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ReadingEP reading = new ReadingEP();
        reading.setId("123");
        when(readingEPService.storeReading(any(ReadingEP.class))).thenReturn(reading);


        ResponseEntity<ResponseEP> responseEntity = electricityProviderController.saveReadings(reading);

        assertThat(responseEntity.getHeaders().get("STATUS").get(0)).isEqualTo("201");
        //assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

    @Test
    public void testStoreReadingNEgative(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ReadingEP reading = new ReadingEP();

        when(readingEPService.storeReading(any(ReadingEP.class))).thenReturn(reading);


        ResponseEntity<ResponseEP> responseEntity = electricityProviderController.saveReadings(reading);

        assertThat(responseEntity.getHeaders().get("STATUS").get(0)).isEqualTo("500");
        //assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }
}
