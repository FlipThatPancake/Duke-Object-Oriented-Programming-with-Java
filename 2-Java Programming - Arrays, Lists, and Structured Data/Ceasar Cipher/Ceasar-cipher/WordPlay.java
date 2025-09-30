// WordPlay.java
// This program defines methods for basic character and string manipulation.

public class WordPlay {

    // ----------------------------------------------------------------------
    // Assignment 1, Method 1: isVowel(ch)
    // ----------------------------------------------------------------------

    /**
     * Checks if a given character is a vowel (A, E, I, O, U), regardless of case.
     * @param ch The char to check.
     * @return boolean: true if it's a vowel, false otherwise.
     */
    public boolean isVowel(char ch) {
        // NOTE: The 'char' data type in Java holds a single 16-bit Unicode character.

        // Step 1: Convert to lowercase.
        // We use the static method Character.toLowerCase(ch). This simplifies the check
        // because we only have to look for five lowercase letters ('a' through 'u').
        char lowerCh = Character.toLowerCase(ch);

        // Step 2: Check for membership.
        // The '||' operator is the logical OR. The expression is true if ANY of the
        // conditions separated by '||' are true.
        if (lowerCh == 'a' || lowerCh == 'e' || lowerCh == 'i' ||
            lowerCh == 'o' || lowerCh == 'u') {
            return true;
        } else {
            return false;
        }
    }

    // --- TESTER METHODS for isVowel ---

    /** TESTER METHOD: isVowel - Case 1 (Lowercase Vowel) */
    public void testIsVowel_LowerCase() {
        char testChar = 'i';
        boolean expected = true;
        boolean result = isVowel(testChar);

        // HOW ASSERT WORKS:
        // The 'assert' keyword is a Java statement for debugging and testing.
        // Syntax: assert <boolean_expression> : <message_if_false>;
        // If the <boolean_expression> (result == expected) is FALSE, the program
        // throws an AssertionError and prints the message.
        // It's typically DISABLED in production, but we enable it for testing (using -ea flag).
        assert result == expected : "Test Failed: Input " + testChar + ", Expected " + expected + ", Got " + result;
        System.out.println("✅ isVowel('"+ testChar + "') returned " + result + ". (Expected: " + expected + ")");
    }
    
    /** TESTER METHOD: isVowel - Case 2 (Uppercase Consonant) */
    public void testIsVowel_UpperCaseConsonant() {
        char testChar = 'P';
        boolean expected = false;
        boolean result = isVowel(testChar);

        assert result == expected : "Test Failed: Input " + testChar + ", Expected " + expected + ", Got " + result;
        System.out.println("✅ isVowel('"+ testChar + "') returned " + result + ". (Expected: " + expected + ")");
    }

    // ----------------------------------------------------------------------
    // Assignment 1, Method 2: replaceVowels(phrase, ch)
    // ----------------------------------------------------------------------

    /**
     * Replaces all vowels in a string (case-insensitive) with a specified character.
     * @param phrase The input String.
     * @param ch The replacement char.
     * @return A new String with replaced vowels.
     */
    public String replaceVowels(String phrase, char ch) {
        // WHAT IS A StringBuilder?
        // Java Strings are IMMUTABLE, meaning once created, they cannot be changed.
        // If we modify a String many times (like replacing characters), Java has to
        // create a *new* String object every time, which is slow.
        // StringBuilder is a MUTABLE class, designed to build and modify strings efficiently
        // without creating a new object for every change.

        // 1. Initialize the StringBuilder with the input phrase.
        StringBuilder newPhrase = new StringBuilder(phrase);

        // 2. Loop through the entire sequence of characters.
        for (int i = 0; i < newPhrase.length(); i++) {
            char currentChar = newPhrase.charAt(i);
            
            // 3. Check if the character is a vowel using our helper method.
            if (isVowel(currentChar)) {
                // 4. If it's a vowel, replace it in the StringBuilder.
                // setCharAt(index, newChar) is the mutation method.
                newPhrase.setCharAt(i, ch);
            }
        }
        
        // 5. Convert the mutable StringBuilder back to an immutable String for return.
        return newPhrase.toString();
    }

    // --- TESTER METHODS for replaceVowels ---

    /** TESTER METHOD: replaceVowels - Case 1 (Assignment Example) */
    public void testReplaceVowels_AssignmentExample() {
        String phrase = "Hello World";
        char replacement = '*';
        String expected = "H*ll* W*rld";
        String result = replaceVowels(phrase, replacement);
        
        assert result.equals(expected) : "Test Failed: Input '" + phrase + "', Expected '" + expected + "', Got '" + result + "'";
        System.out.println("✅ replaceVowels('" + phrase + "', '"+ replacement + "') returned '" + result + "'. (Expected: " + expected + ")");
    }
    
