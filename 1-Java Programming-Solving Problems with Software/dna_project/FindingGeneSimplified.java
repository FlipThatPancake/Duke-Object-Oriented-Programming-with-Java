
/**
 * Write a description of FindingGeneSimplified here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FindingGeneSimplified {
    // Part 1: Finding a Gene - Using the Simplified Algorithm

    public String findSimpleGene(String dna) {
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) {
            return "";
        }

        int stopIndex = dna.indexOf("TAA", startIndex + 3);
        if (stopIndex == -1) {
            return "";
        }

        if ((stopIndex - startIndex) % 3 == 0) {
            return dna.substring(startIndex, stopIndex + 3);
        } else {
            return "";
        }
    }

    public void testSimpleGene() {
        // DNA with no "ATG"
        String dna1 = "AGTACGTAA";
        System.out.println("DNA string: " + dna1);
        String gene1 = findSimpleGene(dna1);
        System.out.println("Gene: " + gene1);

        // DNA with no "TAA"
        String dna2 = "ATGCCGTAG";
        System.out.println("DNA string: " + dna2);
        String gene2 = findSimpleGene(dna2);
        System.out.println("Gene: " + gene2);

        // DNA with no "ATG" or "TAA"
        String dna3 = "GTACGTACG";
        System.out.println("DNA string: " + dna3);
        String gene3 = findSimpleGene(dna3);
        System.out.println("Gene: " + gene3);

        // DNA with ATG, TAA, and a gene (length multiple of 3)
        String dna4 = "AATGCGTTAATCG";
        System.out.println("DNA string: " + dna4);
        String gene4 = findSimpleGene(dna4);
        System.out.println("Gene: " + gene4);

        // DNA with ATG, TAA, but not a gene (length not a multiple of 3)
        String dna5 = "AATGCGTTAAG";
        System.out.println("DNA string: " + dna5);
        String gene5 = findSimpleGene(dna5);
        System.out.println("Gene: " + gene5);
    }
}
