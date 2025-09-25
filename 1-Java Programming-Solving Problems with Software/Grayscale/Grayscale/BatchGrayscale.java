import edu.duke.*;      // import the edu.duke library (has ImageResource, Pixel, DirectoryResource)
import java.io.File;    // import File class so we can work with file names

public class BatchGrayscale {
    
    // method that takes one image and returns a new image that is grayscale
    public ImageResource makeGray(ImageResource inImage) {
        // create empty image with same size (width x height)
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        
        // go through every pixel of the input image
        for (Pixel pixel : inImage.pixels()) {
            // get the "matching" pixel in the new (empty) image
            Pixel outPixel = outImage.getPixel(pixel.getX(), pixel.getY());
            
            // calculate average of RGB (this is formula for gray)
            // pixel.getRed() gives int between 0-255
            // add them and divide by 3 (average)
            int average = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
            
            // now set ALL colors of output pixel to same number (gray means R=G=B)
            outPixel.setRed(average);
            outPixel.setGreen(average);
            outPixel.setBlue(average);
        }
        
        // return finished grayscale image
        return outImage;
    }
    
    // method that does batch process (multiple images)
    public void selectAndConvert() {
        // lets user choose multiple files in a popup window
        DirectoryResource dr = new DirectoryResource();
        
        // loop through every file user selected
        for (File f : dr.selectedFiles()) {
            // turn the file into an ImageResource object
            ImageResource ir = new ImageResource(f);
            
            // call makeGray (our function above) to make new gray image
            ImageResource gray = makeGray(ir);
            
            // build new filename: put "gray-" in front of original file name
            String newName = "gray-" + f.getName();
            
            // tell our gray image to use that new filename
            gray.setFileName(newName);
            
            // THIS line actually saves the file on disk !!
            gray.save();
            
            // optional: draw shows image in a window so we can see result
            gray.draw();
        }
    }
    
    // main method - program starts here if you run it directly
    public static void main(String[] args) {
        // create object of this class so we can call its methods
        BatchGrayscale bg = new BatchGrayscale();
        
        // call selectAndConvert to do everything
        bg.selectAndConvert();
    }
}