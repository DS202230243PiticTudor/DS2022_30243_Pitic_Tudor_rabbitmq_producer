package com.ds.managementrabbitmqproducer.producer;

import com.ds.managementrabbitmqproducer.configuration.RabbitMQConfiguration;
import com.ds.managementrabbitmqproducer.model.Device;
import com.ds.managementrabbitmqproducer.model.Measurement;
import com.ds.managementrabbitmqproducer.reader.Reader;
import com.ds.managementrabbitmqproducer.service.DeviceService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private Reader reader;

    @Autowired
    private DeviceService deviceService;
    public Producer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void composeAndSendMessage() throws IOException {
        UUID personId = this.readIdFromConfigFile();
        List<Device> devices = getDevices(personId);
        List<Measurement> measurements = new ArrayList<>();
        Double energyConsumption = this.readFromCSV();
        Date recordedDate = new Date();
        for(Device device : devices) {
            Measurement measurement = Measurement.builder()
                    .personId(personId)
                    .deviceId(device.getId())
                    .recordedDate(recordedDate)
                    .energyConsumption(energyConsumption)
                    .build();
            measurements.add(measurement);
            System.out.println(measurement);
        }
        rabbitTemplate.convertAndSend(
                RabbitMQConfiguration.EXCHANGE_NAME,
                RabbitMQConfiguration.ROUTING_KEY,
                measurements
        );
    }

    private Double readFromCSV() throws IOException{
        return reader.readFromCSV();
    }

    private List<Device> getDevices(UUID personId) {
        return this.deviceService.getPersonDevices(personId);
    }

    public void replacePersonIdInConfigFile(UUID personId) throws IOException {
        String configFilePath = "src/main/resources/config.properties";
        FileInputStream propInput = new FileInputStream(configFilePath);
        Properties prop = new Properties();
        prop.load(propInput);
        propInput.close();
        FileOutputStream propOut = new FileOutputStream(configFilePath);
        prop.setProperty("PERSON_ID", personId.toString());
        prop.store(propOut, null);
        propOut.close();
    }

    private UUID readIdFromConfigFile() throws IOException {
        String configFilePath = "src/main/resources/config.properties";
        FileInputStream propInput = new FileInputStream(configFilePath);
        Properties prop = new Properties();
        prop.load(propInput);
        return UUID.fromString(prop.getProperty("PERSON_ID"));
    }

    public void resetLastReadLine() throws IOException {
        this.reader.resetLastReadLine();
    }

    public void setLastReadLine(int line) throws IOException {
        this.reader.setLastReadLine(line);
    }
}
