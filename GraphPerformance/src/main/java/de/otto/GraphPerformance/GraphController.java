package de.otto.GraphPerformance;

import de.otto.GraphPerformance.repo.GraphPerformanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class GraphController {



    private final GraphPerformanceRepo service;



    @Autowired
    public GraphController(GraphPerformanceRepo service) {
        this.service = service;
    }


    @GetMapping(path = "/datafindAll")
    @ResponseBody
    public List<Map<Object, Object>> findAll() {

        return service.findAll();
    }


    @RequestMapping(value = "/services/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<Object, Object>> getEmploymentDataById(@PathVariable Long id) {

        return service.getEmploymentDataById(id);

    }

    @RequestMapping(value = "/rate/{rate}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<Object, Object>>getEmploymentDataByRate(@PathVariable String rate) {
        return service.getEmploymentDataByRate(rate);
    }

    @RequestMapping(path = "/byAreaName/{areaName}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<Object, Object>> getEmploymentDataByAreaName(@PathVariable String areaName) {

        return service.getEmploymentDataByAreaName(areaName);
    }

    @DeleteMapping("/delete/{id}")
    public List<Map<Object, Object>> delete(@PathVariable Long id) {

      return service.delete(id);

    }

    @PostMapping(value = "/addVertice")
    public void addNewEmploymentVertice(@Validated @RequestBody EmploymentDataModel newVertice) {

         service.addNewEmploymentVertice(newVertice);


    }

     @PutMapping(value = "/putServices/{cityId}")
     @ResponseBody
    public void updateEmploymentData(@RequestBody EmploymentDataModel updateVertex, @PathVariable Long cityId){

          service.updateEmploymentVertice(updateVertex, cityId);
    }


}
