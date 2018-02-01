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
        String regexPattern = "(?<=: ).+";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allMemoryStatistics;
    }

    public int getTotalSwapSize () throws IOException { //1.3.6.1.4.1.2021.4.3.0
        String snmpCommand = "1.3.6.1.4.1.2021.4.3";
        String regexPattern = "(?<=: )\\d+";
        int totalSwapSize = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return totalSwapSize;
    }

    public int getAvailableSwapSpace () throws IOException { //1.3.6.1.4.1.2021.4.4.0
        String snmpCommand = "1.3.6.1.4.1.2021.4.4";
        String regexPattern = "(?<=: )\\d+";
        int availableSwapSpace = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return availableSwapSpace;
    }

    public int getTotalRAMinMachine () throws IOException { //1.3.6.1.4.1.2021.4.5.0
        String snmpCommand = "1.3.6.1.4.1.2021.4.5";
        String regexPattern = "(?<=: )\\d+";
        int totalRAMinMachine = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return totalRAMinMachine;
    }

    public int getTotalRAMused () throws IOException { //1.3.6.1.4.1.2021.4.6.0
        String snmpCommand = "1.3.6.1.4.1.2021.4.6";
        String regexPattern = "(?<=: )\\d+";
        int totalRAMused = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return totalRAMused;
    }

    public int getTotalRAMfree () throws IOException { //1.3.6.1.4.1.2021.4.11.0
        String snmpCommand = "1.3.6.1.4.1.2021.4.11";
        String regexPattern = "(?<=: )\\d+";
        int totalRAMfree = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return  totalRAMfree;
    }

    public int getTotalRAMshared () throws IOException { //1.3.6.1.4.1.2021.4.13.0
        String snmpCommand = "1.3.6.1.4.1.2021.4.13";
        String regexPattern = "(?<=: )\\d+";
        int totalRAMshared = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return totalRAMshared;
    }

    public int getTotalRAMBuffered () throws IOException { //1.3.6.1.4.1.2021.4.14.0
        String snmpCommand = "1.3.6.1.4.1.2021.4.14";
        String regexPattern = "(?<=: )\\d+";
        int totalRAMbuffered = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return totalRAMbuffered;
    }

    public int getTotalCachedMemory () throws IOException { //1.3.6.1.4.1.2021.4.15.0
        String snmpCommand = "1.3.6.1.4.1.2021.4.15";
        String regexPattern = "(?<=: )\\d+";
        int totalCachedMemory = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return totalCachedMemory;
    }
}
