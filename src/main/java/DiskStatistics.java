public class DiskStatistics {
    /**********************************************************************************
     * gets disk statistics for IDS
     for this to run snmpd.conf has to be edited. add or uncomment line the following to snmpd.conf file:
     disk / 100000
     all integer values are in kilobytes except percentages
     **********************************************************************************/
    public String getPathWhereDiskIsMounted () { //1.3.6.1.4.1.2021.9.1.2.1
        String pathWhereDiskIsMounted = new String ();
        return pathWhereDiskIsMounted;
    }
    public String getPathOfTheDeviceForThePartition () { //1.3.6.1.4.1.2021.9.1.3.1
        String pathOfTheDeviceForThePartition = new String();
        return pathOfTheDeviceForThePartition;
    }
    public int getTotalSizeOfTheDiskOrPartition () { //1.3.6.1.4.1.2021.9.1.6.1
        int totalSizeOfTheDiskOrPartition = 0;
        return totalSizeOfTheDiskOrPartition;
    }
    public int getAvailableSpaceOnTheDisk () { //1.3.6.1.4.1.2021.9.1.7.1
        int availableSpaceOnTheDisk = 0;
        return availableSpaceOnTheDisk;
    }
    public int getUsedSpaceOnTheDisk () { //1.3.6.1.4.1.2021.9.1.8.1
        int usedSpaceOnTheDisk = 0;
        return usedSpaceOnTheDisk;
    }
    public int getPercentageOfSpaceUsedOnDisk () { //1.3.6.1.4.1.2021.9.1.9.1
        int percentageOfSpaceUsedOnDisk = 0;
        return percentageOfSpaceUsedOnDisk;
    }
    public int getPercentageOfInodesOnDisk () { //1.3.6.1.4.1.2021.9.1.10.1
        int percentageOfInodesOnDisk = 0;
        return percentageOfInodesOnDisk;
    }

}