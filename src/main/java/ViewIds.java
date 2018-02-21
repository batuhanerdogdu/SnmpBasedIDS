import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewIds extends JFrame{

    private JLabel lbUser;
    private JLabel lbSysName;
    private JLabel lbSysUpTime;
    private JLabel lbSysIp;
    private JLabel lbIpAddresses;
    private JLabel lbProcess;
    private JLabel lbMalware;
    private JLabel lbDetected;
    private JLabel lbCpu;
    private JLabel lbMemory;
    private JLabel lbDisk;
    private JTextArea txaProcess;
    private JTextArea txaMalware;
    private JTextArea txaDetected;
    private JTextArea txaCpu;
    private JTextArea txaMemory;
    private JTextArea txaDisk;
    private JScrollPane scpProcess;
    private JScrollPane scpMalware;
    private JScrollPane scpDetected;
    private JScrollPane scpCpu;
    private JScrollPane scpMemory;
    private JScrollPane scpDisk;

    public ViewIds (ArrayList<String> ipAddresses) throws IOException {
        //get ip addresses
        //create a dropdown menu
        //for each ip address, reload the page and show info of that ip address
        //for now only ip is 127.0.0.1
        String[] args = new String[] {"/bin/bash", "-c", "./fuseki.sh"};
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Process proc = new ProcessBuilder(args).start();
        SystemInformation systemInformation = new SystemInformation("127.0.0.1");
        ProcessStatistics processStatistics = new ProcessStatistics("127.0.0.1");
        MemoryStatistics memoryStatistics = new MemoryStatistics("127.0.0.1");
        DiskStatistics diskStatistics = new DiskStatistics("127.0.0.1");
        CPUstatistics cpuStatistics = new CPUstatistics("127.0.0.1");
        HTMLparser htmLparser = new HTMLparser();


        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints cs = new GridBagConstraints();

        //ViewInit viewInit= new ViewInit();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUser = new JLabel("User: root");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUser, cs);

        lbSysName = new JLabel("System Name: " + systemInformation.getSystemName());
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbSysName, cs);

        lbSysUpTime = new JLabel("System is running for: " + systemInformation.getSystemUpTime());
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbSysUpTime, cs);

        lbSysIp = new JLabel("System IP: " + systemInformation.getIpAddress());
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbSysIp, cs);

        String ips = null;
        for (String s : ipAddresses)
            ips += s + " ";
        System.out.println(ips);
        lbIpAddresses = new JLabel("Ip Addresses: " + ips);
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbIpAddresses, cs);

        lbProcess = new JLabel("Processes");
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbProcess, cs);

        txaProcess = new JTextArea(20,10);
        txaProcess.setEditable(false);
        txaProcess.setBackground(Color.white);
        scpProcess = new JScrollPane(txaProcess);
        cs.gridx = 0;
        cs.gridy = 6;
        cs.gridwidth = 1;
        panel.add(scpProcess, cs);

        lbMalware = new JLabel("Malwares");
        cs.gridx = 1;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbMalware, cs);

        txaMalware = new JTextArea(20,30);
        txaMalware.setEditable(false);
        txaMalware.setBackground(Color.gray);
        scpMalware = new  JScrollPane(txaMalware);
        cs.gridx = 1;
        cs.gridy = 6;
        cs.gridwidth = 2;
        panel.add(scpMalware, cs);

        lbDetected = new JLabel("Detected Malwares");
        cs.gridx = 3;
        cs.gridy = 5;
        cs.gridwidth = 2;
        panel.add(lbDetected, cs);

        txaDetected = new JTextArea(20,30);
        txaDetected.setEditable(false);
        txaDetected.setBackground(Color.red);
        scpDetected = new JScrollPane(txaDetected);
        cs.gridx = 3;
        cs.gridy = 6;
        cs.gridwidth = 2;
        panel.add(scpDetected, cs);

        lbCpu = new JLabel("CPU information");
        cs.gridx = 0;
        cs.gridy = 8;
        cs.gridwidth = 1;
        panel.add(lbCpu, cs);

        txaCpu  = new JTextArea(20,10);
        txaCpu.setEditable(false);
        txaCpu.setBackground(Color.gray);
        scpCpu = new JScrollPane(txaCpu);
        cs.gridx = 0;
        cs.gridy = 9;
        cs.gridwidth = 1;
        panel.add(scpCpu, cs);

        lbMemory = new JLabel("Memory information");
        cs.gridx = 1;
        cs.gridy = 8;
        cs.gridwidth = 2;
        panel.add(lbMemory, cs);

        txaMemory = new JTextArea(20,30);
        txaMemory.setEditable(false);
        txaMemory.setBackground(Color.white);
        scpMemory = new JScrollPane(txaMemory);
        cs.gridx = 1;
        cs.gridy = 9;
        cs.gridwidth = 2;
        panel.add(scpMemory, cs);

        lbDisk = new JLabel("Disk information");
        cs.gridx = 3;
        cs.gridy = 8;
        cs.gridwidth = 2;
        panel.add(lbDisk, cs);

        txaDisk = new JTextArea(20,30);
        txaDisk.setEditable(false);
        txaDisk.setBackground(Color.gray);
        scpDisk = new JScrollPane(txaDisk);
        cs.gridx = 3;
        cs.gridy = 9;
        cs.gridwidth = 2;
        panel.add(scpDisk, cs);

        OntologyConnection connection = new OntologyConnection();
        connection.uploadRDF(new File(connection.getWorkingDirectory()+"snmpids.owl"),
                connection.serviceURIforData);
        ArrayList<String> processNames = new ArrayList<String>();
        ArrayList<String> processIds = new ArrayList<String>();
        ArrayList<ProcessStatistics.Process> processes = processStatistics.getProcessNames();

        for (ProcessStatistics.Process process: processes){
            processIds.add(process.getId());
        }
        connection.insertIndividuals(processIds, "Process");

        for (ProcessStatistics.Process process : processes) {
            txaProcess.append("id: " + process.getId() + " name: " + process.getName() + "\n");
            processNames.add(process.getName());
            ArrayList<String> name = new ArrayList<String>();
            name.add(process.getName());
            connection.insertDataProperties(process.getId(), "processName", name);
        }

        //add malwares to txaMalwares
        ArrayList<Malware> malwares = htmLparser.getHMTLcontentOfSymantecCom();
        for (Malware m : malwares) {
            txaMalware.append("name: " + m.getName() + "\n");
        }
        //add detected to txaDetected
        String matchQuery = "SELECT ?x \n WHERE {?x " + OntologyConnection.rdfType +
                OntologyConnection.nameSpace+"Malware." +
                "?y "+ OntologyConnection.rdfType + " " +
                OntologyConnection.nameSpace + "Process." +
                "?z +"+ OntologyConnection.nameSpace + "processName ?y." +
                "FILTER (?x = ?z)}";
        connection.execSelectAndProcess(matchQuery);

        txaCpu.append("Percentage of system CPU: " + cpuStatistics.getPercentageOfSystemCPUtime() + "\n");
        txaCpu.append("Percentage of idle CPU: "  + cpuStatistics.getPercentageOfIdleCPUtime() + "\n");
        txaCpu.append("Raw System CPU:" + cpuStatistics.getRawSystemCPUtime() + "\n");
        txaCpu.append("Raw idle CPU: " + cpuStatistics.getRawIdleCPUtime() + "\n");
        txaCpu.append("Raw user CPU: " + cpuStatistics.getRawUserCPUtime());
        txaCpu.append("1 minute load: " + cpuStatistics.get1MinuteLoad() + "\n");
        txaCpu.append("5 minute load: " + cpuStatistics.get5MinuteLoad() + "\n");
        txaCpu.append("15 Minute load: " + cpuStatistics.get15MinuteLoad() + "\n");

        txaMemory.append("Total RAM: " + memoryStatistics.getTotalRAMinMachine() + "\n");
        txaMemory.append("Available swap space: " + memoryStatistics.getAvailableSwapSpace() + "\n");
        txaMemory.append("Total cached memory: " + memoryStatistics.getTotalCachedMemory() + "\n");
        txaMemory.append("Total RAM buffered: " + memoryStatistics.getTotalRAMBuffered() + "\n");
        txaMemory.append("Total RAM free: " + memoryStatistics.getTotalRAMfree() + "\n");
        txaMemory.append("Total RAM shared: " + memoryStatistics.getTotalRAMshared() + "\n");
        txaMemory.append("Total RAM used: " + memoryStatistics.getTotalRAMused() + "\n");

        txaDisk.append("Total disk size: " + diskStatistics.getTotalSizeOfTheDiskOrPartition().get(0) + "\n");
        txaDisk.append("Path where the disk is mounted: " + diskStatistics.getPathWhereDiskIsMounted().get(0) + "\n");
        txaDisk.append("Path of the device for the partition: " + diskStatistics.getPathOfTheDeviceForThePartition().get(0) + "\n");
        txaDisk.append("Used space on the disk: " + diskStatistics.getUsedSpaceOnTheDisk().get(0) + "\n");
        txaDisk.append("Percentage of used space on the disk: " + diskStatistics.getPercentagesOfSpaceUsedOnDisk().get(0) + "\n");
        txaDisk.append("Percentage of inodes on the disk: " + diskStatistics.getPercentageOfInodesOnDisk().get(0));

        panel.setBorder(new LineBorder(Color.GRAY));
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setResizable(true);
        //setLocationRelativeTo(parent);
    }

   /* public static void main (String[] args) throws IOException {
        ViewIds viewIds = new ViewIds();
        viewIds.setVisible(true);
    }*/

}
