// CaesarCipher.java
// This program implements the Caesar Cipher with single and double keys.

import edu.duke.FileResource; 

public class CaesarCipher {

    // ----------------------------------------------------------------------
    // Method 1: encrypt(input, key)
    // ----------------------------------------------------------------------

    public String encrypt(String input, int key) {
        StringBuilder encrypted = new StringBuilder(input);
        
        final String ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";

        int shift = key % 26;
        
        String shiftedAlphabetUpper = ALPHABET_UPPER.substring(shift) + ALPHABET_UPPER.substring(0, shift);
        String shiftedAlphabetLower = ALPHABET_LOWER.substring(shift) + ALPHABET_LOWER.substring(0, shift);

        for (int i = 0; i < encrypted.length(); i++) {
            char currentChar = encrypted.charAt(i);

            if (Character.isUpperCase(currentChar)) {
                int idx = ALPHABET_UPPER.indexOf(currentChar);
                if (idx != -1) {
                    encrypted.setCharAt(i, shiftedAlphabetUpper.charAt(idx));
                }
            } 
            else if (Character.isLowerCase(currentChar)) {
                int idx = ALPHABET_LOWER.indexOf(currentChar);
                if (idx != -1) {
                    encrypted.setCharAt(i, shiftedAlphabetLower.charAt(idx));
                }
            }
        }
        return encrypted.toString();
    }

    // --- SIMPLIFIED TESTER METHOD for encrypt ---
    public void testEncrypt_UpperCase() {
        String phrase = "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        int key = 23;
        String result = encrypt(phrase, key);
        System.out.println("\n--- Running testEncrypt_UpperCase ---");
        System.out.println("Input:  " + phrase);
        System.out.println("Key:    " + key);
        System.out.println("Result: " + result);
    }

    // ----------------------------------------------------------------------
    // Method 2: encryptTwoKeys(input, key1, key2)
    // ----------------------------------------------------------------------

    public String encryptTwoKeys(String input, int key1, int key2) {
        StringBuilder encrypted = new StringBuilder(input);
        
        final String ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";

        int shift1 = key1 % 26;
        int shift2 = key2 % 26;
        
        String shiftedUpper1 = ALPHABET_UPPER.substring(shift1) + ALPHABET_UPPER.substring(0, shift1);
        String shiftedLower1 = ALPHABET_LOWER.substring(shift1) + ALPHABET_LOWER.substring(0, shift1);
        
        String shiftedUpper2 = ALPHABET_UPPER.substring(shift2) + ALPHABET_UPPER.substring(0, shift2);
        String shiftedLower2 = ALPHABET_LOWER.substring(shift2) + ALPHABET_LOWER.substring(0, shift2);

        for (int i = 0; i < encrypted.length(); i++) {
            char currentChar = encrypted.charAt(i);
            boolean useKey1 = (i % 2 == 0); 
            
            String shiftedAlphabetUpper = useKey1 ? shiftedUpper1 : shiftedUpper2;
            String shiftedAlphabetLower = useKey1 ? shiftedLower1 : shiftedLower2;
            String standardAlphabet = Character.isUpperCase(currentChar) ? ALPHABET_UPPER : ALPHABET_LOWER;

            if (Character.isLetter(currentChar)) {
                int idx = standardAlphabet.indexOf(currentChar);
                if (idx != -1) {
                    char newChar = Character.isUpperCase(currentChar) ? 
                                   shiftedAlphabetUpper.charAt(idx) : 
                                   shiftedAlphabetLower.charAt(idx);
                    encrypted.setCharAt(i, newChar);
                }
            }
        }

        return encrypted.toString();
    }
    
    // --- SIMPLIFIED TESTER METHOD for encryptTwoKeys ---
    public void testEncryptTwoKeys_AssignmentExample() {
        String phrase = "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        int key1 = 8;
        int key2 = 21;
        String result = encryptTwoKeys(phrase, key1, key2);
        System.out.println("\n--- Running testEncryptTwoKeys_AssignmentExample ---");
        System.out.println("Input:  " + phrase);
        System.out.println("Keys:   " + key1 + " & " + key2);
        System.out.println("Result: " + result);
    }
    
    // ----------------------------------------------------------------------
    // Method 3: testCaesar()
    // ----------------------------------------------------------------------

    public void testCaesar() {
        FileResource fr = new FileResource();
        String message = fr.asString();
        
        int key = 15;
        String encrypted = encrypt(message, key);
        
        System.out.println("\n--- Running testCaesar ---");
        System.out.println("Key: " + key);
        System.out.println("Encrypted Message:\n" + encrypted);
    }
    
    public void testTestCaesar() {
        System.out.println("\n--- Running testTestCaesar (File Read) ---");
        testCaesar(); 
    }

    // Main method
    public static void main(String[] args) {
        CaesarCipher tester = new CaesarCipher();
        
        tester.testEncrypt_UpperCase();
        tester.testEncryptTwoKeys_AssignmentExample();
        tester.testTestCaesar(); 
    }
}