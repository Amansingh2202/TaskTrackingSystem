package com.util;

import com.google.gson.Gson;
import com.pojo.ProgramsBaseFile;


import java.io.FileReader;
import java.io.FileNotFoundException;

public class DataInitializer {

    public static void initializeData() throws FileNotFoundException {

        FileReader reader = new FileReader(
                "C:\\Users\\Axeno\\Desktop\\Final_Java_Project\\TaskTrackingSystem\\src\\main\\webapp\\WEB-INF\\data\\programData.json"
        );

        Gson gson = new Gson();

        // Creating an object of base class pojo
        ProgramsBaseFile data= gson.fromJson(reader, ProgramsBaseFile.class);

        // assigning that base class pojo object to global object
        GlobalData.Data=data;



    }
}
