package com.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DataSaver {
    public static void saveToFile() throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter("C:\\Users\\Axeno\\Desktop\\Final_Java_Project\\TaskTrackingSystem\\src\\main\\webapp\\WEB-INF\\data\\programData.json");
        gson.toJson(GlobalData.Data, writer);
        writer.close();


        // saving data back to file on system
        Path source = Paths.get(
                "C:\\Users\\Axeno\\Desktop\\Final_Java_Project\\TaskTrackingSystem\\src\\main\\webapp\\WEB-INF\\data\\programData.json"
        );

        Path target = Paths.get(
                "C:\\Users\\Axeno\\Desktop\\random.json"
        );

        Files.copy(
                source,
                target,
                StandardCopyOption.REPLACE_EXISTING
        );
    }
}
