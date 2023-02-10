package com.ds.managementrabbitmqproducer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Device implements Serializable {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("address")
    private String address;
    @JsonProperty("maxEnergyConsumption")
    private float maxEnergyConsumption;

}
