package de.otto.GraphPerformance;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class EmploymentDataModel {


    private Long cityId;
    private String areaType;
    private String areaName;
    private String datum;
    private String year;
    private String month;
    private String season;
    private String status;
    private String labor;
    private String employment;
    private String unemployment;
    private String rate;

    public EmploymentDataModel() {

    }

    public EmploymentDataModel(String areaType, String areaName, String datum, String year, String month, String season, String status, String labor, String employment, String unemployment, String rate) {
        this.areaType = areaType;
        this.areaName = areaName;
        this.datum = datum;
        this.year = year;
        this.month = month;
        this.season = season;
        this.status = status;
        this.labor = labor;
        this.employment = employment;
        this.unemployment = unemployment;
        this.rate = rate;
    }


}
