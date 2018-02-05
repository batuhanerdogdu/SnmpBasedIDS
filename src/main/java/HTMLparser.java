import org.apache.jena.base.Sys;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLparser  {

    public static void main (String args[]) throws IOException, InterruptedException {
        HTMLparser htmlParser = new HTMLparser();
        //htmlParser.getHTMLcontentOfMalwareDomainListCom();
        //htmlParser.getHTMLcontentOfMcAfeeCom();
    }

    //url is the html file's url, part is the required html part (e.g <br>)
    public ArrayList<BadIP> getHTMLcontentOfMalwareDomainListCom() throws IOException {
        String url = "https://www.malwaredomainlist.com/mdl.php";
        ArrayList<BadIP> badIPs = new ArrayList<BadIP>();
        Document document = Jsoup.connect(url).timeout(10*10000).get();
        Elements elements = document.getElementsByTag("tbody");
        Elements rows = elements.select("tr");
        //System.out.println(rows);
        try {//website responses late so we make the program wait...
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rows.remove(0);
        rows.remove(1); //first two rows are not necessary

        for (Element row : rows){

            Elements columns = row.getElementsByTag("td");
            ArrayList<String> entries = new ArrayList<String>();
            for (Element column : columns){
                if(!(column.text().equals(null)) && !(column.text().equals("⇑ ⇓"))) {
                    entries.add(column.text());
                }
            }
            if (entries.size() > 1){
                BadIP badIP = new BadIP();

                if (!entries.get(0).equals(null)) badIP.setDateOfDiscovery(entries.get(0));
                else badIP.setDateOfDiscovery("Unknown");
                if (!entries.get(1).equals(null)) badIP.setDomainName(entries.get(1));
                else badIP.setDomainName("Unknown");
                if (!entries.get(2).equals(null)) badIP.setIpAddress(entries.get(2));
                else badIP.setIpAddress("Unknown");
                if (!entries.get(3).equals(null)) badIP.setReverseLookupAddress(entries.get(3));
                else badIP.setReverseLookupAddress("Unknown");
                if (!entries.get(4).equals(null)) badIP.setDescription(entries.get(4));
                else badIP.setDescription("Unknown");
                if (!entries.get(5).equals(null)) badIP.setRegistrant(entries.get(5));
                else badIP.setRegistrant("Unknown");
                if (!entries.get(6).equals(null)) badIP.setAsn(entries.get(6));
                else badIP.setAsn("Unknown");
                //System.out.println(badIP.getDomainName()+" "+badIP.getReverseLookupAddress() +" "+
                //        badIP.getIpAddress() +" "+badIP.getDateOfDiscovery()+ " " +badIP.getAsn() );

                badIPs.add(badIP);
            }
        }
        return badIPs;
    }

    public ArrayList<String> getHTMLcontentOfBadIpCom () throws IOException {
        String url = "https://www.badips.com/get/list/ssh/3?age=1w";
        Document document = Jsoup.connect(url).timeout(10*10000).get();
        Elements elements = document.getElementsByTag("html");

        /*try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        String[] temp = elements.text().split("(?<= ).\\S+");
        ArrayList<String> badIps = new ArrayList<String>();

        for (int i=0 ; i < temp.length ; i++) { //map string array to arraylist
            badIps.add(temp[i]);
        }

        System.out.println("1st ip address: "+ badIps.get(0));
        return badIps;
    }

    public ArrayList<String> getHTMLcontentOfMcAfeeCom () throws IOException {
        ArrayList<String> malwares = new ArrayList<String>();
        String url = "https://www.mcafee.com/threat-intelligence/malware/latest.aspx?region=us"; //this website wont allow to parse contents
        Document document = Jsoup.connect(url).timeout(10*10000).get();
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Elements elements = document.getElementsByTag("tbody");
        Elements rows =  elements.select("tr");
        System.out.println(elements);
        return malwares;
    }

    public ArrayList<Malware> getHMTLcontentOfSymantecCom () throws IOException {
        String url = "https://www.mcafee.com/threat-intelligence/malware/latest.aspx?region=us"; //this website wont allow to parse contents
        String url2 ="https://www.symantec.com/security_response/landing/azlisting.jsp";
        Document document = Jsoup.connect(url2).timeout(10*10000).get();
        Elements elements = document.getElementsByTag("tbody");
        Elements rows =  elements.select("tr");
        ArrayList<Malware> malwares = new ArrayList<Malware>();
        rows.remove(0); //remove the table header row

        for (Element row : rows){
            Elements columns = row.getElementsByTag("td");
            ArrayList<String> temp = new ArrayList<String>();
            for (Element column : columns) {
                //System.out.println("-" + column);
                if (column.text().equals("Symantec AntiVirus detections (1059)") ||
                        column.text().equals("\n") || column.text().equals("")){
                    continue;
                }else if (column.text().startsWith("(These threats are also detected by the latest Virus Definitions.)")){
                    String tempStr = column.text().replace(
                            "(These threats are also detected by the latest Virus Definitions.)",
                            "");
                    //System.out.println(tempStr);
                    String str = tempStr.replace(' ', '_');
                    String regexPattern = "\\S+";
                    Pattern pattern = Pattern.compile(regexPattern); //"/([A-z])+\\w(?=\")/g"  \S+(?=\")(?<=\")
                    Matcher matcher = pattern.matcher(str);
                    while (matcher.find()) {
                        //System.out.println(matcher.group().substring(0,matcher.group().length()));
                        temp.add((matcher.group().substring(0, matcher.group().length())));
                    }

                }else {
                    //System.out.println("----" + column.text().replace(' ', '_'));
                    temp.add(column.text().replace(' ', '_'));
                }

            }
            if (temp.size()==1){
                Malware m = new Malware();
                m.setName(temp.get(0));
                m.setType("Other");
                m.setDiscoveryDate("Unknown");
                malwares.add(m);
            }else if (temp.size()==2){
                Malware m = new Malware();
                m.setName(temp.get(0));
                m.setType(temp.get(1));
                m.setDiscoveryDate("Unknown");
                malwares.add(m);
            }else if (temp.size()==3){
                Malware m = new Malware();
                m.setName(temp.get(0));
                m.setType(temp.get(1));
                m.setDiscoveryDate(temp.get(2));
                malwares.add(m);
            }else if (temp.size()>3){
                for (String t : temp){
                    Malware m = new Malware();
                    m.setName(t);
                    m.setType("Other");
                    m.setDiscoveryDate("Unknown");
                    malwares.add(m);
                }
            }

        }
        for (Malware mal : malwares){
            System.out.println("Malware Name: "+ mal.getName() + "    Type: "+ mal.getType()+ " Discovery date: "+ mal.getDiscoveryDate());
            System.out.println("-----------------------------------------------------------------------------------------------");
        }
        return malwares;
    }


}
