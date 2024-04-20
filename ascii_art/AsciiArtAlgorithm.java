package ascii_art;
import image.ImgDivider;
import image_char_matching.CheckedEmptyCharException;
import image_char_matching.SubImgCharMatcher;

/**
 * Class representing an algorithm to convert an image into ASCII art.
 */
public class AsciiArtAlgorithm {
    private final ImgDivider imgDivider;
    private final SubImgCharMatcher imgToChar;

    /**
     * Constructs an AsciiArtAlgorithm object.
     * @param imgToChar an object that converts image brightness to characters
     * @param imgDivider an object that divides the image into segments
     */
    public AsciiArtAlgorithm(SubImgCharMatcher imgToChar, ImgDivider imgDivider) {
        this.imgToChar =imgToChar;
        this.imgDivider=imgDivider;
    }
    /**
     * Runs the ASCII art algorithm.
     * @return a 2D char array representing the ASCII art
     * @throws CheckedEmptyCharException if an empty character is encountered during processing
     */
    public char[][] run() throws CheckedEmptyCharException {
        double[][]divider=imgDivider.getBrightness();
        char[][]res=new char[divider.length][divider.length];
        for (int i=0;i< divider.length;i++){
            for (int j=0;j<divider.length;j++){
               res[i][j]= imgToChar.getCharByImageBrightness(divider[i][j]);
            }
        }
        return res;
    }
}
