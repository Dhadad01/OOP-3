package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.CheckResException;
import image.ImgDivider;
import image.PaddedImage;
import image_char_matching.CheckedEmptyCharException;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.TreeMap;
/**
 * Factory class for handling various input commands related to ASCII art generation.
 */
public class InputFactory {
    private final SubImgCharMatcher imgChar;
    private final ImgDivider imgDiv;
    private final AsciiArtAlgorithm asci;
    private String input;
    private String output="console";
    /**
     * Constructs an InputFactory object.
     * @param chars an array of characters for image matching
     * @param image the image file path
     * @param res the initial resolution value
     * @throws IOException if there is an issue with reading the image
     */
    public InputFactory(char[] chars, String image,int res) throws IOException {
        this.imgChar =new SubImgCharMatcher(chars);
        this.imgDiv = new ImgDivider(new PaddedImage(image),res);
        this.asci =new AsciiArtAlgorithm(imgChar, imgDiv);
    }
    /**
     * Processes user input commands.
     * @param userInput the user's input command
     * @throws Exception if there is an issue during command processing
     */
    public void getInput(String userInput) throws Exception {
        this.input=userInput;
        if(input.length()>=2){/// image space
            changeInput(input.split(" ")[0]);
        }
        switch (input){
            case "chars":
                TreeMap<Character,Double>tree= imgChar.getBrightMap();
                for(char c:tree.keySet()){
                    System.out.print(c+" ");///ask " " in last one
                }
                System.out.println();
                break;
            case "add":
                addRemoveInput(userInput,true);
                break;
            case "remove":
                addRemoveInput(userInput,false);
                break;
            case "res":
                resInput(userInput);
                break;
            case "image":
                handleImage(userInput);
                break;
            case "output":
               handleOutput(userInput);
               break;
            case "asciiArt":
                runAscii();
                break;
            default:
                throw new CheckedAllException("Did not execute due to " +
                        "incorrect command.");

        }
    }

    private void runAscii() throws CheckedEmptyCharException {
        if(output.equals("console")){
            ConsoleAsciiOutput console=new ConsoleAsciiOutput();
            console.out(asci.run());
        } else if (output.equals("html")) {
            String filename = "out.html";
            String fontName = "Courier New";
            HtmlAsciiOutput html=new HtmlAsciiOutput(filename, fontName);
            html.out(asci.run());

        }
    }
    private void changeInput(String inputPart){
        switch (inputPart){
            case "add":
                input="add";
                break;
            case "remove":
                input="remove";
                break;
            case "res":
                input="res";
                break;
            case "output":
                input="output";
                break;
            case "image":
                input="image";
                break;
        }
    }
    private void handleImage(String orig) throws IOException, CheckedAllException {
        String[]p=orig.split(" ");
        CheckedAllException checkedAllException  = new CheckedAllException("Did not execute due to " +
                "incorrect command.");
        if (!(p.length==2)){///all exep??
            throw checkedAllException;
        }
        imgDiv.changeImg(new PaddedImage(p[1]));
    }

    private void handleOutput(String orig) throws CheckedFontException {
        String[]p=orig.split(" ");
        CheckedFontException checkedFont = new CheckedFontException("Did not change output method due to " +
                "incorrect format.");
        if (!(p.length==2&&(p[1].equals("console")||p[1].equals("html")))){
            throw checkedFont;
        }
        output=p[1];
    }

    private void resInput(String orig) throws CheckResException, CheckResFormatException {
        String[]p=orig.split(" ");
        CheckResFormatException resFormatException=new CheckResFormatException("Did not change " +
                "resolution due to incorrect format.");
        if(p.length==2){
            if(p[1].equals("up")){
                imgDiv.changeRes(imgDiv.getRes()*2);
                System.out.println("Resolution set to "+ imgDiv.getRes());
                return;
            }
            else if(p[1].equals("down")){
                imgDiv.changeRes(imgDiv.getRes()/2);
                System.out.println("Resolution set to "+ imgDiv.getRes());
                return;
            }
            throw resFormatException;
        }
        throw resFormatException;
    }
    private void addRemoveInput(String orig,boolean addRemove) throws Exception {
        CheckedAddException checkedadd=new CheckedAddException("Did not add due to incorrect format.");
        CheckedRemoveException checkedRemove=new CheckedRemoveException("Did not remove due to " +
                "incorrect format.");
        String[]p=orig.split(" ");
        if(p.length==2){
            if(p[1].length()==1&&isAsciiChar(p[1].charAt(0))){
                addRemoveChar(p[1].charAt(0),addRemove);
                return;
            }
            else if (p[1].equals("all")){
                addAllAsciiChars(addRemove);
                return;
            }
            else if(p[1].equals("space")){
                addRemoveChar(' ',addRemove);
                return;
            }
            else if(p[1].length()==3&&isAsciiChar(p[1].charAt(0))&&isAsciiChar(p[1].charAt(2))&&
            p[1].charAt(1)=='-'){
                processRange(p[1],addRemove);
                return;
            }
            throw (addRemove)?checkedadd:checkedRemove;
        }
        throw (addRemove)?checkedadd:checkedRemove;
    }

    private void addRemoveChar(char c, boolean addRemove){
        if (addRemove) {
            imgChar.addChar(c);
        } else {
            imgChar.removeChar(c);
        }
    }
    private boolean isAsciiChar(char c) {
        // Check if the Unicode value of the character is within the ASCII range
        return c >= 32 && c <= 127;
    }
    private void addAllAsciiChars(boolean addRemove) {
        if(addRemove) {
            for (int i = 32; i <= 126; i++) {
                char c = (char) i;
                imgChar.addChar(c);
            }
        }
        else {
            for (int i = 32; i <= 126; i++) {
                char c = (char) i;
                imgChar.removeChar(c);
            }
        }
    }
    private void processRange(String range,boolean addRemove) {
        // Split the range into start and end characters
        String[] parts = range.split("-");
//            System.out.println(parts[0]);
            char startChar = parts[0].charAt(0);
            char endChar = parts[1].charAt(0);
//            System.out.println(startChar);

            // Determine the direction of the range
            int increment = (startChar < endChar) ? 1 : -1;

            // Iterate over the range and add each character to imgChar
            for (char c = startChar; c != endChar + increment; c += increment) {
//                System.out.println(c);
                if(addRemove) {
                    imgChar.addChar(c);
                }
                else{
                    imgChar.removeChar(c);
                }
            }
    }


}
