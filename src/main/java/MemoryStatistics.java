import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*****************************************************************
 * get memory statistics for IDS
 all values are in kilobytes
 ******************************************************************/
public class MemoryStatistics extends Statistics {

    private String ipAddress;

    public MemoryStatistics (String ipAddress) {

        this.ipAddress = ipAddress;
    }


    public HashMap<String,String> getAllMemoryStatistics () throws IOException { //1.3.6.1.4.1.2021.4
        HashMap<String, String> allMemoryStatistics = new HashMap<String, String>();
        String snmpCommand = "1.3.6.1.4.1.2021.4";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allMemoryStatistics;
    }

    public int getTotalSwapSize () throws IOException { //1.3.6.1.4.1.2021.4.3.0
        int totalSwapSize = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.4.3";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return totalSwapSize;
    }
    public int getAvailableSwapSpace () throws IOException { //1.3.6.1.4.1.2021.4.4.0
        int availableSwapSpace = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.4.4";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return availableSwapSpace;
    }
    public int getTotalRAMinMachine () throws IOException { //1.3.6.1.4.1.2021.4.5.0
        int totalRAMinMachine = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.4.5";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return totalRAMinMachine;
    }
    public int getTotalRAMused () throws IOException { //1.3.6.1.4.1.2021.4.6.0
        int totalRAMused = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.4.6";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return totalRAMused;
    }
    public int getTotalRAMfree () throws IOException { //1.3.6.1.4.1.2021.4.11.0
        int totalRAMfree = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.4.11";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return  totalRAMfree;
    }
    public int getTotalRAMshared () throws IOException { //1.3.6.1.4.1.2021.4.13.0
        int totalRAMshared = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.4.13";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return totalRAMshared;
    }
    public int getTotalRAMBuffered () throws IOException { //1.3.6.1.4.1.2021.4.14.0
        int totalRAMbuffered = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.4.14";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return totalRAMbuffered;
    }
    public int getTotalCachedMemory () throws IOException { //1.3.6.1.4.1.2021.4.15.0
        int totalCachedMemory = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.4.15";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return totalCachedMemory;
    }
}
