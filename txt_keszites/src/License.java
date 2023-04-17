public record License(String licenseNumber, String licenseType, String licenseValidity, String licenseOwner,
                      String licenseDomesticOwner) {
    public License(String licenseNumber, String licenseType, String licenseValidity, String licenseOwner,
                   String licenseDomesticOwner) {
        this.licenseNumber = licenseNumber;
        this.licenseType = licenseType;
        this.licenseValidity = licenseValidity;
        this.licenseOwner = licenseOwner;
        this.licenseDomesticOwner = licenseDomesticOwner;
    }
}
