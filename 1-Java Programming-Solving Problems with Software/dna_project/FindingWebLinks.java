
import edu.duke.URLResource;

/**
 * Write a description of FindingWebLinks here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FindingWebLinks {
    // Part 4: Finding Web Links

    public void findYoutubeLinks() {
        URLResource url = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for (String word : url.words()) {
            String lowerCaseWord = word.toLowerCase();
            if (lowerCaseWord.contains("youtube.com")) {
                int startIndex = word.lastIndexOf("\"", lowerCaseWord.indexOf("youtube.com"));
                int endIndex = word.indexOf("\"", lowerCaseWord.indexOf("youtube.com"));

                if (startIndex != -1 && endIndex != -1) {
                    System.out.println(word.substring(startIndex + 1, endIndex));
                }
            }
        }
    }
}
