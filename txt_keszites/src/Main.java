import java.io.File;
import java.text.MessageFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Novenyvedoszer> novenyvedoszerek = new ArrayList<>();

        for (int fileIndex = 0; fileIndex <= 2; fileIndex++) {
            File f = new File(MessageFormat.format("./src/{0}.txt", fileIndex));

            try {
                Scanner sc = new Scanner(f);
                StringBuilder sb = new StringBuilder();

                while (sc.hasNextLine()) {
                    sb.append(sc.nextLine()).append('\n');
                }

                String text = sb.toString();
                String[] parts = text.split("\n");

                List<UseRegulation> regulations = new ArrayList<>();

                int next = 0;
                for (int i = 4; i < parts.length; i += 6) {
                    if (parts[i].equals("Engedélyokirat:")) {
                        next = i + 1;
                        break;
                    }

                    UseRegulation regulation = new UseRegulation(
                        parts[i].split(": ")[0],
                        parts[i].split(": ")[1],
                        parts[i + 1].split(": ")[1],
                        parts[i + 2].split(": ")[1],
                        parts[i + 3].split(": ")[1],
                        parts[i + 4].split(": ")[1],
                        parts[i + 5].split(": ")[1]
                    );

                    regulations.add(regulation);
                }

                int to = Arrays.asList(parts).indexOf("Altalános adatok:");

                License license = new License(
                    parts[next].split(": ")[1],
                    parts[next + 1].split(": ")[1],
                    parts[next + 2].split(": ")[1],
                    parts[next + 3].split(": ")[1],
                    next + 4 == to ? "" : parts[next + 4].split(": ")[1]
                );

                next = to + 1;

                GeneralData generalData = new GeneralData(
                    parts[next].split(": ")[1],
                    parts[next + 1].split(": ")[1],
                    parts[next + 2].split(": ")[1],
                    parts[next + 3].split(": ")[1],
                    parts[next + 4].split(": ")[1],
                    parts[next + 5].split(": ")[1]
                );

                next += 7;

                String allowedInAgriculture = parts[next];
                next++;

                List<AgricultureRestriction> restrictions = new ArrayList<>();
                String name = "";
                boolean first = true;
                sb.delete(0, sb.length());
                while (next < parts.length) {
                    if (parts[next].equals("Veszélyességre, biztonságra vonatkozó előírások:")) {
                        if (sb.length() != 0) {
                            AgricultureRestriction restriction = new AgricultureRestriction(
                                name,
                                sb.toString()
                            );

                            restrictions.add(restriction);
                        }

                        sb.delete(0, sb.length());
                        next++;
                        break;
                    }

                    if (first) {
                        name = parts[next].split(": ")[1];
                        first = false;
                    } else if (parts[next].split(": ").length == 1) {
                        AgricultureRestriction restriction = new AgricultureRestriction(
                            name,
                            sb.toString()
                        );

                        name = parts[next].split(": ")[0];
                        restrictions.add(restriction);
                        sb.delete(0, sb.length());
                    } else {
                        sb.append(parts[next]);
                    }

                    next++;
                }

                Map<String, String> warningLookup = new TreeMap<>();
                List<String> keyLookup = new ArrayList<>() {
                    {
                        add("A készítmény p.o. LD50 értéke (patkányon)");
                        add("Vízi veszélyesség");
                        add("Méhveszélyesség");
                        add("Méhkímélő technológia");
                        add("S-mondatok");
                        add("H-mondatok");
                        add("P-mondatok");
                        add("Védőfelszerelés előkészítőknek");
                        add("Védőfelszerelés kijuttatóknak");
                    }
                };
                String sbInitializer = "";
                while (next < parts.length) {
                    if (keyLookup.contains(parts[next].split(": ")[0])) {
                        if (sb.length() > 0) {
                            warningLookup.put(sbInitializer, sb.toString());
                            sb.delete(0, sb.length());
                        }

                        switch (parts[next].split(": ")[0]) {
                            case "A készítmény p.o. LD50 értéke (patkányon)":
                                warningLookup.put("poLD", parts[next].split(": ")[1]);
                                break;
                            case "Vízi veszélyesség":
                                warningLookup.put("dangerInWater", parts[next].split(": ")[1]);
                                break;
                            case "Méhveszélyesség":
                                warningLookup.put("dangerOnBees", parts[next].split(": ")[1]);
                                break;
                            case "Méhkímélő technológia":
                                warningLookup.put("beeProtectionTechnology", parts[next].split(": ")[1]);
                                break;
                            case "S-mondatok":
                                sbInitializer = "S-mondatok";
                                sb.append(parts[next].split(": ")[1]);
                                break;
                            case "H-mondatok":
                                sbInitializer = "H-mondatok";
                                sb.append(parts[next].split(": ")[1]);
                                break;
                            case "P-mondatok":
                                sbInitializer = "P-mondatok";
                                sb.append(parts[next].split(": ")[1]);
                                break;
                            case "Védőfelszerelés előkészítőknek":
                                warningLookup.put("gearTrainers", parts[next].split(": ")[1]);
                                break;
                            case "Védőfelszerelés kijuttatóknak":
                                warningLookup.put("gearApplicators", parts[next].split(": ")[1]);
                                break;
                        }
                    } else {
                        sb.append(parts[next]);
                    }

                    next++;
                }

                Warning warning = new Warning(
                    warningLookup.get("poLD"),
                    warningLookup.get("dangerInWater"),
                    warningLookup.get("dangerOnBees"),
                    warningLookup.get("beeProtectionTechnology"),
                    warningLookup.get("S-mondatok"),
                    warningLookup.get("H-mondatok"),
                    warningLookup.get("P-mondatok"),
                    warningLookup.get("gearTrainers"),
                    warningLookup.get("gearApplicators")
                );

                 novenyvedoszerek.add(new Novenyvedoszer(
                    parts[0].split(": ")[1],
                    parts[1].split(": ")[1],
                    parts[2].split(": ")[1],
                    regulations,
                    license,
                    generalData,
                    allowedInAgriculture,
                    restrictions.size() > 0 ? restrictions : null,
                    warning
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // teszt kiíratás
        for (Novenyvedoszer n : novenyvedoszerek) {
            System.out.println(n);
            System.out.println("\n-------------------------------------\n");
        }
    }
}
