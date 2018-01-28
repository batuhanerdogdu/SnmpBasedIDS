
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
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allCPUstatistics;
    }

    public HashMap<String ,String> getAllLoadInformation () throws IOException { //1.3.6.1.4.1.2021.10
        HashMap<String,String> allLoadInformation= new HashMap<String ,String>();
        String snmpCommand = "1.3.6.1.4.1.2021.10";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allLoadInformation;
    }
    public String get1MinuteLoad () throws IOException { //1.3.6.1.4.1.2021.10.1.3.1
        String oneMinuteLoad = new String();
        String snmpCommand = "1.3.6.1.4.1.2021.10.1.3.1";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return oneMinuteLoad;
    }
    public String get5MinuteLoad () throws IOException { //1.3.6.1.4.1.2021.10.1.3.2
        String fiveMinuteLoad = new String();
        String snmpCommand = "1.3.6.1.4.1.2021.10.1.3.2";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return fiveMinuteLoad;
    }
    public String get15MinuteLoad () throws IOException { //1.3.6.1.4.1.2021.10.1.3.3
        String fifteenMinuteLoad = new String();
        String snmpCommand = "1.3.6.1.4.1.2021.10.1.3.3";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return fifteenMinuteLoad;
    }
    public int getPercentageOfUserCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.9.0
        int percentageOfUserCPUtime = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.11.9";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return percentageOfUserCPUtime;
    }
    public int getRawUserCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.50.0
        int rawUserCPUtime = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.11.50";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return rawUserCPUtime;
    }
    public int getPercentagesOfSystemCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.10.0
        int percentagesOfSystemCPUtime = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.11.10";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return percentagesOfSystemCPUtime;
    }
    public int getRawSystemCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.52.0
        int rawSystemCPUtime = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.11.52";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return rawSystemCPUtime;
    }
    public int getPercentagesOfIdleCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.11.0
        int percentagesOfIdleCPUtime = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.11.11";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return percentagesOfIdleCPUtime;
    }
    public int getRawIdleCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.53.0
        int rawIdleCPUtime = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.11.53";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return rawIdleCPUtime;
    }
    public int getRawNiceCPUtime () throws IOException { //1.3.6.1.4.1.2021.11.51.0
        int rawNiceCPUtime = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.11.51";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return rawNiceCPUtime;
    }

}
