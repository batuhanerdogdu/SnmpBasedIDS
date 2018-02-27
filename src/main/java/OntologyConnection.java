import org.apache.jena.ontology.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.*;
import org.apache.jena.util.FileManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class OntologyConnection {
    private static final Logger LOGGER = LogManager.getLogger(OntologyConnection.class);
    final static String nameSpace = "http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#";
    final static String serviceURIforSelect = "http://localhost:3030/ds/sparql";
    final static String serviceURIforUpdate = "http://localhost:3030/ds/update";
    final static String serviceURIforData = "http://localhost:3030/ds/data";
    final static String prefixSNMP = "PREFIX snmp: <http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#>";
    final static String prefixRDF = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    final static String prefixRDFS = "PREFIX rdfs: <http://w3.org/2000/01/rdf-schema#>";
    final static String prefixOWL = "PREFIX owl: <http://www.w3.org/2002/07/owl#>";
    final static String prefixXML = "PREFIX xml: <http://www.w3.org/XML/1998/namespace>";
    final static String prefixXSD = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
    final static String rdfType = "rdf:type";
    final static String rdfsDomain = "rdfs:domain";
    final static String rdfsRange = "rdfs:range";
    final static String owlClass = "owl:class";



    public OntologyConnection() throws IOException {

        String inputFileName = getWorkingDirectory()+"/snmpids.owl";
        System.out.println(getWorkingDirectory());
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null); //initialize without reasoner
        InputStream in = FileManager.get().open(inputFileName);
        model.read(in, null);
        if (model == null){
            throw new IllegalArgumentException("File "+ inputFileName + " not found! Please check the directory and try again.");
        }
        /**the below commented code has to run when the websites add new items, for now they are all in ontology
        a code will be created for updating automatically later***/

        /*FileOutputStream modelToWrite = null;

        //add malwares and badinternet domains

        HTMLparser htmLparser = new HTMLparser();
        ArrayList<Malware> malwares = htmLparser.getHMTLcontentOfSymantecCom();
        ArrayList<BadDomain> badDomains = htmLparser.getAllBadDomains();

        OntClass malware = model.getOntClass(nameSpace + "Malware");
        OntClass badInternetDomain = model.getOntClass(nameSpace + "BadInternetDomain");
        OntProperty type = model.getOntProperty(nameSpace + "type");
        OntProperty discoveryDate = model.getOntProperty(nameSpace + "discoveryDate");
        OntProperty asn = model.getOntProperty(nameSpace + "asn");
        OntProperty registrant = model.getOntProperty(nameSpace + "registrant");
        OntProperty description = model.getOntProperty(nameSpace + "description");
        OntProperty domainName = model.getOntProperty(nameSpace + "domainName");
        OntProperty reverseLookupAddress = model.getOntProperty(nameSpace + "reverseLookupAddress");

        for (Malware m : malwares){
            modelToWrite = new FileOutputStream(inputFileName);
            Individual individual = model.createIndividual(nameSpace + m.getName(), malware);
            individual.addProperty(type, m.getType());
            individual.addProperty(discoveryDate, m.getDiscoveryDate());
            model.write(modelToWrite, "RDF/XML");
        }

        for (BadDomain bd : badDomains) {
            modelToWrite = new FileOutputStream(inputFileName);
            Individual individual = model.createIndividual(nameSpace + bd.getIpAddress(), badInternetDomain);
            individual.addProperty(discoveryDate, bd.getDateOfDiscovery());
            individual.addProperty(asn, bd.getAsn());
            individual.addProperty(registrant, bd.getRegistrant());
            individual.addProperty(description, bd.getDescription());
            individual.addProperty(reverseLookupAddress, bd.getReverseLookupAddress());
            for (String dName : bd.getDomainName()){
                individual.addProperty(domainName, dName);
            }
            model.write(modelToWrite, "RDF/XML");
        }
        modelToWrite.flush();*/
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
        System.out.println("RDF uploaded");

    }

    public void execSelectAndPrint(String query) {
        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURIforSelect,
                query);
        ResultSet results = q.execSelect();

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //convert to JSON format
        //ResultSetFormatter.outputAsJSON(outputStream, results);
        ResultSetFormatter.outputAsCSV(outputStream, results);
        //turn json to string
        String json = new String(outputStream.toString());
        //print json string
        System.out.println(json);

    }

    public ArrayList<String> execSelectAndProcess(String query) {
        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURIforSelect,
                query);
        ResultSet rs = q.execSelect();
        ArrayList<String> results = new ArrayList<String>();
        while (rs.hasNext()) {
            QuerySolution soln = rs.nextSolution();
            // assumes that you have an "?x" in your query
            RDFNode x = soln.get("x");
            //System.out.println(x);
            results.add(x.toString().replace(nameSpace, ""));
            System.out.println(x.toString().replace(nameSpace, ""));
        }
        return results;
    }

    public void insertIndividuals (ArrayList<String> instances, String className) {
        String queryBeginning = prefixSNMP + " " + prefixRDF + " " + prefixRDFS + " "+
                prefixOWL + " " + prefixXML + " " + prefixXSD +" " + "\n" + "INSERT DATA " +
                " { ";
        String queryMid = new String();
        String queryEnding = "}";
        for (String instance : instances ){
            queryMid+= "<" + nameSpace+instance + "> " + rdfType+ " snmp:" + className + ".\n";
        }
        //System.out.println(queryBeginning+ queryMid+ queryEnding);
        UpdateRequest update = UpdateFactory.create(queryBeginning + queryMid + queryEnding);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update,
                serviceURIforUpdate);
        processor.execute();
    }

    public void deleteIndividuals (ArrayList<String> instances, String className){

        String queryBeginning = prefixSNMP + " " + prefixRDF + " " + prefixRDFS + " "+
                prefixOWL + " " + prefixXML + " " + prefixXSD +" " + "\n" + "DELETE DATA " +
                " { ";
        String queryMid = new String();
        String queryEnding = "}";
        for (String instance : instances ){
            queryMid+= "<" + nameSpace + instance + "> " + rdfType + " snmp:" + className + ".\n";
        }
        UpdateRequest update = UpdateFactory.create(queryBeginning + queryMid + queryEnding);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update,
                serviceURIforUpdate);
        processor.execute();
    }

    public void insertObjectProperties (String domainClassInstanceName, String opName, String rangeClassInstanceName) {
        String query = prefixSNMP + " " + prefixRDF + " " + prefixRDFS + " "+
                prefixOWL + " " + prefixXML + " " + prefixXSD +" " + "\n" + "INSERT DATA " +
                " { " + domainClassInstanceName + " snmp:" + opName + " " +rangeClassInstanceName;
        UpdateRequest update = UpdateFactory.create(query);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update,
                serviceURIforUpdate);
        processor.execute();
    }

    public void insertDataProperties (String instanceName, String propertyName, ArrayList<String> properties){
        String queryBeginning = prefixSNMP + " " + prefixRDF + " " + prefixRDFS + " "+
                prefixOWL + " " + prefixXML + " " + prefixXSD +" " + "\n" + "INSERT DATA " +
                " { ";
        String queryMid = new String();
        String queryEnding = "}";
        for (String property : properties){
            queryMid += "<"+nameSpace + instanceName + "> " + "<"+ nameSpace + propertyName + "> " +
                    "\""+ property + "\"" + ".\n";
        }
        UpdateRequest update = UpdateFactory.create(queryBeginning + queryMid + queryEnding);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update,
                serviceURIforUpdate);
        processor.execute();
    }

    public void deleteDataProperties (String instanceName, String propertyName, ArrayList<String> properties){
        String queryBeginning = prefixSNMP + " " + prefixRDF + " " + prefixRDFS + " "+
                prefixOWL + " " + prefixXML + " " + prefixXSD +" " + "\n" + "DELETE DATA " +
                " { ";
        String queryMid = new String();
        String queryEnding = "}";
        for (String property : properties){
            queryMid += "<"+nameSpace + instanceName + "> " + "<"+ nameSpace + propertyName + "> " +
                    "\""+ property + "\"" + ".\n";
        }
        UpdateRequest update = UpdateFactory.create(queryBeginning + queryMid + queryEnding);
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


    public static void main(String argv[]) throws IOException {
        OntologyConnection oc = new OntologyConnection();
        //HTMLparser htmLparser = new HTMLparser();
        //ProcessStatistics ps = new ProcessStatistics("192.168.43.130");
        //ArrayList<String> badIPs = htmLparser.getHTMLcontentOfBadIpCom();
        //ArrayList<Malware> malwaresOfSymantec = htmLparser.getHMTLcontentOfSymantecCom();
        //ArrayList<BadDomain> badIPsOfDomainList = htmLparser.getHTMLcontentOfMalwareDomainListCom();
        //ps.getProcessNames();
        //ArrayList<String> processDirs = ps.getProcessRunDirectories();


        uploadRDF(new File(oc.getWorkingDirectory()+"/snmpids.owl"),
                serviceURIforData);
        ArrayList<String> n = new ArrayList<String>();
        n.add("2.2.2.2");
        n.add("1.1.1.1");
        n.add("127.0.0.1");
        n.add("0.0.0.0");
        oc.insertIndividuals(n, "Agent");
        oc.execSelectAndProcess(prefixSNMP + "\n" + prefixRDF + "\nSELECT ?x WHERE {?x rdf:type snmp:Agent.}");
        oc.execSelectAndProcess(prefixSNMP + "\n" + prefixRDF + "\nSELECT ?x WHERE {?x rdf:type snmp:Malware.}");
        oc.deleteIndividuals(n, "Agent");
        oc.execSelectAndProcess(prefixSNMP + "\n" +prefixRDF +
                "\nSELECT ?x WHERE {?x rdf:type snmp:Agent.}");
    }
}
