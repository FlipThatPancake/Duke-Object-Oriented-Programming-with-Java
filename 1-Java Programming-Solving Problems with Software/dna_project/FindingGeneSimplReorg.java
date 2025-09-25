
/**
 * Write a description of FindingGeneSimplReorg here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FindingGeneSimplReorg {
    // Part 2: Finding a Gene - Using the Simplified Algorithm Reorganized

    public String findSimpleGene(String dna, String startCodon, String stopCodon) {
        // Convert the DNA string to uppercase to handle case-insensitivity
        String upperCaseDna = dna.toUpperCase();
        String upperCaseStartCodon = startCodon.toUpperCase();
        String upperCaseStopCodon = stopCodon.toUpperCase();

        int startIndex = upperCaseDna.indexOf(upperCaseStartCodon);
        if (startIndex == -1) {
            return "";
        }

        int stopIndex = upperCaseDna.indexOf(upperCaseStopCodon, startIndex + 3);
        if (stopIndex == -1) {
            return "";
        }

        if ((stopIndex - startIndex) % 3 == 0) {
            // Return the original-cased substring
            return dna.substring(startIndex, stopIndex + 3);
        } else {
            return "";
        }
    }

    public void testSimpleGene() {
        // DNA with no "ATG"
        String dna1 = "AGTACGTAA";
        System.out.println("DNA string: " + dna1);
        String gene1 = findSimpleGene(dna1, "ATG", "TAA");
        System.out.println("Gene: " + gene1);

        // DNA with no "TAA"
        String dna2 = "ATGCCGTAG";
        System.out.println("DNA string: " + dna2);
        String gene2 = findSimpleGene(dna2, "ATG", "TAA");
        System.out.println("Gene: " + gene2);

        // DNA with no "ATG" or "TAA"
        String dna3 = "GTACGTACG";
        System.out.println("DNA string: " + dna3);
        String gene3 = findSimpleGene(dna3, "ATG", "TAA");
        System.out.println("Gene: " + gene3);

        // DNA with ATG, TAA, and a gene (length multiple of 3)
        String dna4 = "AATGCGTTAATCG";
        System.out.println("DNA string: " + dna4);
        String gene4 = findSimpleGene(dna4, "ATG", "TAA");
        System.out.println("Gene: " + gene4);

        // DNA with ATG, TAA, but not a gene (length not a multiple of 3)
        String dna5 = "AATGCGTTAAG";
        System.out.println("DNA string: " + dna5);
        String gene5 = findSimpleGene(dna5, "ATG", "TAA");
        System.out.println("Gene: " + gene5);

        // DNA with lowercase letters and a gene
        String dna6 = "gatgctataat";
        System.out.println("DNA string: " + dna6);
        String gene6 = findSimpleGene(dna6, "atg", "taa");
        System.out.println("Gene: " + gene6);

        // DNA with uppercase letters and a gene
        String dna7 = "ATGGGTTAAGTC";
        System.out.println("DNA string: " + dna7);
        String gene7 = findSimpleGene(dna7, "ATG", "TAA");
        System.out.println("Gene: " + gene7);
    }
}
