import java.io.IOException;
import java.util.ArrayList;

public class ProcessStatistics extends Statistics{
    private String ipAddress;
    public ProcessStatistics (String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAll () throws IOException { //1.3.6.1.2.1.25.4.2.1
        String allProcessStatistics = new String();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1";
        String regexPattern = new String();
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allProcessStatistics;
    }

    public ArrayList<String> getProcessNames () throws IOException { //1.3.6.1.2.1.25.4.2.1.2
        ArrayList<String> processNames = new ArrayList<String>();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.2";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return  processNames;
    }

    public ArrayList<String> getProcessRunDirectories () throws IOException{ // 1.3.6.1.2.1.25.4.2.1.4
        ArrayList<String> runDirectories = new ArrayList<String>();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.4";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return runDirectories;
    }

    public ArrayList<String> getRunParameters () throws IOException{ //1.3.6.1.2.1.25.4.2.1.5
        ArrayList<String> runParameters = new ArrayList<String>();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.5";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return runParameters;
    }

    public ArrayList<String> getProcessTypes () throws IOException{ //1.3.6.1.2.1.25.4.2.1.6
        ArrayList<String> processTypes = new ArrayList<String>();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.6";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return processTypes;
    }

    public ArrayList<String> getRunStatus () throws IOException{ //1.3.6.1.2.125.4.2.1.7
        ArrayList<String> runStatus = new ArrayList<String>();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.7";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return runStatus;
    }

}
