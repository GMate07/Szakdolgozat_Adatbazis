import java.util.List;

public record Novenyvedoszer(String name, String destination, String ingredients, List<UseRegulation> regulations,
                             License license, GeneralData generalData, String allowedInAgriculture,
                             List<AgricultureRestriction> restrictions, Warning warning) {
    public Novenyvedoszer (String name, String destination, String ingredients, List<UseRegulation> regulations,
                           License license, GeneralData generalData, String allowedInAgriculture,
                           List<AgricultureRestriction> restrictions, Warning warning) {
        this.name = name;
        this.destination = destination;
        this.ingredients = ingredients;
        this.regulations = regulations;
        this.license = license;
        this.generalData = generalData;
        this.allowedInAgriculture = allowedInAgriculture;
        this.restrictions = restrictions;
        this.warning = warning;
    }

    @Override
    public String toString() {
        return this.name + '\n' + this.ingredients + '\n' + this.regulations + '\n' + this.license + '\n'
            + this.generalData + '\n' + this.allowedInAgriculture + '\n' + this.restrictions + '\n' + this.warning;
    }
}
