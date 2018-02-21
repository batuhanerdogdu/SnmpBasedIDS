import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public  String getIpAddress () throws  IOException {
        ArrayList<String> ipAddresses = new ArrayList<String>();
        String ipAddress = new String();
        String command = "ifconfig wlan1";
        String regexPattern = "(?<= inet addr:)\\S+";
        String[] args = new String[] {"/bin/bash", "-c", command};
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        Pattern pattern = Pattern.compile(regexPattern);
        String line;

        while ((line = r.readLine()) != null){
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()){
                ipAddresses.add((matcher.group().substring(0, matcher.group().length())).replace(' ', '.'));
            }
        }
        ipAddress = ipAddresses.get(0);
        return  ipAddress;
    }

    public String getMacAddress () throws IOException {
        String macAddress =  new String();
        String command = "ifconfig";
        String regexPattern = "(?<=HWaddr ).+\\S";
        String[] args = new String[] {"/bin/bash", "-c", command};
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        Pattern pattern = Pattern.compile(regexPattern); //"/([A-z])+\\w(?=\")/g"  \S+(?=\")(?<=\")
        String line;

        while ((line = r.readLine()) != null ) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                //System.out.println(matcher.group().substring(0,matcher.group().length()));
                macAddress = (matcher.group().substring(0, matcher.group().length())).replace(' ', '_');
            }
        }
        return macAddress;
    }
}
