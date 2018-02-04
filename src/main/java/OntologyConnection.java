import org.apache.jena.ontology.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
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
    final String inputFileName = getWorkingDirectory()+"/snmpbasedids.owl";
    final static String nameSpace = "http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#";
    OntModel model = null;

    public OntologyConnection() throws IOException {

    }

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

    public String getWorkingDirectory () throws IOException {
        String[] args = new String[] {"/bin/bash", "-c", "pwd"};
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line;

        while ((line = r.readLine()) != null ) {
            System.out.println(line);
        }
        return line;
    }

    public OntModel loadLocalOntology () throws IOException {
        //add instances to local ontology and then (another function->public void) shoot ontology to the fuseki server

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null); //initialize without reasoner
        FileManager.get().readModel(model, inputFileName);
        if (model == null){
            throw new IllegalArgumentException("File "+ inputFileName + " not found! Please check the directory and try again.");
        }

        /**define ontology classes and subclasses
        * */
        OntClass internetDomain = model.getOntClass(nameSpace + "InternetDomain");
        OntClass badInternetDomain = model.getOntClass(nameSpace + "BadInternetDomain");
        OntClass agent = model.getOntClass(nameSpace + "Agent");
        OntClass databaseServer = model.getOntClass(nameSpace + "DatabaseServer");
        OntClass networkManagementSystem = model.getOntClass(nameSpace + "NetworkManagementSystem");
        OntClass process = model.getOntClass(nameSpace + "Process");
        OntClass snmpProfile = model.getOntClass(nameSpace + "SnmpProfile");
        OntClass v1Profile = model.getOntClass(nameSpace + "v1Profile");
        OntClass v2cProfile = model.getOntClass(nameSpace + "v2cProfile");
        OntClass v3Profile = model.getOntClass(nameSpace + "v3Profile");
        OntClass software = model.getOntClass(nameSpace + "Software");
        OntClass malware = model.getOntClass(nameSpace + "Malware");
        OntClass statistic = model.getOntClass(nameSpace + "Statistic");
        OntClass systemInformation = model.getOntClass(nameSpace + "SystemInformation");
        OntClass cpuStatistic = model.getOntClass(nameSpace + "CpuStatistic");
        OntClass diskStatistic = model.getOntClass(nameSpace + "DiskStatistic");
        OntClass memoryStatistic = model.getOntClass(nameSpace + "MemoryStatistic");
        OntClass user = model.getOntClass(nameSpace + "User");
        OntClass administrator = model.getOntClass(nameSpace + "Administrator");

        /**define ontology object properties
         * */
        OntProperty isDescribedWith = model.getOntProperty(nameSpace + "isDescribedWith");
        OntProperty isConnectedTo = model.getOntProperty(nameSpace + "isConnectedTo");
        OntProperty hasStatistic = model.getOntProperty(nameSpace + "hasStatistic");
        OntProperty hasLogonRightTo = model.getOntProperty(nameSpace + "hasLogonRightTo");
        OntProperty executes = model.getOntProperty(nameSpace + "executes");
        OntProperty hasProfile = model.getOntProperty(nameSpace + "hasProfile");
        OntProperty isaPartOf = model.getOntProperty(nameSpace + "isaPartOf");
        OntProperty isLoggedOn = model.getOntProperty(nameSpace + "isLoggedOn");
        OntProperty runsOn = model.getOntProperty(nameSpace + "runsOn");

        /**define data properties
        * */
        OntProperty runStaus = model.getOntProperty(nameSpace + "runStatus");
        OntProperty processType = model.getOntProperty(nameSpace + "processType");
        OntProperty runParameter = model.getOntProperty(nameSpace + "runParameter");
        OntProperty systemName = model.getOntProperty(nameSpace + "systemName");
        OntProperty systemUpTime = model.getOntProperty(nameSpace + "systemUpTime");
        OntProperty totalCachedMemory = model.getOntProperty(nameSpace + "totalCachedMemory");
        OntProperty totalRamBuffered = model.getOntProperty(nameSpace + "totalRamBuffered");
        OntProperty totalRamShared = model.getOntProperty(nameSpace + "totalRamShared");
        OntProperty totalRamFree = model.getOntProperty(nameSpace + "totalRamFree");
        OntProperty totalRamUsed = model.getOntProperty(nameSpace + "totalRamUsed");
        OntProperty totalRamInMachine = model.getOntProperty(nameSpace + "totalRamInMachine");
        OntProperty availableSwapSize = model.getOntProperty(nameSpace + "availableSwapSize");
        OntProperty totalSwapSize = model.getOntProperty(nameSpace + "totalSwapSize");
        OntProperty percentageOfInodeOnTheDisk = model.getOntProperty(nameSpace + "percentageOfInodeOnTheDisk");
        OntProperty percentageOfSpaceUsedOnTheDisk = model.getOntProperty(nameSpace + "percentageOfSpaceUsedOnTheDisk");
        OntProperty usedSpaceOnTheDisk = model.getOntProperty(nameSpace + "usedSpaceOnTheDisk");
        OntProperty availableSpaceOnTheDisk = model.getOntProperty(nameSpace + "availableSpaceOnTheDisk");
        OntProperty totalSizeOfTheDiskOrPartition = model.getOntProperty(nameSpace + "totalSizeOfTheDiskOrPartition");
        OntProperty pathOfTheDeviceForThePartition = model.getOntProperty(nameSpace + "pathOfTheDeviceForThePartition");
        OntProperty pathWhereDiskIsMounted = model.getOntProperty(nameSpace + "pathWhereDiskIsMounted");
        OntProperty rawCpuNiceTime = model.getOntProperty(nameSpace + "rawCpuNiceTime");
        OntProperty idleCpuTime = model.getOntProperty(nameSpace + "idleCpuTime");
        OntProperty percentageOfIdleCpuTime = model.getOntProperty(nameSpace + "percentageOfIdleCpuTime");
        OntProperty rawSystemCpuTime = model.getOntProperty(nameSpace + "rawSystemCpuTime");
        OntProperty percentageOfSystemCpuTime = model.getOntProperty(nameSpace + "percentageOfSystemCpuTime");
        OntProperty rawUserCpuTime = model.getOntProperty(nameSpace + "rawUserCpuTime");
        OntProperty percentageOfUserCpuTime = model.getOntProperty(nameSpace + "percentageOfUserCpuTime");
        OntProperty fifteenMinuteLoad = model.getOntProperty(nameSpace + "15MinuteLoad");
        OntProperty fiveMinuteLoad = model.getOntProperty(nameSpace + "5MinuteLoad");
        OntProperty oneMinuteLoad = model.getOntProperty(nameSpace + "1MinuteLoad");
        OntProperty authenticationPassphrase = model.getOntProperty(nameSpace + "authenticationPassphrase");
        OntProperty authenticationType = model.getOntProperty(nameSpace + "authenticationType");
        OntProperty encryptionPassphrase = model.getOntProperty(nameSpace + "encryptionPassphrase");
        OntProperty encryptionType = model.getOntProperty(nameSpace + "encryptionType");
        OntProperty macAddress = model.getOntProperty(nameSpace + "macAddress");
        OntProperty processId = model.getOntProperty(nameSpace + "processID");
        OntProperty publicName = model.getOntProperty(nameSpace + "publicName");
        OntProperty runDirectory = model.getOntProperty(nameSpace + "runDirectory");
        OntProperty securityLevel = model.getOntProperty(nameSpace + "securityLevel");
        OntProperty securityName = model.getOntProperty(nameSpace + "securityName");
        OntProperty type = model.getOntProperty(nameSpace + "type");
        OntProperty username = model.getOntProperty(nameSpace + "username");

        return model;
    }

    public void addPropertiesOfAnIndividualToLocalOntology (Individual individual, OntProperty objectProperty, OntProperty dataProperty,
                                              ArrayList<String> properties) throws IOException {
        FileOutputStream modelToWrite = new FileOutputStream(inputFileName);
        if (objectProperty != null){
            for (int i=0; i < properties.size() ; i++){
                individual.addProperty(objectProperty, properties.get(i));
            }
        }
        if (dataProperty != null) {
            for (int i=0 ; i < properties.size() ; i++){
                individual.addProperty(dataProperty, properties.get(i));
            }
        }
        model.write(modelToWrite, "RDF/XML");
        modelToWrite.flush();
    }

    public void addIndividualsToLocalOntology (OntClass ontClass, ArrayList<String> individuals) throws IOException {
        FileOutputStream modelToWrite = new FileOutputStream(inputFileName);
        for (String individual : individuals){
            Individual instance = model.createIndividual(nameSpace + individual, ontClass);
        }
        model.write(modelToWrite, "RDF/XML");
        modelToWrite.flush();
    }

    public void addIndividualsToOntologyOnFusekiServer () {
        //INSERT or UPDATE
    }

    public void deleteIndividuals () {

    }

    public static void main(String argv[]) throws IOException {

        /*uploadRDF(new File("/home/batu/IdeaProjects/SnmpBasedIDS/snmpids.owl"), "http://localhost:3030/ds/data");
        execSelectAndPrint("http://localhost:3030/ds/sparql",
                "PREFIX snmp: <http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#>" +
                        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                "SELECT ?object WHERE {?s ?p ?object}");*/

    }
}
