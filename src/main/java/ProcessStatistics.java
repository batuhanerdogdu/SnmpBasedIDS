import java.io.IOException;
import java.util.ArrayList;

public class ProcessStatistics extends Statistics{
    private String ipAddress;
    public ProcessStatistics (String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public class Process {
        public String id;
        public String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public String getAll () throws IOException { //1.3.6.1.2.1.25.4.2.1
        String allProcessStatistics = new String();
        String snmpCommand = "1.3.6.1.2.1.25.4.2.1";
        String regexPattern = "(?<=: ).+";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allProcessStatistics;
    } //not working for now, better regex required

    public ArrayList<Process> getProcessNames () throws IOException { //1.3.6.1.2.1.25.4.2.1.2

        String snmpCommand = "1.3.6.1.2.1.25.4.2.1.2";
        String regexPattern = "(?<=\")\\S.+(?=\")";
        String regexPatternForId = "(?<!Name)\\d+(?= )";
        ArrayList<String> processNames = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        ArrayList<String> processIds = runSnmpCommand(snmpCommand , regexPatternForId, ipAddress);
        ArrayList<Process> processes= new ArrayList<Process>();
        //String [][] processes = new String[processIds.size()][2];
        //System.out.println("ids: "+processIds.size() +" names: " + processNames.size());
        for (int i=0 ; i < processIds.size(); i++){
            System.out.println("id:" + processIds.get(i) + " name: "+ processNames.get(i));
            Process process = new Process();
            process.setId(processIds.get(i));
            process.setName(processNames.get(i));
            processes.add(process);
        }
        //for (String name : processNames)
            //System.out.println(name);
        //System.out.println(processes);
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
