public record Warning(String poLD, String dangerInWater, String dangerOnBees, String beeProtectionTechnology,
                      String sSentences, String hSentences, String pSentences, String gearTrainers,
                      String gearApplicators) {
    public Warning(String poLD, String dangerInWater, String dangerOnBees, String beeProtectionTechnology,
                   String sSentences, String hSentences, String pSentences, String gearTrainers,
                   String gearApplicators) {
        this.poLD = poLD;
        this.dangerInWater = dangerInWater;
        this.dangerOnBees = dangerOnBees;
        this.beeProtectionTechnology = beeProtectionTechnology;
        this.sSentences = sSentences;
        this.hSentences = hSentences;
        this.pSentences = pSentences;
        this.gearTrainers = gearTrainers;
        this.gearApplicators = gearApplicators;
    }
}
