import java.io.IOException;
import java.util.ArrayList;

public class SystemInformation extends Statistics {

    private String ipAddress;
    public SystemInformation (String ipAddress){
        this.ipAddress = ipAddress;
    }

    public String getSystemDescription () throws IOException {
        String snmpCommand = "1.3.6.1.2.1.1.1";
        String regexPattern = "(?<=: ).+";
        String systemDescription = runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0);
        return systemDescription;
    }

    public String getSystemUpTime () throws IOException {
        String snmpCommand = "1.3.6.1.2.1.1.3";
        String regexPattern = "(?<=: ).+";
        String systemUpTime= runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0);
        return systemUpTime;
    }

    public String getSystemName () throws IOException {
        String snmpCommand = "1.3.6.1.2.1.1.5";
        String regexPattern = "(?<=: ).+";
        String systemName = runSnmpCommand(snmpCommand, regexPattern, ipAddress).get(0);
        return systemName;
    }
}
