package de.otto.GraphPerformance;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Configuration
public class IngestCsvToJavaObjekt {


    public IngestCsvToJavaObjekt() {

    }


    /**
     * @return List of EmploymentDataModel
     */
    public List<EmploymentDataModel> getArrayOfModels() throws IOException {

        String fileName = "C:\\Program Files\\MongoDB\\Server\\4.4\\bin\\Unemployment_Statistics.csv";



       BufferedReader reader = null;
        try {
            Path pathToFile = Paths.get(fileName);
              reader = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<EmploymentDataModel> csvArrayList = new ArrayList<>();

        assert reader != null;
        String row = reader.readLine();

        while (row != null) {
            try {

                if ((row = reader.readLine()) == null){
                    break;
                }
                String[] column = row.split(",");
                csvArrayList.add(getOneDataFromTable(column));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return csvArrayList;
    }


    private static EmploymentDataModel getOneDataFromTable(String[] column) {


        EmploymentDataModel model = new EmploymentDataModel();

        model.setAreaType(column[0]);
        model.setAreaName(column[1]);
        model.setDatum(column[2]);
        model.setYear(column[3]);
        model.setMonth(column[4]);
        model.setSeason(column[5]);
        model.setStatus(column[6]);
        model.setLabor(column[7]);
        model.setEmployment(column[8]);
        model.setUnemployment(column[9]);
        model.setRate(column[10]);

        return  model;
    }

}
