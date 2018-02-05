import org.apache.jena.ontology.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.OneToManyMap;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OntologyConnection {
    private static final Logger LOGGER = LogManager.getLogger(OntologyConnection.class);
    final static String nameSpace = "http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#";
    final static String serviceURIforSelect = "http://localhost:3030/ds/sparql";
    final static String serviceURIforUpdate = "http://localhost:3030/ds/update";
    final static String serviceURIforData = "http://localhost:3030/ds/data";
    final static String prefixSNMP = "PREFIX snmp: <http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#>";
    final static String prefixRDF = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    final static String prefixRDFS = "PREFIX rdfs: <http://w3.org/2000/01/rdf-schema#>";
    final static String prefixOWL = "http://www.w3.org/2002/07/owl#";
    final static String prefixXML = "http://www.w3.org/XML/1998/namespace";
    final static String prefixXSD = "http://www.w3.org/2001/XMLSchema#";


    public OntologyConnection() throws IOException {
        String inputFileName = getWorkingDirectory()+"/snmpids.owl";
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null); //initialize without reasoner
        InputStream in = FileManager.get().open(inputFileName);
        model.read(in, null);
        if (model == null){
            throw new IllegalArgumentException("File "+ inputFileName + " not found! Please check the directory and try again.");
        }
    }

    public static void uploadRDF(File rdf , String URI)
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
        DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(URI);
        accessor.putModel(m);

    }

    public static void execSelectAndPrint(String query) {
        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURIforSelect,
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

    public static void insertIndividuals (ArrayList<String> instances, String className) {
        UpdateRequest update = UpdateFactory.create(prefixSNMP + " " + prefixRDF + " " + prefixRDFS + " "+
                prefixOWL + " " + prefixXML + " " + prefixXSD +" " + "\n" + "INSERT DATA " +
                " { "+ "<"+ nameSpace+instances.get(0)+">"+" rdf:type "+ "snmp:"+className +".}");
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update,
                serviceURIforUpdate);
        processor.execute();
    }

    public static void deleteIndividuals (ArrayList<String> instances, String className){
        UpdateRequest update = UpdateFactory.create(prefixSNMP + " " + prefixRDF + " " + prefixRDFS + " "+
                prefixOWL + " " + prefixXML + " " + prefixXSD +" " + "\n" + "DELETE DATA " +
                " { "+ "<"+ nameSpace+instances.get(0)+">"+" rdf:type "+ "snmp:"+className +".}");
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update,
                serviceURIforUpdate);
        processor.execute();
    }

    public String getWorkingDirectory () throws IOException {
        String[] args = new String[] {"/bin/bash", "-c", "pwd"};
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line;
        String result = null;

        while ((line = r.readLine()) != null ) {
            result = line;
            //System.out.println(line);
        }
        return result;
    }

    public void deleteIndividuals () {

    }

    public static void main(String argv[]) throws IOException {
        OntologyConnection oc = new OntologyConnection();
        HTMLparser htmLparser = new HTMLparser();
        ProcessStatistics ps = new ProcessStatistics("127.0.0.1");
        ArrayList<String> badIPs = htmLparser.getHTMLcontentOfBadIpCom();
        ArrayList<Malware> malwaresOfSymantec = htmLparser.getHMTLcontentOfSymantecCom();
        ArrayList<BadIP> badIPsOfDomainList = htmLparser.getHTMLcontentOfMalwareDomainListCom();
        ArrayList<String> processNames = ps.getProcessNames();
        ArrayList<String> processDirs = ps.getProcessRunDirectories();


        uploadRDF(new File("/home/batu/IdeaProjects/SnmpBasedIDS/snmpids.owl"),
                serviceURIforData);
        ArrayList<String> n = new ArrayList<String>();
        n.add("1.1.1.1");
        insertIndividuals(n, "Agent");
        execSelectAndPrint(prefixSNMP+ prefixRDF+
                "SELECT ?s WHERE {?s rdf:type snmp:Agent}");

        /*prefix rdfs: <http://w3.org/2000/01/rdf-schema#>
prefix snmp: <http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#>
prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
insert data
{graph <http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP>
{<http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#127.0.0.1> rdf:type snmp:Agent.}}*/


    }
}
