import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TcpDump {
    public Process proc;
    public String nifName;
    public boolean stop;

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Process getProc() {
        return proc;
    }

    public void setProc(Process proc) {
        this.proc = proc;
    }

    public String getNifName() {
        return nifName;
    }

    public void setNifName(String nifName) {
        this.nifName = nifName;
    }

    public TcpDump() throws IOException {

    }


    public ArrayList<String> getPackets () throws IOException {
        ArrayList<String> ips = new ArrayList<String>();
        String[] args = new String[] {"/bin/bash", "-c", "tcpdump -i "+ nifName +" -n"};// -w" + getWorkingDirectory() + "/file.cap"};
        proc = new ProcessBuilder(args).start();
        setStop(false);
        setProc(proc);
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String regexPattern = "((?<=IP )\\S+(?= >))|(?<=> )\\d.+(?=:)";
        Pattern pattern = Pattern.compile(regexPattern); //"/([A-z])+\\w(?=\")/g"  \S+(?=\")(?<=\")
        String line;
        System.out.println("Parsing...");

        while ((line = r.readLine()) != null ) {
            Matcher matcher = pattern.matcher(line);
            System.out.println("Control:" + line);
            while (matcher.find()) {
                while (!stop) {
                    System.out.println(matcher.group().substring(0,matcher.group().length()));
                    ips.add((matcher.group().substring(0, matcher.group().length())));
                }
            }
        }
        int i = 0;
        for (String ip : ips){
            if (isEven(i))
                System.out.println(i + "th" + " source ip: " + ip);
            else
                System.out.println(i + "th" + "dest ip: " + ip);
            i++;
        }
        return ips;
    }

    public static boolean isEven (int i) {
        boolean flag;
        if (i%2 == 1)
            flag = false;
        else
            flag = true;
        return flag;
    }

    public void stopTcpDump (Process proc) {
        System.out.println("Destroying tcpdump process...");
        proc.destroy();
    }

    public static String getWorkingDirectory () throws IOException {
        String[] args = new String[] {"/bin/bash", "-c", "pwd"};
        Process proc = new ProcessBuilder(args).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line;
        String result = null;

        while ((line = r.readLine()) != null ) {
            result = line;
            //System.out.println(line);
        }
        return result;
    }

    public static void main (String[] args) throws IOException {
        TcpDump tcpDump =  new TcpDump();
        tcpDump.setNifName("wlan1");
        ArrayList<String> ips = tcpDump.getPackets();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tcpDump.stopTcpDump(tcpDump.getProc());
    }
}
