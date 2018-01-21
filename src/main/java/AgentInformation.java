import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgentInformation {

    public static void main (String args[]) throws IOException {
        AgentInformation ainf = new AgentInformation();
        System.out.println("Please first enter the localhost IP address (e.g. 127.0.0.1 for most situations)");
        //Scanner sc = new Scanner(System.in);
        //String ip = sc.nextLine();
        //String hostname = ainf.getHostName("127.0.0.1");
        String agentname =  ainf.getHostName("192.168.1.21");
        //System.out.println("HOST: " + hostname);
        System.out.println("AGENT:" + agentname);

    }

    public String getHostName (String ipAddress) throws IOException {
        String hostname = new String();
        String snmpCommand = "1.3.6.1.2.1.1.1";
        String[] args = new String[] {"/bin/bash", "-c", "snmpwalk "+ipAddress +" "+snmpCommand}; //1.3.6.1.2.1.25.4.2.1.2
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
        String[] args = new String[]{"/bin/bash", "-c", "snmpwalk " + ipAddress + " " + snmpCommand}; //1.3.6.1.2.1.25.4.2.1.2
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        Pattern pattern = Pattern.compile("\\S+(?=\")"); //"/([A-z])+\\w(?=\")/g"  \S+(?=\")(?<=\")
        String line;

        while ((line = r.readLine()) != null) {
            Matcher matcher1 = pattern.matcher(line);
            while (matcher1.find()) {
                System.out.println(matcher1.group().substring(1, matcher1.group().length()));
                processes.add(matcher1.group().substring(1, matcher1.group().length()));
            }
        }
        return processes;
    }
}
