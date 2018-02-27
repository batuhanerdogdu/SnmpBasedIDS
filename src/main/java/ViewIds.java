import com.sun.xml.internal.bind.v2.TODO;
import org.apache.jena.riot.lang.SinkTriplesToGraph;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private JLabel lbConnections;
    private JTextArea txaProcess;
    private JTextArea txaMalware;
    private JTextArea txaDetected;
    private JTextArea txaCpu;
    private JTextArea txaMemory;
    private JTextArea txaDisk;
    private JTextArea txaConnections;
    private JScrollPane scpProcess;
    private JScrollPane scpMalware;
    private JScrollPane scpDetected;
    private JScrollPane scpCpu;
    private JScrollPane scpMemory;
    private JScrollPane scpDisk;
    private JScrollPane scpConnections;
    private JButton btnStop;
    private JButton btnStart;
    private final Border border =
            BorderFactory.createLoweredBevelBorder();
    private JPanel panel;
    OntologyConnection connection;
    SystemInformation systemInformation;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");




    /**TO DO
     * define ontologyConnection at first
     * send every process to swing builder
     * get ips and analyze
     * somehow enable stop capturing
     * */


    public ViewIds (JFrame frame, ArrayList<String> ipAddresses) throws IOException {
        //get ip addresses
        //create a dropdown menu
        //for each ip address, reload the page and show info of that ip address
        //for now only ip is 127.0.0.1
        connection = new OntologyConnection();
        String[] args = new String[] {"/bin/bash", "-c", connection.getWorkingDirectory() +"./fuseki.sh"};
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Process[] proc = {new ProcessBuilder(args).start()};
        systemInformation = new SystemInformation("127.0.0.1");


        //this is for NMS, however if you change the ip address the results are changed to corresponding ip address
        panel = new JPanel(new GridBagLayout());
        final GridBagConstraints cs = new GridBagConstraints();

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

        String ips = new String();
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
        scpProcess.setBorder(border);
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
        scpMalware.setBorder(border);
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
        scpDetected.setBorder(border);
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
        scpCpu.setBorder(border);
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
        scpMemory.setBorder(border);
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

        lbConnections = new JLabel("Connections");
        cs.gridx = 5;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lbConnections, cs);

        txaConnections = new JTextArea(20,30);
        txaConnections.setEditable(false);
        txaConnections.setBackground(Color.lightGray);
        scpConnections = new JScrollPane(txaConnections);
        scpConnections.setBorder(border);
        cs.gridx = 5;
        cs.gridy = 6;
        cs.gridwidth = 1;
        panel.add(scpConnections, cs);

        btnStop = new JButton("Stop capturing");
        cs.gridx = 5;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(btnStop, cs);

        btnStart = new JButton("Start capturing");
        cs.gridx = 5;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(btnStart, cs);

        JProgressBar jpbDisk = new JProgressBar();
        panel.add(jpbDisk);

        frame.add(panel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //txaConnections

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setBorder(new LineBorder(Color.GRAY));
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setResizable(true);
        //setLocationRelativeTo(parent);

        final DiskWorker diskWorker = new DiskWorker();
        diskWorker.execute();
        final MemWorker memWorker = new MemWorker();
        memWorker.execute();
        final ProcessWorker processWorker = new ProcessWorker();
        processWorker.execute();
        final CpuWorker cpuWorker = new CpuWorker();
        cpuWorker.execute();
        final DetectionWorker detectionWorker = new DetectionWorker();
        detectionWorker.execute();
        final MalwareWorker malwareWorker = new MalwareWorker();
        malwareWorker.execute();
        final TcpDumpWorker tcpDumpWorker = new TcpDumpWorker();
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tcpDumpWorker.execute();
            }
        });
        btnStart.updateUI();


        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //tcpDumpWorker.cancel();
                tcpDumpWorker.cancel(true);
            }
        });
        btnStop.updateUI();
    }

    private class ProcessWorker extends SwingWorker <String, String> {

        @Override
        protected String doInBackground () throws IOException {
            ArrayList<String> processNames = new ArrayList<String>();
            ArrayList<String> processIds = new ArrayList<String>();
            ProcessStatistics processStatistics = new ProcessStatistics("127.0.0.1");
            ArrayList<ProcessStatistics.Process> processes = processStatistics.getProcessNames();
            for (ProcessStatistics.Process process: processes){
                processIds.add(process.getId());
            }
            connection.insertIndividuals(processIds, "Process");
            for (ProcessStatistics.Process process : processes) {
                publish("id: " + process.getId() + " name: " + process.getName() + "\n");
                processNames.add(process.getName());
                ArrayList<String> name = new ArrayList<String>();
                name.add(process.getName());
                connection.insertDataProperties(process.getId(), "processName", name);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void process (List<String> chunks) {
            for (String s: chunks)
                txaProcess.append(s);
        }
        @Override
        protected  void done () {}
    }

    private class MemWorker extends SwingWorker<String, String> {
        @Override
        protected String doInBackground () throws IOException {
            MemoryStatistics memoryStatistics = new MemoryStatistics("127.0.0.1");
            publish("Total RAM: " + memoryStatistics.getTotalRAMinMachine() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Available swap space: " + memoryStatistics.getAvailableSwapSpace() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Total cached memory: " + memoryStatistics.getTotalCachedMemory() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Total RAM buffered: " + memoryStatistics.getTotalRAMBuffered() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Total RAM free: " + memoryStatistics.getTotalRAMfree() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Total RAM shared: " + memoryStatistics.getTotalRAMshared() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Total RAM used: " + memoryStatistics.getTotalRAMused() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;

        }
        @Override
        protected void process (List<String> chunks) {

            for (String s: chunks)
                txaMemory.append(s);
        }
        @Override
        protected  void done () {}
    }

    private class DiskWorker extends SwingWorker<String, String> {
        @Override
        protected String doInBackground () throws IOException {
            DiskStatistics diskStatistics = new DiskStatistics("127.0.0.1");
            String temp1 = diskStatistics.getPathWhereDiskIsMounted().get(0);
            publish("Total disk size: " + temp1 + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String temp2 = diskStatistics.getPathWhereDiskIsMounted().get(0);
            publish("Path where the disk is mounted: " + temp2 + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String temp3  = diskStatistics.getPathOfTheDeviceForThePartition().get(0);
            publish("Path of the device for the partition: " + temp3 + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String temp4 = diskStatistics.getUsedSpaceOnTheDisk().get(0).toString();
            publish("Used space on the disk: " + temp4 + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String temp5 = diskStatistics.getPercentagesOfSpaceUsedOnDisk().get(0).toString();
            publish("Percentage of used space on the disk: " + temp5 + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String temp6 = diskStatistics.getPercentageOfInodesOnDisk().get(0).toString();
            publish("Percentage of inodes on the disk: " + temp6 + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void process (List<String> chunks) {
            for (String s : chunks) {
                txaDisk.append(s);
            }
        }
        @Override
        protected void done () {
        }
    }

    private class DetectionWorker extends  SwingWorker<String, String> {

        @Override
        protected String doInBackground() {

            String matchQuery = OntologyConnection.prefixRDF + "\n"+
                    OntologyConnection.prefixSNMP + "\n" +
                    "SELECT ?x \n WHERE {?x " + OntologyConnection.rdfType + " snmp:Malware." +
                    "?y "+ OntologyConnection.rdfType + " snmp:Process." +
                    "?z snmp:processName ?y." +
                    "FILTER (?x = ?z)}";
            ArrayList<String> results = connection.execSelectAndProcess(matchQuery);
            ArrayList<String> alerts = new ArrayList<String>();
            Date date = new Date();
            String s = sdf.format(date).toString();
            alerts.add(s);
            connection.insertIndividuals(alerts, "Alert");
            try {
                connection.insertObjectProperties("Alert", "isAbout", systemInformation.getIpAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(String result: results)
                publish(result);
            if (results.isEmpty() || results.get(0).equals(null)  || results.get(0).equals(" "))
                publish("No intrusions detected on processes.");
            return null;
        }
        @Override
        protected void process (List<String> chunks) {
            for (String s: chunks)
                txaDetected.append(s);
        }
    }

    private class MalwareWorker extends SwingWorker<String, String> {

        HTMLparser htmLparser = new HTMLparser();

        private MalwareWorker() throws IOException {
        }

        @Override
        protected String doInBackground () throws IOException {
            ArrayList<Malware> malwares = htmLparser.getHMTLcontentOfSymantecCom();

            for (Malware m : malwares) {
                publish("name: " + m.getName() + "\n");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
        @Override
        protected void process (List<String> chunks) {
            for (String s: chunks)
                txaMalware.append(s);
        }


    }

    private class CpuWorker extends SwingWorker<String, String> {

        @Override
        protected String doInBackground () throws IOException {
            CPUstatistics cpuStatistics = new CPUstatistics("127.0.0.1");
            publish("Percentage of system CPU: " + cpuStatistics.getPercentageOfSystemCPUtime() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Percentage of idle CPU: "  + cpuStatistics.getPercentageOfIdleCPUtime() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Raw System CPU:" + cpuStatistics.getRawSystemCPUtime() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Raw idle CPU: " + cpuStatistics.getRawIdleCPUtime() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("Raw user CPU: " + cpuStatistics.getRawUserCPUtime());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("1 minute load: " + cpuStatistics.get1MinuteLoad() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("5 minute load: " + cpuStatistics.get5MinuteLoad() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publish("15 Minute load: " + cpuStatistics.get15MinuteLoad() + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            for (String s: chunks)
                txaCpu.append(s);
        }
    }

    private class TcpDumpWorker extends SwingWorker<ArrayList<String>, String> {

        ArrayList<String> badips = connection.execSelectAndProcess(OntologyConnection.prefixSNMP +
                "\n " + OntologyConnection.prefixRDF + "\n" + "SELECT ?x WHERE {?x rdf:type snmp:BadInternetDomain.}");

        @Override
        protected ArrayList<String> doInBackground() throws IOException, InterruptedException {
            String[] args1 = new String[] {"/bin/bash", "-c", "tcpdump -i wlan1" +" -n"};// -w" + getWorkingDirectory() + "/file.cap"};
            Process proc = new ProcessBuilder(args1).start();
            ArrayList<String> ips = new ArrayList<String>();
            BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String regexPattern = "\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b";
            Pattern pattern = Pattern.compile(regexPattern); //"/([A-z])+\\w(?=\")/g"  \S+(?=\")(?<=\")
            String line;
            System.out.println("Parsing packets...");

            while ((line = r.readLine()) != null ) {
                Matcher matcher = pattern.matcher(line);
                //System.out.println("Control:" + line);
                while (matcher.find()) {
                    //System.out.println(matcher.group().substring(0,matcher.group().length()));
                    String temp = (matcher.group().substring(0, matcher.group().length()));
                    publish("ip: " + temp + "\n");
                    Thread.sleep(10);
                    //ips.add(temp);
                }
            }
            return ips;
        }

        @Override
        protected void process (List<String> chunks) {

            for (String s : chunks) {
                txaConnections.append(s);
                if (!s.startsWith("192.168.1")){
                    for (String s1 : badips){
                        if(s.equals(s1)){
                            try {
                                JOptionPane.showMessageDialog(panel, "Detected bad ip connection in " + systemInformation.getIpAddress() + " : " + s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        @Override
        protected void done() {
        }

    }


    public static void main (String[] args) throws IOException {
        JFrame frame = new JFrame("IDS");
        ArrayList<String> ips = new ArrayList<String>();
        ips.add("192.168.1.21");
        ViewIds viewIds = new ViewIds(frame, ips);
        viewIds.setVisible(true);
    }

}
