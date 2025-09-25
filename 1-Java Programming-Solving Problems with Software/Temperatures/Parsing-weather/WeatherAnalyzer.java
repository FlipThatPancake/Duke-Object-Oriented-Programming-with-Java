import org.apache.commons.csv.*;
import edu.duke.*;
import java.io.*;

public class WeatherAnalyzer {

    // 1. Find coldest hour in a single file
    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord coldestSoFar = null;
        for (CSVRecord currentRow : parser) {
            coldestSoFar = getColdestOfTwo(currentRow, coldestSoFar);
        }
        return coldestSoFar;
    }

    // Helper method to compare two records
    private CSVRecord getColdestOfTwo(CSVRecord currentRow, CSVRecord coldestSoFar) {
        if (coldestSoFar == null) {
            return currentRow;
        }

        double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
        double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));

        // Ignore bogus values (-9999)
        if (currentTemp == -9999) {
            return coldestSoFar;
        }

        if (currentTemp < coldestTemp) {
            return currentRow;
        } else {
            return coldestSoFar;
        }
    }
    
    // Tester for coldestHourInFile
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();  // choose one file
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldest = coldestHourInFile(parser);
        System.out.println("Coldest temperature was " + coldest.get("TemperatureF") +
                " at " + coldest.get("DateUTC"));
    }
    
    // 2. Find which file has the coldest temperature
    public File fileWithColdestTemperature() {
        CSVRecord coldestSoFar = null;
        File coldestFile = null;
    
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
    
            if (coldestSoFar == null) {
                coldestSoFar = currentRow;
                coldestFile = f;
            } else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
    
                if (currentTemp < coldestTemp) {
                    coldestSoFar = currentRow;
                    coldestFile = f;
                }
            }
        }
        return coldestFile;
    }
    
    public void testFileWithColdestTemperature() {
        File coldestFile = fileWithColdestTemperature();
        System.out.println("Coldest day was in file " + coldestFile.getName());
    
        FileResource fr = new FileResource(coldestFile);
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldest = coldestHourInFile(parser);
    
        System.out.println("Coldest temperature on that day was " + coldest.get("TemperatureF"));
        System.out.println("All the Temperatures on the coldest day were:");
        for (CSVRecord record : fr.getCSVParser()) {
            System.out.println(record.get("DateUTC") + ": " + record.get("TemperatureF"));
        }
    }

    // 3. Find the record with the lowest humidity in a single file
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestSoFar = null;
        for (CSVRecord currentRow : parser) {
            lowestSoFar = getLowerHumidityOfTwo(currentRow, lowestSoFar);
        }
        return lowestSoFar;
    }

    // Helper method for humidity comparison
    private CSVRecord getLowerHumidityOfTwo(CSVRecord currentRow, CSVRecord lowestSoFar) {
        String currentHumidityStr = currentRow.get("Humidity");

        // Ignore rows with N/A humidity
        if (currentHumidityStr.equals("N/A")) {
            return lowestSoFar;
        }

        double currentHumidity = Double.parseDouble(currentHumidityStr);

        if (lowestSoFar == null) {
            return currentRow;
        }

        double lowestHumidity = Double.parseDouble(lowestSoFar.get("Humidity"));
        if (currentHumidity < lowestHumidity) {
            return currentRow;
        } else {
            return lowestSoFar;
        }
    }

    // Tester for lowestHumidityInFile
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();  // choose one file manually
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestHumidityRecord = lowestHumidityInFile(parser);

        System.out.println("Lowest Humidity was " + lowestHumidityRecord.get("Humidity")
                + " at " + lowestHumidityRecord.get("DateUTC"));
    }
    
    // 4. Find the record with the lowest humidity across multiple files
    public CSVRecord lowestHumidityInManyFiles() {
        CSVRecord lowestSoFar = null;

        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentLowest = lowestHumidityInFile(fr.getCSVParser());

            // Use our helper method to compare and keep the lowest
            lowestSoFar = getLowerHumidityOfTwo(currentLowest, lowestSoFar);
        }

        return lowestSoFar;
    }

    // Tester for lowestHumidityInManyFiles
    public void testLowestHumidityInManyFiles() {
        CSVRecord lowestHumidityRecord = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + lowestHumidityRecord.get("Humidity")
                + " at " + lowestHumidityRecord.get("DateUTC"));
    }
    
    // 5. Compute average temperature in a file
    public double averageTemperatureInFile(CSVParser parser) {
        double totalTemp = 0.0;
        int count = 0;

        for (CSVRecord currentRow : parser) {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));

            // Ignore bogus readings (-9999)
            if (currentTemp == -9999) {
                continue;
            }

            totalTemp += currentTemp;
            count++;
        }

        if (count == 0) {
            return 0.0;  // Avoid division by zero
        }

        return totalTemp / count;
    }

    // Tester for averageTemperatureInFile
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();  // choose one file manually
        CSVParser parser = fr.getCSVParser();
        double averageTemp = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is " + averageTemp);
    }
    
    // 6. Compute average temperature with a humidity filter
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        double totalTemp = 0.0;
        int count = 0;

        for (CSVRecord currentRow : parser) {
            String humidityStr = currentRow.get("Humidity");

            // Skip N/A humidity values
            if (humidityStr.equals("N/A")) {
                continue;
            }

            double currentHumidity = Double.parseDouble(humidityStr);
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));

            // Skip bogus temperature readings (-9999)
            if (currentTemp == -9999) {
                continue;
            }

            if (currentHumidity >= value) {
                totalTemp += currentTemp;
                count++;
            }
        }

        if (count == 0) {
            return Double.NaN;  // Special value to indicate no valid temperatures
        }

        return totalTemp / count;
    }

    // Tester for averageTemperatureWithHighHumidityInFile
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource(); // choose one file manually
        CSVParser parser = fr.getCSVParser();

        int humidityThreshold = 80;  // can be changed if needed
        double averageTemp = averageTemperatureWithHighHumidityInFile(parser, humidityThreshold);

        if (Double.isNaN(averageTemp)) {
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidity is " + averageTemp);
        }
    }
}