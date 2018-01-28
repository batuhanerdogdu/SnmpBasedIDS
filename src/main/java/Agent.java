public class Agent {
    private String ipAddress = new String();

    public Agent (String ipAddress){
        ipAddress = this.ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void getInformation (){
        MemoryStatistics memoryStatistics = new MemoryStatistics(ipAddress);
        CPUstatistics cpUstatistics = new CPUstatistics(ipAddress);
        DiskStatistics diskStatistics = new DiskStatistics(ipAddress);
        ProcessStatistics processStatistics = new ProcessStatistics(ipAddress);
    }
}
