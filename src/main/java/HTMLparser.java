import org.apache.jena.base.Sys;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLparser  {

    public static void main (String args[]) throws IOException, InterruptedException {
        HTMLparser htmlParser = new HTMLparser();
        //htmlParser.getHTMLcontentOfMalwareDomainListCom();
        //htmlParser.getHTMLcontentOfMcAfeeCom();
        htmlParser.getHMTLcontentOfSymantecCom();
        //htmlParser.getHTMLcontentOfBadIpCom();
        //htmlParser.getAllBadDomains();

    }

    //url is the html file's url, part is the required html part (e.g <br>)
    public ArrayList<BadDomain> getHTMLcontentOfMalwareDomainListCom() throws IOException {
        String url = "https://www.malwaredomainlist.com/mdl.php";
        ArrayList<BadDomain> badDomains = new ArrayList<BadDomain>();
        Document document = Jsoup.connect(url).timeout(0).get();
        /*try {//website responses late so we make the program wait...
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Elements elements = document.getElementsByTag("tbody");
        Elements rows = elements.select("tr");
        //System.out.println(rows);


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
                BadDomain badDomain = new BadDomain();
                //add relevant data
                if (!entries.get(0).equals(null)) badDomain.setDateOfDiscovery(entries.get(0));
                else badDomain.setDateOfDiscovery("Unknown");
                ArrayList<String> domains = new ArrayList<String>();

                if (!entries.get(1).equals(null)) {
                    domains.add(entries.get(1));
                    badDomain.setDomainName(domains);
                }
                else{
                    domains.add("Unknown");
                    badDomain.setDomainName(domains);
                }
                if (!entries.get(2).equals(null)) badDomain.setIpAddress(entries.get(2));
                else badDomain.setIpAddress("Unknown");
                if (!entries.get(3).equals(null)) badDomain.setReverseLookupAddress(entries.get(3));
                else badDomain.setReverseLookupAddress("Unknown");
                if (!entries.get(4).equals(null)) badDomain.setDescription(entries.get(4));
                else badDomain.setDescription("Unknown");
                if (!entries.get(5).equals(null)) badDomain.setRegistrant(entries.get(5));
                else badDomain.setRegistrant("Unknown");
                if (!entries.get(6).equals(null)) badDomain.setAsn(entries.get(6));
                else badDomain.setAsn("Unknown");
                //System.out.println(badDomain.getDomainName()+" "+badDomain.getReverseLookupAddress() +" "+
                //        badDomain.getIpAddress() +" "+badDomain.getDateOfDiscovery()+ " " +badDomain.getAsn() );

                badDomains.add(badDomain);
            }
            for (int i = 0; i < badDomains.size(); i++){ //merge same ip addresses' domain names
                for (int j = i+1; j <  badDomains.size(); j++){
                    if (badDomains.get(i).getIpAddress().equals(badDomains.get(j).getIpAddress())){
                        String temp = badDomains.get(j).getDomainName().get(0);
                        ArrayList<String> domainNames = badDomains.get(i).getDomainName();
                        domainNames.add(temp);
                        badDomains.get(j).setDomainName(domainNames);
                        badDomains.remove(i);
                    }
                }
            }
        }
        /*for(BadDomain bd : badDomains){
            System.out.print("ip: " + bd.getIpAddress() + "domain names: ");
            for(String s : bd.getDomainName())
                System.out.print(s + " -- ");
            System.out.print("\n");
        }*/

        return badDomains;
    }

    public ArrayList<BadDomain> getHTMLcontentOfBadIpCom () throws IOException {
        String url = "https://www.badips.com/get/list/ssh/3?age=1w";
        Document document = Jsoup.connect(url).timeout(10*10000).get();
        Elements elements = document.getElementsByTag("html");
        String regexPattern = "\\S+";
        ArrayList<String> temp = new ArrayList<String>();

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(elements.text());
        while (matcher.find()) { //REGEX AND FOR LOOP ABOVE WILL BE MERGED
            temp.add((matcher.group().substring(0, matcher.group().length())));
        }
        ArrayList<BadDomain> badDomains = new ArrayList<BadDomain>();
        ArrayList<String> unknownDomain = new ArrayList<String>();
        unknownDomain.add("Unknown");
        for (int i=0 ; i < temp.size() ; i++) { //map string array to arraylist
            //System.out.println(temp.get(i));
            BadDomain bd = new BadDomain();
            bd.setIpAddress(temp.get(i));
            bd.setDateOfDiscovery("Unknown");
            bd.setAsn("Unknown");
            bd.setDescription("Unknown");
            bd.setRegistrant("Unknown");
            bd.setReverseLookupAddress("Unknown");
            bd.setDomainName(unknownDomain);
            badDomains.add(bd);
        }
        /*for (BadDomain bd : badDomains) {
            System.out.println(bd.getIpAddress());
        }*/
        return badDomains;
    }

    public ArrayList<BadDomain> getAllBadDomains () throws IOException {
        ArrayList<BadDomain> badDomains = new ArrayList<BadDomain>();
        ArrayList<BadDomain> badipcom = getHTMLcontentOfBadIpCom();
        System.out.println("Bad ip website parsed");
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        ArrayList<BadDomain> mdlcom = getHTMLcontentOfMalwareDomainListCom();
        System.out.println("Malware domain list parsed.");


        //System.out.println(badipcom.size());
        //System.out.println(mdlcom.size());

        for (BadDomain bd : badipcom){
            for (BadDomain bd1 : mdlcom) {
                //check each ip then add preferably mdlcom
                if (bd.getIpAddress().equals(bd1.getIpAddress())){
                    //System.out.println("Same: " + bd.getIpAddress());
                    badDomains.add(bd1);
                    badipcom.remove(bd);
                    mdlcom.remove(bd1);
                }//add the same ones to the result arraylist
            }
        }
        //System.out.println(badipcom.size());
        //System.out.println(mdlcom.size());
        for (BadDomain bd : badipcom){
            badDomains.add(bd);
        }
        for (BadDomain bd  :mdlcom) {
            badDomains.add(bd);
        }
        System.out.println("Bad IP website parsed.");
        return badDomains;
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
        //System.out.println(elements);
        return malwares;
    }

    public ArrayList<Malware> getHMTLcontentOfSymantecCom () throws IOException {
        //String url = "https://www.mcafee.com/threat-intelligence/malware/latest.aspx?region=us"; //this website wont allow to parse contents
        //String url2 ="https://www.symantec.com/security_response/landing/azlisting.jsp";
        String rawUrl = "https://www.symantec.com/security-center/a-z/";
        ArrayList<Malware> malwares = new ArrayList<Malware>();
        ArrayList<String> urls = new ArrayList<String>();
        ArrayList<String> prefixes = new ArrayList<String>();
        prefixes.add("Android.");
        prefixes.add("Adware.");
        prefixes.add("Backdoor.");
        prefixes.add("Downloader.");
        prefixes.add("Exp.");
        prefixes.add("Hacktool.");
        //prefixes.add("Linux.");
        //prefixes.add("OSX.");
        prefixes.add("Ransom.");
        //prefixes.add("SONAR.");
        prefixes.add("Trojan.");
        //prefixes.add("Unix.");
        //prefixes.add("W32.");
        //prefixes.add("W97M.");
        //prefixes.add("W64.");
        prefixes.add("Worm.");

        ArrayList<String> characters = new ArrayList<String>();
        characters.add("{"); // | '}' | '|' | '\' | '^' | '[' | ']' | '`'
        characters.add("}");
        characters.add("|");
        characters.add("\\");
        characters.add("^");
        characters.add("[");
        characters.add("]");
        characters.add("`");
        characters.add(";");
        characters.add("$");
        characters.add("?");
        characters.add(":");
        characters.add("@");
        characters.add(",");
        characters.add("=");
        characters.add("&");
        characters.add("+");
        characters.add("<");
        characters.add(">");
        characters.add("#");
        characters.add("%");

        for (char s = 'A'; s <= 'Z'; s++){
            urls.add(rawUrl+s);
            System.out.println(rawUrl+s);
        }
        urls.add(rawUrl+"_1234567890");
        //for each url we parse the website
        for (String url : urls) {
            Document document = Jsoup.connect(url).timeout(10*10000).get();
            Elements elements = document.getElementsByTag("tbody"); //PARSING NEEDS MORE WORK
            Elements rows =  elements.select("tr");
            System.out.println("Parsing: " + url);
            //this is for malwares in the table
            for (Element row : rows){
                Elements columns = row.getElementsByTag("td");
                ArrayList<String> temp = new ArrayList<String>();
                for (Element column : columns) {
                    //System.out.println("-" + column);
                    temp.add(column.text().trim().replace(' ', '_'));
                }
                if (temp.size()==1){
                    Malware m = new Malware();
                    String temp1 = temp.get(0);
                    for(String pre : prefixes){
                        if(temp1.startsWith(pre))
                            temp1 = temp.get(0).replace(pre, "");
                    }// remove known words like worm, android, adware etc...

                    for (String c : characters){
                        if(temp1.contains(c)){
                            temp1 = temp1.replace(c.toCharArray()[0], '_');
                        }
                    }
                    m.setName(temp1.toLowerCase());
                    m.setType("Other");
                    m.setDiscoveryDate("Unknown");
                    malwares.add(m);
                }else if (temp.size()==2){
                    Malware m = new Malware();
                    String temp1 = temp.get(0);
                    for(String pre : prefixes){
                        if(temp1.startsWith(pre))
                            temp1 = temp.get(0).replace(pre, "");
                    }// remove known words like worm, android, adware etc...
                    for (String c : characters){
                        if(temp1.contains(c)){
                            temp1 = temp1.replace(c.toCharArray()[0], '_');
                        }
                    }
                    m.setName(temp1.toLowerCase());
                    m.setType(temp.get(1));
                    m.setDiscoveryDate("Unknown");
                    malwares.add(m);
                }else if (temp.size()==3){
                    Malware m = new Malware();
                    String temp1 = temp.get(0);
                    for(String pre : prefixes){
                        if(temp1.startsWith(pre))
                            temp1 = temp.get(0).replace(pre, "");
                    }// remove known words like worm, android, adware etc...
                    for (String c : characters){
                        if(temp1.contains(c)){
                            temp1 = temp1.replace(c.toCharArray()[0], '_');
                        }
                    }
                    m.setName(temp1.toLowerCase());
                    m.setType(temp.get(1));
                    m.setDiscoveryDate(temp.get(2));
                    malwares.add(m);
                }
            }

            Elements list = document.getElementsByTag("pre");
            String regexPattern = ".+(?<!\\n)|.+(?>=\\n)|(?<=\\n).+";
            Pattern pattern = Pattern.compile(regexPattern);
            //System.out.println(list.text());
            Matcher matcher = pattern.matcher(list.text());

            //this is for malwares under <pre> tag
            while (matcher.find()) {
                //System.out.println(matcher.group().substring(0,matcher.group().length()));
                Malware m = new Malware();
                String temp = matcher.group().substring(0, matcher.group().length()).trim().replace(' ', '_');
                String temp1 = temp;
                for (String pre : prefixes){
                    if (temp.startsWith(pre)){
                        temp1 = temp.replace(pre, "");
                    }
                }// remove known words like worm, android, adware etc...

                for (String c : characters){
                    if(temp1.contains(c)){
                        temp1 = temp1.replace(c.toCharArray()[0], '_');
                    }
                }
                m.setName(temp1.toLowerCase());
                m.setType("Other");
                m.setDiscoveryDate("Unknown");
                malwares.add(m);
            }

        }



        /*for (Element l : latest) {
            System.out.println(latest.text() + "\n");
        }*/
        int i = 0;
        for (Malware mal : malwares){
            System.out.println("Malware Name: "+ mal.getName() + "    Type: "+ mal.getType()+ " Discovery date: "+ mal.getDiscoveryDate());
            //System.out.println("-----------------------------------------------------------------------------------------------");
            i++;
        }
        System.out.println(i + " malwares parsed.");
        return malwares;
    }


}
