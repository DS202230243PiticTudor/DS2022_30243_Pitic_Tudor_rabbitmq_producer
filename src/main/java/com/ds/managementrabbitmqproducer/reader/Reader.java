package com.ds.managementrabbitmqproducer.reader;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class Reader {
    public static final String SENSOR_VALUES_PATH = "src/main/resources/readings/sensor.csv";
    public static final String LAST_READ_LINE_PATH = "src/main/resources/readings/lastReadLine.txt";
    public Double readFromCSV() {
        try {
            FileReader filereader = new FileReader(SENSOR_VALUES_PATH);
            CSVReader csvReader = new CSVReader(filereader);
            int lineNo = 0;
            String[] readValue = csvReader.readNext();
            BufferedReader brLastReadLine = new BufferedReader(new FileReader(LAST_READ_LINE_PATH));
            String lastReadLine = brLastReadLine.readLine();
            while(readValue.length != 0) {
                lineNo++;
                if(lastReadLine == null || (lineNo > Integer.parseInt(lastReadLine))) {
                    double energyConsumptionValue = Double.parseDouble(readValue[0]);
                    FileWriter writer = new FileWriter(LAST_READ_LINE_PATH, false);
                    writer.write(Integer.toString(lineNo));
                    writer.close();
                    System.out.println(energyConsumptionValue);
                    return energyConsumptionValue;
                }
                readValue = csvReader.readNext();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resetLastReadLine() throws IOException {
        FileWriter writer = new FileWriter(LAST_READ_LINE_PATH, false);
        writer.write(Integer.toString(0));
        writer.close();
    }

    public void setLastReadLine(int line) throws IOException {
        FileWriter writer = new FileWriter(LAST_READ_LINE_PATH, false);
        writer.write(Integer.toString(line));
        writer.close();
    }
}
