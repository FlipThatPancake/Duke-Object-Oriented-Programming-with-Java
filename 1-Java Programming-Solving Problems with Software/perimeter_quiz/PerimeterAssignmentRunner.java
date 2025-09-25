import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        // Count the number of points in Shape s
        int count = 0;
        for (Point p : s.getPoints()) {
            count++; // Increase counter for each point
        }
        return count; // Return total number of points
    }

    public double getAverageLength(Shape s) {
        // Average length = total perimeter / number of points
        double perimeter = getPerimeter(s);
        int numPoints = getNumPoints(s);
        return perimeter / numPoints; // Return average as double
    }


    public double getLargestSide(Shape s) {
        // Track the largest side found so far
        double largestSide = 0.0;
        // Start with the last point so the loop covers the final side too
        Point prevPt = s.getLastPoint();
        // Loop through each point in the shape
        for (Point currPt : s.getPoints()) {
            double currDist = prevPt.distance(currPt);
            // If current distance is bigger than what we have, update largestSide
            if (currDist > largestSide) {
                largestSide = currDist;
            }
            prevPt = currPt;
        }
        return largestSide; // Return the biggest side length
    }
    
    public double getLargestX(Shape s) {
        // Track the largest X found so far
        double largestX = Double.NEGATIVE_INFINITY;
        // Loop through each point in the shape
        for (Point p : s.getPoints()) {
            double xVal = p.getX();
            // If this point’s X is bigger, update largestX
            if (xVal > largestX) {
                largestX = xVal;
            }
        }
        return largestX; // Return the largest X value
    }

    public double getLargestPerimeterMultipleFiles() {
        // Start with smallest possible perimeter
        double largestPerim = 0.0;
    
        // Create DirectoryResource so user can select multiple files
        DirectoryResource dr = new DirectoryResource();
    
        // Loop over each selected file
        for (File f : dr.selectedFiles()) {
            // Create FileResource from file
            FileResource fr = new FileResource(f);
            // Create Shape from FileResource
            Shape s = new Shape(fr);
            // Get this shape's perimeter
            double peri = getPerimeter(s);
            // If this is the largest so far, update largestPerim
            if (peri > largestPerim) {
                largestPerim = peri;
            }
        }
        return largestPerim;
    }

    public String getFileWithLargestPerimeter() {
        File temp = null; // Will hold file with largest perimeter
        double largestPerim = 0.0;
    
        DirectoryResource dr = new DirectoryResource();
    
        // Loop over each selected file
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double peri = getPerimeter(s);
            // Update largest perimeter and file if this one is bigger
            if (peri > largestPerim) {
                largestPerim = peri;
                temp = f;
            }
        }
        return temp.getName();
    }


    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
    
        // From Assignment 1
        int numPoints = getNumPoints(s);
        System.out.println("Number of points = " + numPoints);
    
        double avgLength = getAverageLength(s);
        System.out.println("Average side length = " + avgLength);
    
        // NEW: Call getLargestSide and print result
        double largestSide = getLargestSide(s);
        System.out.println("Largest side length = " + largestSide);
    
        // NEW: Call getLargestX and print result
        double largestX = getLargestX(s);
        System.out.println("Largest X value = " + largestX);
    }
    
    public void testPerimeterMultipleFiles() {
        // Call getLargestPerimeterMultipleFiles and store result
        double largestPerim = getLargestPerimeterMultipleFiles();
        System.out.println("Largest perimeter among selected files = " + largestPerim);
    }   

    public void testFileWithLargestPerimeter() {
        String fileName = getFileWithLargestPerimeter();
        System.out.println("File with largest perimeter = " + fileName);
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        pr.testPerimeter();
    }
}
