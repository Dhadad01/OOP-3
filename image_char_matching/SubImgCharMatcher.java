package image_char_matching;

import java.util.*;
/**
 * A class for matching characters based on their brightness values.
 */
public class SubImgCharMatcher {
    Double maxBrightness =null;
    Double minBrightness =null;
    private final TreeMap<Double, TreeSet<Character>> letterBrightness;
    private final TreeMap<Character,Double> brightMap;
    /**
     * Constructs a SubImgCharMatcher object with the specified character set.
     * @param charset the character set
     */
    public SubImgCharMatcher(char[] charset){
        brightMap =new TreeMap<>();
        setCharBrightness(charset);
        updateMinMax();
        letterBrightness=new TreeMap<>();
        createLinearTree();
    }
    private void setCharBrightness(char[] charset){
        for(char c:charset){
            double brightness=charBrightness(c);
            brightMap.put(c,brightness);
        }
    }
    private void updateMinMax(){
        for(char c: brightMap.keySet()){
            if(maxBrightness ==null|| brightMap.get(c)> maxBrightness){
                maxBrightness = brightMap.get(c);
            }
            if(minBrightness ==null|| brightMap.get(c)< minBrightness){
                minBrightness = brightMap.get(c);
            }
        }
    }
    /**
     * Retrieves the TreeMap containing character brightness values.
     *
     * @return the TreeMap containing character brightness values
     */
    public TreeMap<Character,Double> getBrightMap(){
        return brightMap;
    }
    private void createLinearTree(){
        for (char c: brightMap.keySet()){
            addToTree(c,linearStretching(brightMap.get(c)));
        }
    }
    private void addToTree(char c, double brightness){
        if (letterBrightness.containsKey(brightness)){
            letterBrightness.get(brightness).add(c);
            return;
        }
        TreeSet<Character> characterSet = new TreeSet<>();
        characterSet.add(c);
        letterBrightness.put(brightness,characterSet);
    }
    /**
     * Retrieves the character that matches the specified brightness value.
     *
     * @param brightness the brightness value
     * @return the matching character
     * @throws CheckedEmptyCharException if the charset is empty
     */

    public char getCharByImageBrightness(double brightness) throws CheckedEmptyCharException {
        if(letterBrightness.isEmpty()){
            throw new CheckedEmptyCharException("Did not execute. " +
                    "Charset is empty.");
        }
        Double ceiling=letterBrightness.ceilingKey(brightness);
        Double floor=letterBrightness.floorKey(brightness);
        if(ceiling!=null &&floor!=null){
            return compareDistance(ceiling,floor,brightness);
        }
        if (ceiling!=null){
            return letterBrightness.get(ceiling).first();
        }
        else if(floor!=null){
            return letterBrightness.get(floor).first();
        }
        return ' ';
    }
    private char compareDistance(Double ceiling, Double floor, double brightness){
        double minusCeiling=ceiling-brightness;
        double minusFloor=brightness-floor;
        if(minusCeiling>minusFloor){
            return letterBrightness.get(floor).first();
        }
        else if(minusCeiling==minusFloor){
            return asciCompare(letterBrightness.get(floor).first(),
                    letterBrightness.get(ceiling).first());
        }
        else{
            return letterBrightness.get(ceiling).first();
        }
    }
    private char asciCompare(char a, char b){
        if (a<b){
            return a;
        }
        return b;
    }
    /**
     * Adds a character to the character set.
     *
     * @param c the character to add
     */
    public void addChar(char c){
        if(brightMap.containsKey(c)){
            return;
        }
        double brightness=charBrightness(c);
        brightMap.put(c,brightness);
        if (brightness>= minBrightness && brightness<= maxBrightness){
            addToTree(c,linearStretching(brightness));
        }
        else {
            letterBrightness.clear();
            minBrightness =(brightness< minBrightness)?brightness : minBrightness;
            maxBrightness =(brightness> maxBrightness)?brightness : maxBrightness;
            createLinearTree();
        }
    }
    /**
     * Removes a character from the character set.
     *
     * @param c the character to remove
     */
    public void removeChar(char c){
        if(!brightMap.containsKey(c)){
            return;
        }
        double brightness= brightMap.get(c);
        brightMap.remove(c);
        if (brightness> minBrightness && brightness< maxBrightness){
            removeFromTree(c,linearStretching(brightness));
        }
        else if (removeFromTree(c,linearStretching(brightness))==0){
            updateMinMax();
            letterBrightness.clear();
            createLinearTree();
        }
    }
    private int removeFromTree(char c, double val){
        letterBrightness.get(val).remove(c);
        if (letterBrightness.get(val).isEmpty()){
            letterBrightness.remove(val);
            return 0;
        }
        return 1;
    }

    private static double charBrightness(char c){
        int whitePixels=0;
        boolean[][]matrix= CharConverter.convertToBoolArray(c);
        for(int y = 0 ; y < CharConverter.DEFAULT_PIXEL_RESOLUTION ; y++) {
            for(int x = 0 ; x < CharConverter.DEFAULT_PIXEL_RESOLUTION ; x++) {
                if(matrix[y][x]){
                    whitePixels+=1;
                }
            }
        }
        double normalizesBrightess= (double) whitePixels/(CharConverter.DEFAULT_PIXEL_RESOLUTION*
                CharConverter.DEFAULT_PIXEL_RESOLUTION);
        return normalizesBrightess;
    }
    private double linearStretching(double charBrightness){
        return (charBrightness- minBrightness)/(maxBrightness - minBrightness);
    }

}
