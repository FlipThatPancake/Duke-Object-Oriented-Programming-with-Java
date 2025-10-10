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
     * @param encrypted the encrypted message
     * @param key the key that was used to encrypt
     * @return the decrypted message
     */
    public String decrypt(String encrypted, int key) {
        // create a CaesarCipher object to use its encrypt method
        CaesarCipher cc = new CaesarCipher();
        
        // to decrypt, we encrypt with the opposite key
        // if encrypted with key 3, decrypt with key 23 (26 - 3 = 23)
        String message = cc.encrypt(encrypted, 26 - key);
        
        return message;
    }
    
    /**
     * Decrypts a message encrypted with two keys
     * @param encrypted the encrypted message
     * @param key1 the key used for positions 0, 2, 4, 6...
     * @param key2 the key used for positions 1, 3, 5, 7...
     * @return the decrypted message
     */
    public String decryptTwoKeys(String encrypted, int key1, int key2) {
        // create cipher object
        CaesarCipher cc = new CaesarCipher();
        
        // decrypt by encrypting with opposite keys (26 - key)
        String decrypted = cc.encryptTwoKeys(encrypted, 26 - key1, 26 - key2);
        
        return decrypted;
    }
    
    /**
     * AUTO-DETECTS key and decrypts a message encrypted with one key
     * @param encrypted the encrypted message
     * @return the decrypted message
     */
    public String decryptAutoDetect(String encrypted) {
        // figure out what key was used
        int key = getKey(encrypted);
        System.out.println("Detected key: " + key);
        
        // decrypt using the detected key
        return decrypt(encrypted, key);
    }
    
    /**
     * AUTO-DETECTS keys and decrypts a message encrypted with two keys
     * @param encrypted the encrypted message
     * @return the decrypted message
     */
    public String decryptTwoKeysAutoDetect(String encrypted) {
        // Split into two halves
        String half1 = halfOfString(encrypted, 0);
        String half2 = halfOfString(encrypted, 1);
        
        // Get keys for each half
        int key1 = getKey(half1);
        int key2 = getKey(half2);
        
        System.out.println("Detected Key1: " + key1);
        System.out.println("Detected Key2: " + key2);
        
        // decrypt using the detected keys
        return decryptTwoKeys(encrypted, key1, key2);
    }
    
    /**
     * TEST: Encrypt with a single key
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void testEncryptOneKey() {
        // EDIT THESE to test with different values
        String message = "Just a test string with lots of eeeeeeeeeeeeeeeees";
        int key = 15;
        
        System.out.println("=== ENCRYPT WITH ONE KEY ===");
        System.out.println("Original: " + message);
        System.out.println("Key: " + key);
        
        CaesarCipher cc = new CaesarCipher();
        String encrypted = cc.encrypt(message, key);
        
        System.out.println("Encrypted: " + encrypted);
    }
    
    /**
     * TEST: Decrypt with a single key (you provide the key)
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void testDecryptOneKey() {
        // EDIT THESE - paste your encrypted string and provide the key
        String encrypted = "Yjhi p ithi higuydv lxiw adih du ttttttttttttttttth";
        int key = 15;
        
        System.out.println("=== DECRYPT WITH ONE KEY ===");
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Key: " + key);
        
        String decrypted = decrypt(encrypted, key);
        
        System.out.println("Decrypted: " + decrypted);
    }
    
    /**
     * TEST: Encrypt with two keys
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void testEncryptTwoKeys() {
        // EDIT THESE to test with different values
        String message = "Top ncmy qkff vi vguv vbg ycpx";
        int key1 = 23;
        int key2 = 2;
        
        System.out.println("=== ENCRYPT WITH TWO KEYS ===");
        System.out.println("Original: " + message);
        System.out.println("Key1: " + key1);
        System.out.println("Key2: " + key2);
        
        CaesarCipher cc = new CaesarCipher();
        String encrypted = cc.encryptTwoKeys(message, key1, key2);
        
        System.out.println("Encrypted: " + encrypted);
    }
    
    /**
     * TEST: Decrypt with two keys (you provide both keys)
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void testDecryptTwoKeys() {
        // EDIT THESE - paste your encrypted string and provide both keys
        String encrypted = "Aal oldy rsnv dk xiwx yjo bemz";
        int key1 = 23;
        int key2 = 2;
        
        System.out.println("=== DECRYPT WITH TWO KEYS ===");
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Key1: " + key1);
        System.out.println("Key2: " + key2);
        
        String decrypted = decryptTwoKeys(encrypted, key1, key2);
        
        System.out.println("Decrypted: " + decrypted);
    }
    
    /**
     * TEST: Auto-detect key and decrypt (one key)
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void testAutoDetectOneKey() {
        // EDIT THIS - paste your encrypted string
        String encrypted = "Yjhi p ithi higuydv lxiw adih du ttttttttttttttttth";
        
        System.out.println("=== AUTO-DETECT ONE KEY ===");
        System.out.println("Encrypted: " + encrypted);
        
        String decrypted = decryptAutoDetect(encrypted);
        
        System.out.println("Decrypted: " + decrypted);
    }
    
    /**
     * TEST: Auto-detect keys and decrypt (two keys)
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void testAutoDetectTwoKeys() {
        // EDIT THIS - paste your encrypted string
        String encrypted = "Aal oldy rsnv dk xiwx yjo bemz";
        
        System.out.println("=== AUTO-DETECT TWO KEYS ===");
        System.out.println("Encrypted: " + encrypted);
        
        String decrypted = decryptTwoKeysAutoDetect(encrypted);
        
        System.out.println("Decrypted: " + decrypted);
    }
    
    /**
     * TEST: halfOfString method
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void testHalfOfString() {
        // EDIT THIS to test with different input
        String testString = "Qbkm Zgis";
        
        System.out.println("=== HALF OF STRING TEST ===");
        System.out.println("Input: \"" + testString + "\"");
        System.out.println("halfOfString(input, 0): \"" + halfOfString(testString, 0) + "\"");
        System.out.println("halfOfString(input, 1): \"" + halfOfString(testString, 1) + "\"");
    }
    
    /**
     * TEST: Auto-detect two keys and decrypt from a file
     * Opens file explorer to select a .txt file
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void testAutoDetectTwoKeysFromFile() {
        System.out.println("=== AUTO-DETECT TWO KEYS FROM FILE ===");
        
        // Import necessary classes for file handling
        edu.duke.FileResource fr = new edu.duke.FileResource();
        
        // Read the entire file content as one string
        String encrypted = fr.asString();
        
        System.out.println("File loaded successfully!");
        System.out.println("Encrypted text length: " + encrypted.length() + " characters");
        System.out.println("\nFirst 5000 characters of encrypted text:");
        if (encrypted.length() > 5000) {
            System.out.println(encrypted.substring(0, 5000) + "...");
        } else {
            System.out.println(encrypted);
        }
        
        System.out.println("\nAttempting to decrypt...\n");
        
        // Use the auto-detect method to decrypt
        String decrypted = decryptTwoKeysAutoDetect(encrypted);
        
        System.out.println("\nDecrypted text:");
        System.out.println(decrypted);
        
        // Show first 200 characters if text is long
        if (decrypted.length() > 5000) {
            System.out.println("\n[Showing first 5000 characters only]");
            System.out.println(decrypted.substring(0, 5000) + "...");
        }
    }
    
    /**
     * RUN ALL TESTS - Run this to test everything at once!
     * RIGHT-CLICK this method in BlueJ to run it!
     */
    public void runAllTests() {
        testHalfOfString();
        System.out.println("\n" + "=".repeat(50) + "\n");
        testEncryptOneKey();
        System.out.println("\n" + "=".repeat(50) + "\n");
        testDecryptOneKey();
        System.out.println("\n" + "=".repeat(50) + "\n");
        testEncryptTwoKeys();
        System.out.println("\n" + "=".repeat(50) + "\n");
        testDecryptTwoKeys();
        System.out.println("\n" + "=".repeat(50) + "\n");
        testAutoDetectOneKey();
        System.out.println("\n" + "=".repeat(50) + "\n");
        testAutoDetectTwoKeys();
    }
}