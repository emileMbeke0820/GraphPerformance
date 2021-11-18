package de.otto.GraphPerformance;


import de.otto.GraphPerformance.repo.GraphPerformanceRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.ser.Serializers;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.WithOptions;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@Repository
public class IngestJavaToNeptune implements GraphPerformanceRepo {


    private static final String VERTEX_NAME = "statistik";

    // Create a new (empty) TinkerGrap
    TinkerGraph tg = TinkerGraph.open();
    // Create a Traversal source object
    protected final GraphTraversalSource g;


    public IngestJavaToNeptune(){

        Cluster cluster = Cluster.build()
                .addContactPoint("localhost")
                .port(8182)
                .serializer(Serializers.GRAPHBINARY_V1D0)
                .create();
        //Client client = cluster.connect();
      //  g = traversal().withRemote(DriverRemoteConnection.using(cluster));
         g = tg.traversal();

    }

    public  void updateFromJavaToGremlin(EmploymentDataModel entityData, GraphTraversalSource  ts, String vertexName) {
        addVertice(entityData, ts, vertexName);
    }

    /**
     * jede einzelne zeile aus der List in der Datenbank hinzufügen!
     * @param entityData  Liste aller Einträge, die in der Datenbank kommen.
     */
    public void addDataToList(List<EmploymentDataModel>  entityData){


        //ForEach
        for (EmploymentDataModel dataModel : entityData) {
            updateFromJavaToGremlin(dataModel, g, VERTEX_NAME);
        }

    }

    @Override
    public List<Map<Object, Object>> findAll() {
        return g.V().valueMap().with(WithOptions.tokens).toList();
    }

    @Override
    public List<Map<Object, Object>> getEmploymentDataById(Long id) {

        return g.V(id).valueMap().with(WithOptions.tokens).toList();
    }

    @Override
    public List<Map<Object, Object>> getEmploymentDataByRate(String rate) {

        return  g.V().has("rate",rate).valueMap().with(WithOptions.tokens).toList();

    }

    @Override
    public List<Map<Object, Object>> getEmploymentDataByAreaName(String areaName) {
        return  g.V().has("areaName", areaName).valueMap().toList();

    }

    @Override
    public void addNewEmploymentVertice(EmploymentDataModel newVertice) {

        addVertice(newVertice, g, VERTEX_NAME);

    }

    private void addVertice(EmploymentDataModel newVertice, GraphTraversalSource g, String vertexName) {


        final var ts=     g.addV(vertexName)
                     .property(VertexProperty.Cardinality.single, "areatype", newVertice.getAreaType())
                     .property(VertexProperty.Cardinality.single, "areaName", newVertice.getAreaName())
                     .property(VertexProperty.Cardinality.single, "datum", newVertice.getDatum())
                     .property(VertexProperty.Cardinality.single, "year", newVertice.getYear())
                     .property(VertexProperty.Cardinality.single, "month", newVertice.getMonth())
                     .property(VertexProperty.Cardinality.single, "season", newVertice.getSeason())
                     .property(VertexProperty.Cardinality.single, "status", newVertice.getStatus())
                     .property(VertexProperty.Cardinality.single, "labor", newVertice.getLabor())
                     .property(VertexProperty.Cardinality.single, "employment",newVertice.getEmployment())
                     .property(VertexProperty.Cardinality.single, "unemployment", newVertice.getUnemployment())
                     .property(VertexProperty.Cardinality.single, "rate",newVertice.getRate());

        ts.iterate();
    }

    @Override
    public void updateEmploymentVertice(EmploymentDataModel updateVertex, Long cityId) {

        addVertice(updateVertex, g, VERTEX_NAME);
    }

    @Override
    public List<Map<Object, Object>> delete(Long id) {

        g.V(id).drop().iterate();
        return getEmploymentDataById(id);

    }


    public Long getNumberOfEntities() {

        /*
        frage die DB: selektiere Knoten, mit Eigenschaft
         (Eintrag den wir in der Methode selbst hinzugefügt haben);
         zähle die Anzahl dieser Knoten, gib die Anzahl zurück
         */

        //The command counts how many vertices (nodes) are in the graph.
        return g.V().hasLabel(VERTEX_NAME).count().next();
         //g.V().group().by(label).by(count());


    }



    /*
    Importer
     */
    @PostConstruct
    public void dataModelList() throws IOException {
        IngestCsvToJavaObjekt caller = new IngestCsvToJavaObjekt();

        addDataToList(caller.getArrayOfModels());

        System.out.println("Import fertig");
        System.out.println(getNumberOfEntities());
 }



}
