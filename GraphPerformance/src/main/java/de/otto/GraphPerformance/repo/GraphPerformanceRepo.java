package de.otto.GraphPerformance.repo;

import de.otto.GraphPerformance.EmploymentDataModel;

import java.util.List;
import java.util.Map;


public interface GraphPerformanceRepo{
     List<Map<Object, Object>> findAll();
     List<Map<Object, Object>> getEmploymentDataById(Long id);
     List<Map<Object, Object>> getEmploymentDataByRate(String rate);
     List<Map<Object, Object>> getEmploymentDataByAreaName(String areaname);
     void  addNewEmploymentVertice(EmploymentDataModel newVertice);
     void updateEmploymentVertice(EmploymentDataModel updateVertex, Long cityId);
     List<Map<Object, Object>> delete(Long cityId);

}
