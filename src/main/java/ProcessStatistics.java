import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessStatistics extends Statistics{
    private String ipAddress;
    public ProcessStatistics (String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAll () throws IOException { //1.3.6.1.2.1.25.4.2.1
        String allProcessStatistics = new String();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1";
        String regexPattern = "(?<=: ).+";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allProcessStatistics;
    }

    public HashMap<String, String> getProcessNames () throws IOException { //1.3.6.1.2.1.25.4.2.1.2
        HashMap<String, String> processes = new HashMap<String, String>();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.2";
        String regexPattern = "(?<=: ).+";
        String regexPatternForId = "(?<!Name)\\d+(?= )";
        ArrayList<String> processNames = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        ArrayList<String> processIds = runSnmpCommand(snmpCommand , regexPatternForId, ipAddress);
        for (int i=0 ; i < processIds.size(); i++){
            processes.put(processIds.get(i), processNames.get(i));
        }
        for (String name : processNames)
            System.out.println(name);
        return  processes;
    }

    public ArrayList<String> getProcessRunDirectories () throws IOException{ // 1.3.6.1.2.1.25.4.2.1.4
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.4";
        String regexPattern = "(?<=: ).+";
        ArrayList<String> runDirectories = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return runDirectories;
    }

    public ArrayList<String> getRunParameters () throws IOException{ //1.3.6.1.2.1.25.4.2.1.5
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.5";
        String regexPattern = "(?<=: ).+";
        ArrayList<String> runParameters = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return runParameters;
    }

    public ArrayList<String> getProcessTypes () throws IOException{ //1.3.6.1.2.1.25.4.2.1.6
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.6";
        String regexPattern = "(?<=: ).+";
        ArrayList<String> processTypes = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return processTypes;
    }

    public ArrayList<String> getRunStatus () throws IOException{ //1.3.6.1.2.125.4.2.1.7
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.7";
        String regexPattern = "(?<=: ).+";
        ArrayList<String> runStatus = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return runStatus;
    }

}
