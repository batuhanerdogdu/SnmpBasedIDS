import org.apache.jena.base.Sys;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HTMLparser  {

    public static void main (String args[]) throws IOException, InterruptedException {
        //parse symantecs website: https://www.symantec.com/security_response/landing/azlisting.jsp
        //parse mcafee's website: https://www.mcafee.com/threat-intelligence/malware/latest.aspx?region=us
        //bad ips: https://www.badips.com/get/list/ssh/3?age=1w
        HTMLparser htmlParser = new HTMLparser();
        //htmlParser.getHTMLcontentOfMalwareDomainListCom();
        htmlParser.getHMTLcontentOfSymantecCom();
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
        rows.remove(1);

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

    public String[] getHTMLcontentOfBadIpCom () throws IOException {
        String url = "https://www.badips.com/get/list/ssh/3?age=1w";
        Document document = Jsoup.connect(url).timeout(10*10000).get();
        Elements elements = document.getElementsByTag("html");

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] badIps = elements.text().split("(?<= ).\\S+");

        System.out.println("1st ip address: "+ badIps[0]);
        return badIps;
    }

    public ArrayList<Malware> getHMTLcontentOfSymantecCom () throws IOException {
        String url = "https://www.mcafee.com/threat-intelligence/malware/latest.aspx?region=us";
        String url2 ="https://www.symantec.com/security_response/landing/azlisting.jsp";
        Document document = Jsoup.connect(url2).timeout(10*10000).get();
        Elements elements = document.select("tbody");

        Elements rows =  elements.select("tr");
        /*try{
            Thread.sleep(8000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }*/
        //System.out.println(elements);
        ArrayList<Malware> malwares = new ArrayList<Malware>();
        for (Element row : rows){
            Elements columns = row.getElementsByTag("td");
            for (Element column : columns) {
                System.out.println (column.text());
            }
        }
        return malwares;
    }


}
