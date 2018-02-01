
/***********************************************************
 gets CPU statistics for IDS
 */

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import java.io.IOException;
import java.util.HashMap;

public class CPUstatistics extends Statistics {

    private String ipAddress;

    public CPUstatistics(String ipAddress){
        this.ipAddress = ipAddress;
    }

    public HashMap<String, String> getAllCPUstatistics () throws IOException { // 1.3.6.1.4.1.2021.11
        HashMap<String,String > allCPUstatistics = new HashMap<String, String>();
        String snmpCommand = "1.3.6.1.4.1.2021.11";
        String regexPattern = "(?<=: ).+";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allCPUstatistics; //on progress
    }

    public HashMap<String ,String> getAllLoadInformation () throws IOException { //1.3.6.1.4.1.2021.10
        HashMap<String,String> allLoadInformation= new HashMap<String ,String>();
        String snmpCommand = "1.3.6.1.4.1.2021.10";
        String regexPattern = "(?<=: ).+";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allLoadInformation; //on progress
    }

    public String get1MinuteLoad () throws IOException { //1.3.6.1.4.1.2021.10.1.3.1
        String snmpCommand = "1.3.6.1.4.1.2021.10.1.3.1";
        String regexPattern = "(?<=: ).+";
        String oneMinuteLoad = runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0);
        return oneMinuteLoad;
    }

    public String get5MinuteLoad () throws IOException { //1.3.6.1.4.1.2021.10.1.3.2
        String snmpCommand = "1.3.6.1.4.1.2021.10.1.3.2";
        String regexPattern = "(?<=: ).+";
        String fiveMinuteLoad= runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0);
        return fiveMinuteLoad;
    }

    public String get15MinuteLoad () throws IOException { //1.3.6.1.4.1.2021.10.1.3.3
        String snmpCommand = "1.3.6.1.4.1.2021.10.1.3.3";
        String regexPattern = "(?<=: ).+";
        String fifteenMinuteLoad = runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0);
        return fifteenMinuteLoad;
    }

    public int getPercentageOfUserCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.9.0
        String snmpCommand = "1.3.6.1.4.1.2021.11.9";
        String regexPattern = "(?<=: ).+";
        int percentageOfUserCPUtime = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return percentageOfUserCPUtime;
    }

    public int getRawUserCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.50.0
        String snmpCommand = "1.3.6.1.4.1.2021.11.50";
        String regexPattern = "(?<=: ).+";
        int rawUserCPUtime = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return rawUserCPUtime;
    }

    public int getPercentagesOfSystemCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.10.0
        String snmpCommand = "1.3.6.1.4.1.2021.11.10";
        String regexPattern = "(?<=: ).+";
        int percentagesOfSystemCPUtime = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return percentagesOfSystemCPUtime;
    }

    public int getRawSystemCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.52.0
        String snmpCommand = "1.3.6.1.4.1.2021.11.52";
        String regexPattern = "(?<=: ).+";
        int rawSystemCPUtime = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return rawSystemCPUtime;
    }

    public int getPercentagesOfIdleCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.11.0
        String snmpCommand = "1.3.6.1.4.1.2021.11.11";
        String regexPattern = "(?<=: ).+";
        int percentagesOfIdleCPUtime = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return percentagesOfIdleCPUtime;
    }

    public int getRawIdleCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.53.0
        String snmpCommand = "1.3.6.1.4.1.2021.11.53";
        String regexPattern = "(?<=: ).+";
        int rawIdleCPUtime = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return rawIdleCPUtime;
    }

    public int getRawNiceCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.51.0
        String snmpCommand = "1.3.6.1.4.1.2021.11.51";
        String regexPattern = "(?<=: ).+";
        int rawNiceCPUtime = Integer.parseInt(runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0));
        return rawNiceCPUtime;
    }
}
