/***************************************
 gets agent name, up time, process information for IDS
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgentInformation {

    private String ipAdress;

    public AgentInformation(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public static void main (String args[]) throws IOException {
        ArrayList<String> sysUpTime = new ArrayList<String>();
        AgentInformation ainf = new AgentInformation("127.0.0.1");

        System.out.println("Please first enter the localhost IP address (e.g. 127.0.0.1 for most situations)");
        //Scanner sc = new Scanner(System.in);
        //String ip = sc.nextLine();
        //String hostname = ainf.getHostName("127.0.0.1");
        String agentname =  ainf.getHostName();
        //System.out.println("HOST: " + hostname);
        System.out.println("AGENT:" + agentname);
        sysUpTime = ainf.getSystemUpTime();
        //System.out.println(sysUpTime.get(1));

    }

    public String getHostName () throws IOException {
        String hostname = new String();
        String snmpCommand = "1.3.6.1.2.1.1.1";
        String[] args = new String[] {"/bin/bash", "-c", "snmpwalk "+this.ipAdress +" "+snmpCommand}; //1.3.6.1.2.1.25.4.2.1.2
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        Pattern pattern = Pattern.compile("((?<= STRING: ))+\\w.+(?=$)"); //"/([A-z])+\\w(?=\")/g"  \S+(?=\")(?<=\")
        String line;

        while ((line = r.readLine()) != null ) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                //System.out.println(matcher.group().substring(0,matcher.group().length()));
                hostname = (matcher.group().substring(0,matcher.group().length()));
            }
        }
        return hostname;
    }

    public ArrayList<String> getProcesses (String ipAddress) throws IOException {
        ArrayList<String> processes = new ArrayList<String>();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.2";
        String[] args = new String[]{"/bin/bash", "-c", "snmpwalk " + this.ipAdress + " " + snmpCommand}; //1.3.6.1.2.1.25.4.2.1.2
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        Pattern pattern = Pattern.compile("\\S+(?=\")"); //"/([A-z])+\\w(?=\")/g"  \S+(?=\")(?<=\")
        String line;

        while ((line = r.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                System.out.println(matcher.group().substring(1, matcher.group().length()));
                processes.add(matcher.group().substring(1, matcher.group().length()));
            }
        }
        return processes;
    }

    public ArrayList<String> getSystemUpTime () throws IOException {
        ArrayList<String> systemUpTime = new ArrayList<String> ();
        String snmpCommand = "1.3.6.1.2.1.1.3.0";
        String[] args = new String []{"/bin/bash", "-c", "snmpwalk " + this.ipAdress + " " + snmpCommand};
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        Pattern pattern = Pattern.compile("([0-9])\\S+(?! )");
        String line;

        while ((line = r.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()){
                System.out.println(matcher.group().substring(0, matcher.group().length()));
                systemUpTime.add(matcher.group().substring(0, matcher.group().length())) ;
            }
        }
        return systemUpTime;
    }
}
