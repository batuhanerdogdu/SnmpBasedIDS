public class BadDomain { //https://www.badips.com/get/list/ssh/3?age=1w
                    //https://www.malwaredomainlist.com/mdl.php?search=&colsearch=All&quantity=All

    private String dateOfDiscovery;
    private String domainName;
    private String ipAddress;
    private String reverseLookupAddress;
    private String description;
    private String registrant;
    private String asn;

    public String getDateOfDiscovery() {
        return dateOfDiscovery;
    }

    public void setDateOfDiscovery(String dateOfDiscovery) {
        this.dateOfDiscovery = dateOfDiscovery;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getReverseLookupAddress() {
        return reverseLookupAddress;
    }

    public void setReverseLookupAddress(String reverseLookupAddress) {
        this.reverseLookupAddress = reverseLookupAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }
}
