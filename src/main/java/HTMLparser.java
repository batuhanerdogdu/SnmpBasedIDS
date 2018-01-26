import org.apache.jena.base.Sys;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class HTMLparser  {

    public static void main (String args[]) throws IOException {
        HTMLparser htmlParser = new HTMLparser();
        htmlParser.getHTMLcontent("https://www.malwaredomainlist.com/mdl.php?search=&colsearch=All&quantity=All",
                "td");
    }

    //url is the html file's url, part is the required html part (e.g <br>)
    public HashMap<String, String> getHTMLcontent(String url, String part) throws IOException { //"https://www.malwaredomainlist.com/mdl.php"
        HashMap<String ,String > contents = new HashMap<String, String>();
        Document document = Jsoup.connect(url).timeout(10*10000).get();
        Element element = document.getElementById("content_box");
        Elements es = element.select("tr");

        for (Element e : es){
            Elements trs = e.getElementsByTag("td");
            for (Element td : trs){
                System.out.println(td.text());
            }
        }
        return contents;
    }



}
