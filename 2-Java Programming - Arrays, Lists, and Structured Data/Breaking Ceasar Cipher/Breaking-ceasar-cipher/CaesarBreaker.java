// ====================================================================
// ====================================================================
// FILE 3: CaesarBreaker.java
// Copy ONLY the code between the START and END markers below
// IMPORTANT: Make sure CaesarCipher.java is in the same folder!
// ====================================================================
// ====================================================================

// -------- START COPYING HERE FOR CaesarBreaker.java --------

/**
 * Assignment 2: Caesar Cipher breaking (decryption)
 * This class figures out the encryption keys and decrypts messages
 * REQUIRES: CaesarCipher.java must be in the same folder!
 */
public class CaesarBreaker {
    
    /**
     * Counts the frequency of each letter in the string
     */
    public int[] countLetters(String message) {
        // array of 26 integers (one for each letter a-z)
        int[] counts = new int[26];
        
        // lowercase alphabet for reference
        String alphabetLower = "abcdefghijklmnopqrstuvwxyz";
        
        // go through each character in the message
        for (char c : message.toCharArray()) {
            // convert to lowercase (so A and a both count as 'a')
            char lowerC = Character.toLowerCase(c);
            
            // find where this letter is in the alphabet
            // indexOf returns -1 if not found (like if it's a space or number)
            int idx = alphabetLower.indexOf(lowerC);
            
            // if we found it (idx != -1 means it's a letter)
            if (idx != -1) {
                // increment the count for that letter
                counts[idx]++;
            }
        }
        // return the array of letter counts
        return counts;
    }
    
    /**
     * Returns the index of the maximum value in the array
     */
    public int maxIndex(int[] values) {
        // same as indexOfMax from before
        int maxIdx = 0;  // assume first element is biggest
        
        // check the rest
        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[maxIdx]) {
                maxIdx = i;  // found a new max
            }
        }
        return maxIdx;
    }
    
    /**
     * Returns every other character from message starting at start position
     */
    public String halfOfString(String message, int start) {
        // StringBuilder to build our result string
        StringBuilder result = new StringBuilder();
        
        // start at 'start' position, go until end of string
        // i += 2 means jump by 2 each time (so we skip every other character)
        for (int i = start; i < message.length(); i += 2) {
            // add this character to our result
            result.append(message.charAt(i));
        }
        // convert to string and return
        return result.toString();
    }
    
    /**
     * Determines the key used to encrypt a string
     */
    public int getKey(String s) {
        // count how often each letter appears
        int[] freqs = countLetters(s);
        
        // find which letter appears most often
        int maxIdx = maxIndex(freqs);
        
        // In English, 'e' is the most common letter
        // 'e' is at index 4 in the alphabet (a=0, b=1, c=2, d=3, e=4)
        // so the key is the distance from maxIdx to 4
        int key = maxIdx - 4;
        
        // if key is negative, we need to wrap around
        // (like if maxIdx is 2, key would be -2, but we want 24)
        if (key < 0) {
            key = 26 + key;  // add 26 to make it positive
        }
        return key;
    }
    
    /**
     * Decrypts a message encrypted with one key
     */
    public String decrypt(String encrypted) {
        // figure out what key was used
        int key = getKey(encrypted);
        
        // create a CaesarCipher object to use its encrypt method
        CaesarCipher cc = new CaesarCipher();
        
        // to decrypt, we encrypt with the opposite key
        // if encrypted with key 3, decrypt with key 23 (26 - 3 = 23)
        String message = cc.encrypt(encrypted, 26 - key);
        
        return message;
    }
    
    /**
     * Decrypts a message encrypted with two keys
     */
    public String decryptTwoKeys(String encrypted) {
        // Split into two halves
        // half1 gets characters at positions 0, 2, 4, 6... (encrypted with key1)
        String half1 = halfOfString(encrypted, 0);
        // half2 gets characters at positions 1, 3, 5, 7... (encrypted with key2)
        String half2 = halfOfString(encrypted, 1);
        
        // Get keys for each half
        // figure out what key was used for the odd positions
        int key1 = getKey(half1);
        // figure out what key was used for the even positions
        int key2 = getKey(half2);
        
        // print the keys we found (for debugging/checking)
        System.out.println("Key1: " + key1);
        System.out.println("Key2: " + key2);
        
        // Decrypt using two keys
        // create cipher object
        CaesarCipher cc = new CaesarCipher();
        // decrypt by encrypting with opposite keys (26 - key)
        String decrypted = cc.encryptTwoKeys(encrypted, 26 - key1, 26 - key2);
        
        return decrypted;
    }
    
    /**
     * TEST METHOD for single key decryption
     * RIGHT-CLICK this method in BlueJ to run it!
     * Edit the 'original' variable to test with different input
     */
    public void testDecrypt() {
        // EDIT THIS to test with different encrypted text
        // this is our original message that we'll encrypt and then try to decrypt
        String original = "Just a test string with lots of eeeeeeeeeeeeeeeees";
        
        System.out.println("=== SINGLE KEY DECRYPT TEST ===");
        System.out.println("Original: " + original);
        
        // Encrypt with key 15
        // create a cipher object
        CaesarCipher cc = new CaesarCipher();
        // encrypt the original message with key 15
        String encrypted = cc.encrypt(original, 15);
        System.out.println("Encrypted (key=15): " + encrypted);
        
        // Decrypt
        // try to decrypt it (should figure out the key and get back original)
        String decrypted = decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);
        
        // check if it worked
        // .equals() compares two strings to see if they're the same
        System.out.println("Match: " + original.equals(decrypted));
    }
    
    /**
     * TEST METHOD for two key decryption
     * RIGHT-CLICK this method in BlueJ to run it!
     * Edit the 'original' variable to test with different input
     */
    public void testDecryptTwoKeys() {
        // EDIT THIS to test with different encrypted text
        String original = "Just a test string with lots of eeeeeeeeeeeeeeeees";
        
        System.out.println("\n=== TWO KEY DECRYPT TEST ===");
        System.out.println("Original: " + original);
        
        // Encrypt with keys 23 and 2
        CaesarCipher cc = new CaesarCipher();
        // encrypt with two different keys alternating
        String encrypted = cc.encryptTwoKeys(original, 23, 2);
        System.out.println("Encrypted (key1=23, key2=2): " + encrypted);
        
        // Decrypt
        // try to figure out both keys and decrypt
        String decrypted = decryptTwoKeys(encrypted);
        System.out.println("Decrypted: " + decrypted);
        
        // check if it worked
        System.out.println("Match: " + original.equals(decrypted));
    }
    
    /**
     * TEST METHOD for halfOfString
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void testHalfOfString() {
        System.out.println("\n=== HALF OF STRING TEST ===");
        
        // EDIT THIS to test with different input
        String testString = "Qbkm Zgis";
        
        // print the original string
        System.out.println("Input: \"" + testString + "\"");
        
        // get every other character starting at position 0 (positions 0, 2, 4...)
        System.out.println("halfOfString(input, 0): \"" + halfOfString(testString, 0) + "\"");
        
        // get every other character starting at position 1 (positions 1, 3, 5...)
        System.out.println("halfOfString(input, 1): \"" + halfOfString(testString, 1) + "\"");
    }
    
    /**
     * RUN ALL TESTS - Run this to test everything at once!
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void runAllTests() {
        testHalfOfString();
        System.out.println("\n" + "=".repeat(50) + "\n");
        testDecrypt();
        System.out.println("\n" + "=".repeat(50) + "\n");
        testDecryptTwoKeys();
    }
}

// -------- STOP COPYING HERE FOR CaesarBreaker.java --------