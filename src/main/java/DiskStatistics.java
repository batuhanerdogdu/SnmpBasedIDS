/**********************************************************************************
 * gets disk statistics for IDS
 for this to run snmpd.conf has to be edited. add or uncomment line the following to snmpd.conf file:
 disk / 100000
 all integer values are in kilobytes except percentages
 **********************************************************************************/
import java.io.IOException;
import java.util.HashMap;

public class DiskStatistics extends Statistics {
    private String ipAddress;
     public DiskStatistics(String ipAddress){
         this.ipAddress = ipAddress;
     }


    public HashMap getAllDiskStatistics () throws IOException{
        HashMap<String, String> allDiskStatistics= new HashMap<String,String >();
        String snmpCommand = "1.3.6.1.4.1.2021.9.1";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allDiskStatistics;
    }
    public String getPathWhereDiskIsMounted () throws IOException { //1.3.6.1.4.1.2021.9.1.2
        String pathWhereDiskIsMounted = new String ();
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.2";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return pathWhereDiskIsMounted;
    }
    public String getPathOfTheDeviceForThePartition () throws IOException { //1.3.6.1.4.1.2021.9.1.3
        String pathOfTheDeviceForThePartition = new String();
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.3";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return pathOfTheDeviceForThePartition;
    }
    public int getTotalSizeOfTheDiskOrPartition () throws IOException { //1.3.6.1.4.1.2021.9.1.6
        int totalSizeOfTheDiskOrPartition = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.6";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return totalSizeOfTheDiskOrPartition;
    }
    public int getAvailableSpaceOnTheDisk () throws IOException{ //1.3.6.1.4.1.2021.9.1.7
        int availableSpaceOnTheDisk = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.7";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return availableSpaceOnTheDisk;
    }
    public int getUsedSpaceOnTheDisk () throws IOException { //1.3.6.1.4.1.2021.9.1.8
        int usedSpaceOnTheDisk = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.8";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return usedSpaceOnTheDisk;
    }
    public int getPercentageOfSpaceUsedOnDisk () throws IOException { //1.3.6.1.4.1.2021.9.1.9
        int percentageOfSpaceUsedOnDisk = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.9";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return percentageOfSpaceUsedOnDisk;
    }
    public int getPercentageOfInodesOnDisk () throws IOException { //1.3.6.1.4.1.2021.9.1.10
        int percentageOfInodesOnDisk = 0;
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.10";
        String regexPattern = "((?<= STRING: ))+\\w.+(?=$)";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return percentageOfInodesOnDisk;
    }

}