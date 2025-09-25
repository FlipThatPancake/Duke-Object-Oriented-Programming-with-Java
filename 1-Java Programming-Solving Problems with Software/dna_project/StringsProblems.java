
/**
 * Write a description of StringsProblems here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StringsProblems {
    // Part 3: Problem Solving with Strings

    public boolean twoOccurrences(String stringa, String stringb) {
        int firstIndex = stringb.indexOf(stringa);
        if (firstIndex == -1) {
            return false;
        }

        int secondIndex = stringb.indexOf(stringa, firstIndex + stringa.length());
        return secondIndex != -1;
    }

    public String lastPart(String stringa, String stringb) {
        int startIndex = stringb.indexOf(stringa);
        if (startIndex == -1) {
            return stringb;
        } else {
            return stringb.substring(startIndex + stringa.length());
        }
    }

    public void testing() {
        // Test twoOccurrences
        System.out.println("--- Testing twoOccurrences ---");
        String a1 = "by";
        String b1 = "A story by Abby Long";
        System.out.println("Does '" + a1 + "' occur at least twice in '" + b1 + "'? " + twoOccurrences(a1, b1));

        String a2 = "a";
        String b2 = "banana";
        System.out.println("Does '" + a2 + "' occur at least twice in '" + b2 + "'? " + twoOccurrences(a2, b2));

        String a3 = "atg";
        String b3 = "ctgtatgta";
        System.out.println("Does '" + a3 + "' occur at least twice in '" + b3 + "'? " + twoOccurrences(a3, b3));

        // Test lastPart
        System.out.println("\n--- Testing lastPart ---");
        String a4 = "an";
        String b4 = "banana";
        System.out.println("The part of the string after '" + a4 + "' in '" + b4 + "' is: " + lastPart(a4, b4));

        String a5 = "zoo";
        String b5 = "forest";
        System.out.println("The part of the string after '" + a5 + "' in '" + b5 + "' is: " + lastPart(a5, b5));
    }
}
