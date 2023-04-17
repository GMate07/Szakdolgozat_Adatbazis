public record UseRegulation(String name, String quantity, String harmfulFactors, String juiceQuantity,
                            String foodWaitPeriod, String workWaitPeriod, String aerialApplication) {
    public UseRegulation(String name, String quantity, String harmfulFactors, String juiceQuantity,
                         String foodWaitPeriod, String workWaitPeriod, String aerialApplication) {
        this.name = name;
        this.quantity = quantity;
        this.harmfulFactors = harmfulFactors;
        this.juiceQuantity = juiceQuantity;
        this.foodWaitPeriod = foodWaitPeriod;
        this.workWaitPeriod = workWaitPeriod;
        this.aerialApplication = aerialApplication;
    }
}
