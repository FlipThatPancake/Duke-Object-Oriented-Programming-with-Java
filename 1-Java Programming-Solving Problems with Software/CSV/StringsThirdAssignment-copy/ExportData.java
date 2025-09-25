import edu.duke.*;
import org.apache.commons.csv.*;

public class ExportData {

    public void tester() {
        FileResource fr = new FileResource();
        
        // Test countryInfo
        CSVParser parser1 = fr.getCSVParser();
        System.out.println("Country Info for Nauru: " + countryInfo(parser1, "Nauru"));
        
        System.out.println("\n-------------------------------\n");

        // Test listExportersTwoProducts
        CSVParser parser2 = fr.getCSVParser();
        System.out.println("Countries that export cotton and flowers:");
        listExportersTwoProducts(parser2, "cotton", "flowers");

        System.out.println("\n-------------------------------\n");

        // Test numberOfExporters
        CSVParser parser3 = fr.getCSVParser();
        System.out.println("Number of countries that export cocoa: " + numberOfExporters(parser3, "cocoa"));
        System.out.println("Number of countries that export motor vehicles: " + numberOfExporters(parser3, "motor vehicles"));

        System.out.println("\n-------------------------------\n");

        // Test bigExporters
        CSVParser parser4 = fr.getCSVParser();
        System.out.println("Countries with exports greater than $999,999,999:");
        bigExporters(parser4, "$999,999,999");
        
        CSVParser parser5 = fr.getCSVParser();
        String thirdCountry = findNthCountryWithTwoProducts(parser5, "fish", "nuts", 3);
        System.out.println("The third country that exports fish and nuts is: " + thirdCountry);
    }

    /**
     * Prints information for a specific country.
     * @param parser The CSVParser for the data.
     * @param country The name of the country.
     * @return A string with the country's export information or "NOT FOUND".
     */
    public String countryInfo(CSVParser parser, String country) {
        for (CSVRecord record : parser) {
            String currentCountry = record.get("Country");
            if (currentCountry.equals(country)) {
                return currentCountry + ": " + record.get("Exports") + ": " + record.get("Value (dollars)");
            }
        }
        return "NOT FOUND";
    }

    /**
     * Prints the names of countries that export two specific items.
     * @param parser The CSVParser for the data.
     * @param exportItem1 The first export item.
     * @param exportItem2 The second export item.
     */
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportItem1) && exports.contains(exportItem2)) {
                System.out.println(record.get("Country"));
            }
        }
    }

    /**
     * Counts the number of countries that export a specific item.
     * @param parser The CSVParser for the data.
     * @param exportItem The export item to count.
     * @return The number of countries.
     */
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int count = 0;
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportItem)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Prints the names and values of countries with an export value string longer than the given amount string.
     * @param parser The CSVParser for the data.
     * @param amount The string representation of the dollar amount.
     */
    public void bigExporters(CSVParser parser, String amount) {
        int amountLength = amount.length();
        for (CSVRecord record : parser) {
            String value = record.get("Value (dollars)");
            if (value.length() > amountLength) {
                System.out.println(record.get("Country") + " " + value);
            }
        }
    }
    
    /**
     * Finds the name of the Nth country that exports two specific items.
     * In this case, it finds the third country.
     *
     * @param parser    The CSVParser for the data.
     * @param exportItem1 The first export item.
     * @param exportItem2 The second export item.
     * @return The name of the third country found, or "NOT FOUND" if fewer than three countries match.
     */
    public String findNthCountryWithTwoProducts(CSVParser parser, String exportItem1, String exportItem2, int n) {
        int count = 0;
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportItem1) && exports.contains(exportItem2)) {
                count++;
                if (count == n) {
                    return record.get("Country");
                }
            }
        }
        return "NOT FOUND";
    }
}