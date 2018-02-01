import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.util.FileManager;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class OntologyConnection {
    private static final Logger LOGGER = LogManager.getLogger(OntologyConnection.class);

    public static void uploadRDF(File rdf, String serviceURI)
            throws IOException {

        // parse the file
        Model m = ModelFactory.createDefaultModel();
        try  {
            FileInputStream in = new FileInputStream(rdf);
            m.read(in, null, "RDF/XML");
        }catch (IOException ex){
            LOGGER.trace(ex);
        }

        // upload the resulting model
        DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(serviceURI);
        accessor.putModel(m);

    }

    public static void execSelectAndPrint(String serviceURI, String query) {
        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,
                query);
        ResultSet results = q.execSelect();

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //convert to JSON format
        ResultSetFormatter.outputAsJSON(outputStream, results);
        //turn json to string
        String json = new String(outputStream.toByteArray());
        //print json string
        System.out.println(json);

    }

    public static void execSelectAndProcess(String serviceURI, String query) {
        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,
                query);
        ResultSet results = q.execSelect();

        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();
            // assumes that you have an "?x" in your query
            RDFNode x = soln.get("x");
            System.out.println(x);
        }
    }

    public static void main(String argv[]) throws IOException {
        // uploadRDF(new File("test.rdf"), );

        uploadRDF(new File("/home/batu/IdeaProjects/SnmpBasedIDS/snmpids.owl"), "http://localhost:3030/ds/data");
        execSelectAndPrint("http://localhost:3030/ds/sparql",
                "PREFIX snmp: <http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#>" +
                        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                "SELECT ?object WHERE {?s ?p ?object}");


    }
}
