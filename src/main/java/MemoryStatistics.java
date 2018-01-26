import java.util.ArrayList;
import java.util.HashMap;

public class MemoryStatistics {
    /*****************************************************************
     * get memory statistics for IDS
     all values are in kilobytes
    ******************************************************************/

    public HashMap<String,String> getAllMemoryStatistics () { //1.3.6.1.4.1.2021.4
        HashMap<String, String> allMemoryStatistics = new HashMap<String, String>();
        return allMemoryStatistics;
    }

    public int getTotalSwapSize () { //1.3.6.1.4.1.2021.4.3.0
        int totalSwapSize = 0;
        return totalSwapSize;
    }
    public int getAvailableSwapSpace () { //1.3.6.1.4.1.2021.4.4.0
        int availableSwapSpace = 0;
        return availableSwapSpace;
    }
    public int getTotalRAMinMachine () { //1.3.6.1.4.1.2021.4.5.0
        int totalRAMinMachine = 0;
        return totalRAMinMachine;
    }
    public int getTotalRAMused () { //1.3.6.1.4.1.2021.4.6.0
        int totalRAMused = 0;
        return totalRAMused;
    }
    public int getTotalRAMfree () { //1.3.6.1.4.1.2021.4.11.0
        int totalRAMfree = 0;
        return  totalRAMfree;
    }
    public int getTotalRAMshared () { //1.3.6.1.4.1.2021.4.13.0
        int totalRAMshared = 0;
        return totalRAMshared;
    }
    public int getTotalRAMBuffered () { //1.3.6.1.4.1.2021.4.14.0
        int totalRAMbuffered = 0;
        return totalRAMbuffered;
    }
    public int getTotalCachedMemory () { //1.3.6.1.4.1.2021.4.15.0
        int totalCachedMemory = 0;
        return totalCachedMemory;
    }
}
