// ============================================
// FILE 1: WordLengths.java
// Save this as "WordLengths.java"
// ============================================

/**
 * Assignment 1: Word Lengths
 * This class counts the lengths of words in text
 */
public class WordLengths {
    
    /**
     * Counts the length of words in a file and stores in counts array
     * @param content - the text content to analyze
     * @param counts - array to store word length counts
     */
    public void countWordLengths(String content, int[] counts) {
        // split() breaks the string into an array of words
        // \\s+ means "one or more whitespace characters" (spaces, tabs, newlines)
        // so this splits on any whitespace
        String[] words = content.split("\\s+");
        
        // for-each loop: goes through every word in the words array
        // String word is a temporary variable that holds each word one at a time
        for (String word : words) {
            // if the word is empty (like if there were extra spaces), skip it
            if (word.isEmpty()) continue;  // continue = jump to next iteration of loop
            
            // call our helper method to figure out the real length
            // (excluding punctuation at start/end)
            int length = getWordLength(word);
            
            // Group words of length 30+ together at index 30
            // if length is bigger than our array, put it in the last spot
            if (length >= counts.length) {
                // counts.length is the size of the array (31 in our case)
                // counts.length - 1 is the last index (30)
                // ++ means add 1 to whatever's there
                counts[counts.length - 1]++;
            } else if (length > 0) {  // if length is valid (greater than 0)
                // increment the count at that length's index
                // so if length is 3, we do counts[3]++
                counts[length]++;
            }
        }
    }
    
    /**
     * Helper method to get the actual word length excluding non-letter punctuation
     * at the beginning or end
     */
    private int getWordLength(String word) {
        // start at the beginning of the word (index 0)
        int start = 0;
        // end at the last character (length - 1 because arrays start at 0)
        int end = word.length() - 1;
        
        // Find first letter
        // while we haven't passed the end AND the current character isn't a letter
        while (start <= end && !Character.isLetter(word.charAt(start))) {
            // word.charAt(start) gets the character at position start
            // Character.isLetter() returns true if it's a letter, false otherwise
            // ! means NOT, so !Character.isLetter means "is NOT a letter"
            start++;  // move forward one position
        }
        
        // Find last letter (working backwards)
        // while we haven't passed the start AND the current character isn't a letter
        while (end >= start && !Character.isLetter(word.charAt(end))) {
            end--;  // move backward one position
        }
        
        // Return length from first to last letter (inclusive)
        // if end >= start, we found letters, so calculate distance
        // (end - start + 1) gives us the number of characters between them (inclusive)
        // otherwise return 0 (no letters found)
        // ? : is the ternary operator (shorthand if-else)
        // condition ? value_if_true : value_if_false
        return (end >= start) ? (end - start + 1) : 0;
    }
    
    /**
     * Returns the index of the maximum value in the array
     */
    public int indexOfMax(int[] values) {
        // assume the first element is the biggest (start at index 0)
        int maxIndex = 0;
        
        // loop through the rest of the array starting at index 1
        for (int i = 1; i < values.length; i++) {
            // if the current element is bigger than our current max
            if (values[i] > values[maxIndex]) {
                // update maxIndex to point to this new bigger value
                maxIndex = i;
            }
        }
        // return the index where we found the biggest value
        return maxIndex;
    }
    
    /**
     * TEST METHOD for Word Lengths
     * RIGHT-CLICK this method in BlueJ to run it!
     * Edit the 'content' variable to test with different input
     */
    public void testCountWordLengths() {
        // EDIT THIS to test with different content
        // \n means newline character (like pressing Enter)
        // + concatenates (joins) strings together
        String content = "Laer. My necessaries are embark'd. Farewell.\n" +
                        "And, sister, as the winds give benefit";
        
        // println prints a line and then moves to the next line
        System.out.println("=== WORD LENGTHS TEST ===");
        System.out.println("Input text:\n" + content);
        System.out.println("\n--- Analysis ---");
        
        // create an array of 31 integers (indices 0-30), all start at 0
        int[] counts = new int[31];
        
        // call our method to fill up the counts array
        countWordLengths(content, counts);
        
        // Print word count for each length
        // regular for loop: start at 0, go while i < counts.length, increment i each time
        for (int i = 0; i < counts.length; i++) {
            // only print if there are words of this length (count > 0)
            if (counts[i] > 0) {
                // print the length, then the count
                System.out.println(i + " letters: " + counts[i] + " words");
            }
        }
        
        // find which length had the most words
        int mostCommonLength = indexOfMax(counts);
        // print it out along with how many words had that length
        System.out.println("\nMost common word length: " + mostCommonLength + 
                          " (" + counts[mostCommonLength] + " words)");
    }
}
