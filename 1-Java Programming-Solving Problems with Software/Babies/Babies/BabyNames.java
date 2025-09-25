import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class BabyNames {

    /* ------------------- Utility ------------------- */

    private void printSeparator() {
        System.out.println("--------------------------------------------------");
    }

    /* ------------------- totalBirths ------------------- */

    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalGirlsBirths = 0;
        int totalBoysBirths = 0;
        int girlCount = 0;
        int boyCount = 0;

        for (CSVRecord rec : fr.getCSVParser(false)) {
            int births = Integer.parseInt(rec.get(2));
            totalBirths += births;
            if (rec.get(1).equals("F")) {
                totalGirlsBirths += births;
                girlCount++;
            } else {
                totalBoysBirths += births;
                boyCount++;
            }
        }

        System.out.println("Total births = " + totalBirths);
        System.out.println("Girls births = " + totalGirlsBirths);
        System.out.println("Boys births = " + totalBoysBirths);
        System.out.println("Number of girl names = " + girlCount);
        System.out.println("Number of boy names = " + boyCount);
    }

    public void testTotalBirths() {
        FileResource fr = new FileResource(); // choose file
        totalBirths(fr);
        printSeparator();
    }

    /* ------------------- getRank ------------------- */

    public int getRankFromFile(FileResource fr, String name, String gender) {
        int rank = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equalsIgnoreCase(gender)) {
                rank++;
                if (rec.get(0).equalsIgnoreCase(name)) {
                    return rank;
                }
            }
        }
        return -1;
    }

    // Example: Q3 - Rank of Emily (F) in 1960
    public void testGetRankForName() {
        FileResource fr = new FileResource(); // choose yob1960.csv
        int rank = getRankFromFile(fr, "Frank", "M");
        System.out.println("Rank of Frank (M) = " + rank);
        printSeparator();
    }

    /* ------------------- getName ------------------- */

    public String getNameFromFile(FileResource fr, int rank, String gender) {
        int currentRank = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equalsIgnoreCase(gender)) {
                currentRank++;
                if (currentRank == rank) {
                    return rec.get(0);
                }
            }
        }
        return "NO NAME";
    }

    // Example test: 
    public void testGetNameAtRankX() {
        FileResource fr = new FileResource(); // choose any file
        String name = getNameFromFile(fr, 450, "M");
        System.out.println("Name at rank X: " + name);
        printSeparator();
    }

    /* ------------------- whatIsNameInYear ------------------- */

    public void whatIsNameInFiles(String name, FileResource frOriginal, FileResource frNew, String gender) {
        int rank = getRankFromFile(frOriginal, name, gender);
        if (rank == -1) {
            System.out.println(name + " not found in original file.");
        } else {
            String newName = getNameFromFile(frNew, rank, gender);
            System.out.println(name + " would be " + newName + " in the new file (same rank " + rank + ")");
        }
    }

    public void testWhatIsNameInYear() {
        FileResource frOrig = new FileResource(); // choose first year
        FileResource frNew = new FileResource();  // choose new year
        whatIsNameInFiles("Owen", frOrig, frNew, "M");
        printSeparator();
    }

    /* ------------------- yearOfHighestRank ------------------- */

    private int extractYearFromFilename(File f) {
        String name = f.getName();
        Matcher m = Pattern.compile("(\\d{4})").matcher(name);
        if (m.find()) return Integer.parseInt(m.group(1));
        return -1;
    }

    public int yearOfHighestRankFromFiles(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int bestRank = Integer.MAX_VALUE;
        int bestYear = -1;

        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int rank = getRankFromFile(fr, name, gender);
            if (rank != -1 && rank < bestRank) {
                bestRank = rank;
                bestYear = extractYearFromFilename(f);
            }
        }
        return bestYear;
    }

    public void testYearOfHighestRank() {
        int year = yearOfHighestRankFromFiles("Mich", "M");
        System.out.println("Year of highest rank = " + year);
        printSeparator();
    }

    /* ------------------- getAverageRank ------------------- */

    public double getAverageRankFromFiles(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int total = 0;
        int count = 0;

        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int rank = getRankFromFile(fr, name, gender);
            if (rank != -1) {
                total += rank;
                count++;
            }
        }
        if (count == 0) return -1.0;
        return (double) total / count;
    }

    public void testGetAverageRank() {
        double avg = getAverageRankFromFiles("Robert", "M");
        System.out.println("Average rank of Susan = " + avg);
        printSeparator();
    }

    /* ------------------- getTotalBirthsRankedHigher ------------------- */

    public int getTotalBirthsRankedHigherFromFile(FileResource fr, String name, String gender) {
        int total = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equalsIgnoreCase(gender)) {
                if (rec.get(0).equalsIgnoreCase(name)) break;
                total += Integer.parseInt(rec.get(2));
            }
        }
        return total;
    }

    public void testGetTotalBirthsRankedHigher() {
        FileResource fr = new FileResource();
        int total = getTotalBirthsRankedHigherFromFile(fr, "Drew", "M");
        System.out.println("Total births of boys ranked higher than Ethan = " + total);
        printSeparator();
    }
}