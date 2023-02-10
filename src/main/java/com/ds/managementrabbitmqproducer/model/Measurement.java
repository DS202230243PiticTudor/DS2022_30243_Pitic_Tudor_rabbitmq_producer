package com.ds.managementrabbitmqproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Measurement implements Serializable {
    @JsonProperty("personId")
    private UUID personId;
    @JsonProperty("recordedDate")
    private Date recordedDate;
    @JsonProperty("deviceId")
    private UUID deviceId;
    @JsonProperty("energyConsumption")
    private double energyConsumption;
}
