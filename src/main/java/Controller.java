import org.apache.jena.base.Sys;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    public Controller() throws IOException {
    }
    final static String password = "password";
    final static String nameSpace = "http://www.semanticweb.org/b/ontologies/2018/0/IdsSNMP#";
    HTMLparser htmlParser = new HTMLparser();
    OntologyConnection ontologyConnection = new OntologyConnection();

    public static void main (String args[]) throws IOException {
        //first, lets get ip addresses from administrator
        Controller c = new Controller();
        System.out.println("Welcome to The Ontology Based IDS With SNMP Engine! Still working on an acronym :)");
        System.out.println("Are you admin? Y/N");
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        //while (!answer.equals("Y") || !answer.equals("y") || !answer.equals("N") || !answer.equals("n")){
        if (answer.equals("N") || answer.equals("n")){
            System.out.println("You shouldn't be here... Exiting system...");
            System.exit(1);
        }else if (answer.equals("Y") || answer.equals("y")) {
            String flag = "No";
            int attempt = 3;
            while (flag.equals("No")) {
                System.out.print("Please enter administrative password: ");
                if (sc.nextLine().equals(password)) {
                    System.out.println("Authentication successful!");
                    flag = "Yes";
                } else {
                    attempt--;
                    System.out.println("Wrong password! Try again. Attempts left: " +attempt);
                    if(attempt==0){
                        System.out.println("Too many faulty attempts! Exiting system...");
                        System.exit(1);
                    }
                }
            }
            System.out.println("Welcome Boss! Run the system or initialize it?");
            System.out.println("NOTE: If you initialized the system, just run it...");
            System.out.println("To run the system please enter R, To initialize the system please enter I");
            String answer1 = sc.nextLine();
            if (answer.equals("R") || answer.equals("r")){
                //run the program
            }else if (answer1.equals("I") || answer.equals("i")){
                //initialize the program
                c.initProgram();

            }
        }else {
            System.out.println("Wrong answer! Only Y/y/N/n is accepted. Exiting system...");
            System.exit(1);
        }
    }

    public void initProgram() throws IOException {
        System.out.println("Please enter the ip addresses you wish to add into your IDS network, when you are done please enter Q");
        ArrayList<String> ipAddresses = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        while (!answer.equals("Q")){
            ipAddresses.add(answer);
            answer = sc.nextLine();
        }
        ArrayList<String> agents = new ArrayList<String>();
        for (String ipAddress : ipAddresses){
            agents.add(ipAddress);
        }

        System.out.println("IP addresses are successfully added!");
        System.out.println("Are all devices belongs to those IP addresses configured for Network Management System? Y/N");
        String answer1 = sc.nextLine();
        int attempts = 5;
        while (!answer1.equals("Y") || !answer1.equals("y") || !answer1.equals("N") || !answer1.equals("n")){
            if (answer1.equals("Y") || answer1.equals("y")){
                System.out.println("Okay! Network scan will begin momentarily...");
                break;
            }else if (answer1.equals("N") || answer1.equals("n")){
                System.out.println("You should configure /etc/snmp/snmpd.conf file first!");
                System.exit(1);
            }else attempts--;
            if (attempts == 0){
                System.out.println("Too many faulty attempts. Exiting system...");
            }
        }

    }
}
