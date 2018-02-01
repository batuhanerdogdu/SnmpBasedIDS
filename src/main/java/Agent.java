import org.apache.jena.base.Sys;

import java.io.IOException;
import java.util.ArrayList;

public class Agent {
    private String ipAddress = new String();

    public Agent (String ipAddress){
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }
    public static void main (String[] args) throws IOException {
        Agent agent = new Agent("127.0.0.1");
        agent.getInformation();
    }

    public void getInformation () throws IOException {
        SystemInformation systemInformation = new SystemInformation(ipAddress);
        MemoryStatistics memoryStatistics = new MemoryStatistics(ipAddress);
        CPUstatistics cpUstatistics = new CPUstatistics(ipAddress);
        DiskStatistics diskStatistics = new DiskStatistics(ipAddress);
        ProcessStatistics processStatistics = new ProcessStatistics(ipAddress);
        String name = systemInformation.getSystemName();
        System.out.println("System description: " + name);
        System.out.println("System name: " + systemInformation.getSystemDescription());
        System.out.println("System up time: " + systemInformation.getSystemUpTime());
        System.out.println("Memory Statistics: "+ memoryStatistics.getAllMemoryStatistics());
        System.out.println("CPU Statistics" + cpUstatistics.get1MinuteLoad());


    }
}
