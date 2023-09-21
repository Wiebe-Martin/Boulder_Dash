package com.mygdx.game.world;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class JsonFileReader {
    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("yourfile.json"); // Replace with your JSON file path
            YourPojoClass yourData = objectMapper.readValue(jsonFile, YourPojoClass.class);

            // Now you can work with yourData, which is an instance of YourPojoClass
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

