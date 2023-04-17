public record GeneralData(String hungarianDistributor, String manufacturer, String formulation,
                          String facturingCategory, String allowedInEcology, String packaging) {
    public GeneralData(String hungarianDistributor, String manufacturer, String formulation,
                       String facturingCategory, String allowedInEcology, String packaging) {
        this.hungarianDistributor = hungarianDistributor;
        this.manufacturer = manufacturer;
        this.formulation = formulation;
        this.facturingCategory = facturingCategory;
        this.allowedInEcology = allowedInEcology;
        this.packaging = packaging;
    }
}
