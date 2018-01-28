import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Statistics {

    private String snmpCommand = new String();

    public void setSnmpCommand(String snmpCommand) {
        this.snmpCommand = snmpCommand;
    }


    public ArrayList<String> runSnmpCommand (String snmpCommand, String regexPattern, String ipAddress) throws IOException {
        ArrayList<String> results = new ArrayList<String>();
        String[] args = new String[] {"/bin/bash", "-c", "snmpwalk "+ ipAddress +" "+snmpCommand}; //1.3.6.1.2.1.25.4.2.1.2
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        Pattern pattern = Pattern.compile(regexPattern); //"/([A-z])+\\w(?=\")/g"  \S+(?=\")(?<=\")
        String line;

        while ((line = r.readLine()) != null ) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                //System.out.println(matcher.group().substring(0,matcher.group().length()));
                results.add((matcher.group().substring(0, matcher.group().length())));
            }
        }
        return results;
    }
}