package com.ds.managementrabbitmqproducer.controller;

import com.ds.managementrabbitmqproducer.producer.Producer;
import com.ds.managementrabbitmqproducer.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = {"/producer"})
public class ProducerController {
    private final Producer producer;

    @Autowired
    public ProducerController(Producer producer) {
        this.producer = producer;
    }
    @GetMapping("/one-reading")
    public void getReadingForUserConfigFile() {
        try {
            this.producer.composeAndSendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/one-reading/{personId}")
    public void getReadingForUser(@PathVariable("personId") UUID personId) {
        try {
            this.producer.replacePersonIdInConfigFile(personId);
            this.producer.composeAndSendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/reset-last-read-line")
    public void resetLastReadLine() {
        try {
            this.producer.resetLastReadLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/set-last-read-line/{line}")
    public void setLastReadLine(@PathVariable("line") int line) {
        try {
            this.producer.setLastReadLine(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
