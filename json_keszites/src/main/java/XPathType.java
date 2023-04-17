public enum XPathType {
    // A szer tulajdonságainak HTML blokkja felsorolásos típusként
    PRODUCTNAME("/html/body/div[2]/main/section[1]/div/div[2]/h1"),
    DESIGNATION("/html/body/div[2]/main/section[2]/div/div/div[1]/div[1]/div/ul/li/span"),
    INGREDIENT("/html/body/div[2]/main/section[2]/div/div/div[1]/div[2]/div/ul/li/span"),
    INSTRUCTIONS("/html/body/div[2]/main/section[2]/div/div/div[1]/div[3]/div"),
    LICENSE("/html/body/div[2]/main/section[2]/div/div/div[1]/div[4]/div"),
    GENERALINFO("/html/body/div[2]/main/section[2]/div/div/div[1]/div[5]/div"),
    ALLOWEDINAGRICULTURE("/html/body/div[2]/main/section[2]/div/div/div[1]/div[6]/div"),
    DANGERANDSAFETY("/html/body/div[2]/main/section[2]/div/div/div[1]/div[7]/div");

    public final String path;
    // Enum példányosításas egy String mezővel, ami a HTML feldolgozása szempontjából releváns értéket hordoz
    XPathType(String path) {
        this.path = path;
    }
}
