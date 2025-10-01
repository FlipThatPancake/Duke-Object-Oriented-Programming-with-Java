// ====================================================================
// ====================================================================
// FILE 2: CaesarCipher.java
// Copy ONLY the code between the START and END markers below
// ====================================================================
// ====================================================================

// -------- START COPYING HERE FOR CaesarCipher.java --------

/**
 * Assignment 2: Caesar Cipher encryption
 * This class encrypts messages using Caesar Cipher (with one or two keys)
 */
public class CaesarCipher {
    
    // private means only this class can see it
    // String is text, this is our alphabet for reference
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * Encrypts a message using a single key
     */
    public String encrypt(String input, int key) {
        // StringBuilder is like String but you can modify it (more efficient for building strings)
        StringBuilder encrypted = new StringBuilder();
        
        // get uppercase and lowercase versions of alphabet
        String alphabetUpper = alphabet.toUpperCase();  // makes it uppercase (already is, but ok)
        String alphabetLower = alphabet.toLowerCase();  // makes it lowercase
        
        // for-each loop through every character in the input string
        // input.toCharArray() converts the string into an array of characters
        for (char c : input.toCharArray()) {
            // check if the character is uppercase
            if (Character.isUpperCase(c)) {
                // find where this letter is in the alphabet (A=0, B=1, etc.)
                int idx = alphabetUpper.indexOf(c);
                // shift it by the key amount, % 26 wraps around (so Z+1 = A)
                int newIdx = (idx + key) % 26;
                // add the new encrypted character to our result
                encrypted.append(alphabetUpper.charAt(newIdx));
            } else if (Character.isLowerCase(c)) {  // if it's lowercase
                // same process as uppercase but with lowercase alphabet
                int idx = alphabetLower.indexOf(c);
                int newIdx = (idx + key) % 26;
                encrypted.append(alphabetLower.charAt(newIdx));
            } else {  // if it's not a letter (space, punctuation, number)
                // just add it unchanged
                encrypted.append(c);
            }
        }
        // convert StringBuilder back to a regular String and return it
        return encrypted.toString();
    }
    
    /**
     * Encrypts a message using two keys alternately
     */
    public String encryptTwoKeys(String input, int key1, int key2) {
        // same setup as before
        StringBuilder encrypted = new StringBuilder();
        String alphabetUpper = alphabet.toUpperCase();
        String alphabetLower = alphabet.toLowerCase();
        
        // regular for loop so we can track position (i)
        for (int i = 0; i < input.length(); i++) {
            // get the character at position i
            char c = input.charAt(i);
            
            // decide which key to use based on position
            // i % 2 gives remainder when dividing by 2
            // if i is 0, 2, 4, 6... (even) then i % 2 == 0, use key1
            // if i is 1, 3, 5, 7... (odd) then i % 2 == 1, use key2
            // ternary operator: condition ? value_if_true : value_if_false
            int key = (i % 2 == 0) ? key1 : key2;
            
            // same encryption logic as before, but using the selected key
            if (Character.isUpperCase(c)) {
                int idx = alphabetUpper.indexOf(c);
                int newIdx = (idx + key) % 26;
                encrypted.append(alphabetUpper.charAt(newIdx));
            } else if (Character.isLowerCase(c)) {
                int idx = alphabetLower.indexOf(c);
                int newIdx = (idx + key) % 26;
                encrypted.append(alphabetLower.charAt(newIdx));
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }
}

// -------- STOP COPYING HERE FOR CaesarCipher.java --------