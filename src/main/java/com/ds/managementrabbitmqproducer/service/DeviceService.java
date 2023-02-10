package com.ds.managementrabbitmqproducer.service;

import com.ds.managementrabbitmqproducer.model.Device;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DeviceService {

    public List<Device> getPersonDevices(UUID personId) {
        String endpointURL = "http://localhost:8080/api/devices/person/" + personId;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object[]> res = restTemplate.getForEntity(endpointURL, Object[].class);
        Object[] objects = res.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        if(objects == null) {
            return null;
        }
        return Arrays.stream(objects)
                .map(object -> objectMapper.convertValue(object, Device.class))
                .toList();

//        ResponseEntity<DeviceList> res = restTemplate.getForEntity(endpointURL, DeviceList.class);
//        List<Device> devices = res.getBody().getDevices();
//        return devices;
    }
}
