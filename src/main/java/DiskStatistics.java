/**********************************************************************************
 * gets disk statistics for IDS
 for this to run snmpd.conf has to be edited. add or uncomment line the following to snmpd.conf file:
 disk / 100000
 all integer values are in kilobytes except percentages
 **********************************************************************************/
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DiskStatistics extends Statistics {

    private String ipAddress;
    public DiskStatistics(String ipAddress){
         this.ipAddress = ipAddress;
    }

    public HashMap getAllDiskStatistics () throws IOException{ // for all functions regex is = (?<=: ).+
        HashMap<String, String> allDiskStatistics= new HashMap<String,String >();
        ArrayList<String> allDStatistics = new ArrayList<String>();
        String snmpCommand = "1.3.6.1.4.1.2021.9.1";
        String regexPattern = "(?<=: ).+";
        runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return allDiskStatistics; //on progress
    }

    public ArrayList<String> getPathWhereDiskIsMounted () throws IOException { //1.3.6.1.4.1.2021.9.1.2
        ArrayList<String> pathWhereDiskIsMounted;
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.2";
        String regexPattern = "(?<=: ).+";
        pathWhereDiskIsMounted = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return pathWhereDiskIsMounted; //1st element gives the current operating system's path
    }

    public ArrayList<String> getPathOfTheDeviceForThePartition () throws IOException { //1.3.6.1.4.1.2021.9.1.3
        ArrayList<String> pathOfTheDeviceForThePartition;
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.3";
        String regexPattern = "(?<=: ).+";
        pathOfTheDeviceForThePartition = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        return pathOfTheDeviceForThePartition; //1st element gives the current os' path for the partition
    }

    public ArrayList<Integer> getTotalSizeOfTheDiskOrPartition () throws IOException { //1.3.6.1.4.1.2021.9.1.6
        ArrayList<Integer> totalSizeOfTheDiskOrPartition = new ArrayList<Integer>();
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.6";
        String regexPattern = "(?<=: ).+";
        ArrayList<String> temp = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        for (int i=0 ; i < temp.size() ; i++) {
            totalSizeOfTheDiskOrPartition.add(Integer.parseInt(temp.get(i)));
        }

        return totalSizeOfTheDiskOrPartition; //1st element is the current os' value
    }

    public ArrayList<Integer> getAvailableSpaceOnTheDisk () throws IOException{ //1.3.6.1.4.1.2021.9.1.7
        ArrayList<Integer> availableSpaceOnTheDisk =  new ArrayList<Integer>();
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.7";
        String regexPattern = "(?<=: ).+";
        ArrayList<String> temp = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        for (int i=0 ; i < temp.size() ; i++){
            availableSpaceOnTheDisk.add(Integer.parseInt(temp.get(i)));
        }
        return availableSpaceOnTheDisk; //1st element is the current os' value
    }

    public ArrayList<Integer> getUsedSpaceOnTheDisk () throws IOException { //1.3.6.1.4.1.2021.9.1.8
        ArrayList<Integer> usedSpaceOnTheDisk = new ArrayList<Integer>();
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.8";
        String regexPattern = "(?<=: ).+";
        ArrayList<String> temp = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        for (int i=0 ; i < temp.size() ; i++) {
            usedSpaceOnTheDisk.add(Integer.parseInt(temp.get(i)));
        }
        return usedSpaceOnTheDisk; //1st element is the current os' value
    }

    public ArrayList<Integer> getPercentageOfSpaceUsedOnDisk () throws IOException { //1.3.6.1.4.1.2021.9.1.9
        ArrayList<Integer> percentageOfSpaceUsedOnDisk =  new ArrayList<Integer>();
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.9";
        String regexPattern = "(?<=: ).+";
        ArrayList<String> temp = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        for (int i=0 ; i < temp.size() ; i++){
            percentageOfSpaceUsedOnDisk.add(Integer.parseInt(temp.get(i)));
        }
        return percentageOfSpaceUsedOnDisk; //1st element is the current os' value
    }

    public ArrayList<Integer> getPercentageOfInodesOnDisk () throws IOException { //1.3.6.1.4.1.2021.9.1.10
        ArrayList<Integer> percentageOfInodesOnDisk = new ArrayList<Integer>();
        String snmpCommand = "1.3.6.1.4.1.2021.9.1.10";
        String regexPattern = "(?<=: ).+";
        ArrayList <String> temp = runSnmpCommand(snmpCommand, regexPattern, ipAddress);
        for (int i=0 ; i <  temp.size(); i++){
            percentageOfInodesOnDisk.add(Integer.parseInt(temp.get(i)));
        }
        return percentageOfInodesOnDisk; //1st element is the current os' value
    }

}