    /** TESTER METHOD: replaceVowels - Case 2 (No Vowels) */
    public void testReplaceVowels_NoVowels() {
        String phrase = "Rhythm";
        char replacement = 'x';
        String expected = "Rhythm";
        String result = replaceVowels(phrase, replacement);
        
        assert result.equals(expected) : "Test Failed: Input '" + phrase + "', Expected '" + expected + "', Got '" + result + "'";
        System.out.println("✅ replaceVowels('" + phrase + "', '"+ replacement + "') returned '" + result + "'. (Expected: " + expected + ")");
    }


    // ----------------------------------------------------------------------
    // Assignment 1, Method 3: emphasize(phrase, ch)
    // ----------------------------------------------------------------------

    /**
     * Emphasizes a target character 'ch' by alternating replacements:
     * '*' for ODD location (1st, 3rd, 5th...)
     * '+' for EVEN location (2nd, 4th, 6th...).
     * @param phrase The input String.
     * @param ch The target char to emphasize.
     * @return A new String with emphasized characters.
     */
    public String emphasize(String phrase, char ch) {
        StringBuilder newPhrase = new StringBuilder(phrase);
        
        for (int i = 0; i < newPhrase.length(); i++) {
            char currentChar = newPhrase.charAt(i);
            
            // 1. Check if the current character matches the target (case-insensitive).
            if (Character.toLowerCase(currentChar) == Character.toLowerCase(ch)) {
                
                // 2. Determine the location (1-based position) and check for parity.
                //
                // **IMPORTANT NOTE ON INDEX vs. LOCATION:**
                // - Programming Index (i) is 0-based (0, 1, 2, 3, 4, ...).
                // - Assignment Location is 1-based (1st, 2nd, 3rd, 4th, 5th, ...).
                //
                // Location = i + 1
                //
                // If the Location is ODD (1st, 3rd, 5th...), the Index is EVEN (0, 2, 4...).
                // If the Location is EVEN (2nd, 4th, 6th...), the Index is ODD (1, 3, 5...).
                
                // We can check the Location (i+1) for oddness.
                int location = i + 1;
                
                // Check for ODD Location: (location % 2 == 1)
                if (location % 2 == 1) { 
                    // This happens at Index 0, 2, 4, ...
                    newPhrase.setCharAt(i, '*');
                } else {
                    // This happens at Index 1, 3, 5, ...
                    // Since it's not ODD, it must be EVEN Location.
                    newPhrase.setCharAt(i, '+');
                }
            }
        }
        
        return newPhrase.toString();
    }

    // --- TESTER METHODS for emphasize ---

    /** TESTER METHOD: emphasize - Case 1 (Odd/Even Locations) */
    public void testEmphasize_AssignmentExample1() {
        String phrase = "dna ctgaaactga";
        char target = 'a';
        String expected = "dn* ctg+*+ctg+";
        String result = emphasize(phrase, target);
        
        assert result.equals(expected) : "Test Failed: Input '" + phrase + "', Expected '" + expected + "', Got '" + result + "'";
        System.out.println("✅ emphasize('" + phrase + "', '"+ target + "') returned '" + result + "'. (Expected: " + expected + ")");
    }
    
    /** TESTER METHOD: emphasize - Case 2 (Mixed Case Target) */
    public void testEmphasize_AssignmentExample2() {
        String phrase = "Mary Bella Abracadabra";
        char target = 'a';
        String expected = "M+ry Bell+ +br*c*d*br+";
        String result = emphasize(phrase, target);
        
        assert result.equals(expected) : "Test Failed: Input '" + phrase + "', Expected '" + expected + "', Got '" + result + "'";
        System.out.println("✅ emphasize('" + phrase + "', '"+ target + "') returned '" + result + "'. (Expected: " + expected + ")");
    }

    // Main method to run all tests.
    public static void main(String[] args) {
        // REMEMBER: Run Java with the -ea flag (java -ea WordPlay) to enable assertions!
        WordPlay tester = new WordPlay();
        
        System.out.println("\n--- Running WordPlay Tests ---");
        tester.testIsVowel_LowerCase();
        tester.testIsVowel_UpperCaseConsonant();
        tester.testReplaceVowels_AssignmentExample();
        tester.testReplaceVowels_NoVowels();
        tester.testEmphasize_AssignmentExample1();
        tester.testEmphasize_AssignmentExample2();
        System.out.println("--- All WordPlay Tests Complete ---\n");
    }
}