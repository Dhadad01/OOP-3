package image;

import java.awt.*;
/**
 * Class for dividing an image into sub-images and calculating brightness values for each sub-image.
 */
public class ImgDivider {
    private PaddedImage img;
    private int res;
    private Image[][] divideImg;
    private double [][] brightness;

    /**
     * Constructs an ImgDivider object with the specified image and resolution.
     * @param img the image to divide
     * @param res the resolution value
     */
    public ImgDivider(PaddedImage img,int res) {
        this.img=img;
        this.res=res;
        divideImg =divider();
        brightness =brightness();

    }
    /**
     * Changes the current image to a new image without changing the resolution.
     * @param image the new image
     */
    public void changeImg(PaddedImage image){/////check change img without res
        img=image;
        divideImg =divider();
        brightness =brightness();
    }
    /**
     * Changes the resolution of the image.
     * @param res the new resolution value
     * @throws CheckResException if the new resolution exceeds boundaries
     */
    public void changeRes(int res) throws CheckResException {
        if(!validRes(res)){
            throw new CheckResException("Did not change resolution due to " +
                    "exceeding boundaries.");
        }
        this.res=res;
        divideImg =divider();
        brightness =brightness();
    }
    /**
     * Retrieves the brightness matrix of the divided image.
     * @return the brightness matrix
     */
    public double[][] getBrightness() {
        return brightness;
    }
    /**
     * Retrieves the current resolution value.
     * @return the resolution value
     */
    public int getRes(){
        return res;
    }
    private boolean validRes(int res){
        if(res>img.getWidth()){
            return false;
        }
        int minimal=(1>img.getWidth()/img.getHeight())?1:img.getWidth()/img.getHeight();
        return res >= minimal;
    }

    private Image[][] divider() {
        int subImgWidth = img.getWidth() / res;
        int subImgHeight = subImgWidth;
        Image[][] divider = new Image[res][res];
        int line = 0;

        for (int t = 0; t < img.getHeight(); t += subImgHeight) {
            divider[line] = createSubImagesForRow(t, subImgWidth, subImgHeight, res);
            line++;
        }

        return divider;
    }

    private Image[] createSubImagesForRow(int rowStart, int subImgWidth, int subImgHeight, int numCols) {
        Image[] rowImages = new Image[numCols];
        int col = 0;

        for (int m = 0; m < img.getWidth(); m += subImgWidth) {
            Color[][] subPixelArray = extractSubPixelArray(rowStart, m, subImgWidth, subImgHeight);
            rowImages[col] = new Image(subPixelArray, subImgWidth, subImgHeight);
            col++;
        }

        return rowImages;
    }

    private Color[][] extractSubPixelArray(int rowStart, int colStart, int subImgWidth, int subImgHeight) {
        Color[][] subPixelArray = new Color[subImgHeight][subImgWidth];

        for (int i = 0; i < subImgHeight; i++) {
            for (int j = 0; j < subImgWidth; j++) {
                subPixelArray[i][j] = img.getPixel(rowStart + i, colStart + j);
            }
        }
        return subPixelArray;
    }
    private double[][] brightness(){
        double[][] finalBrightness=new double[res][res];
        for(int i=0;i<res;i++){
            for(int j=0;j<res;j++){
                double bright=brightImg(divideImg[i][j]);
                finalBrightness[i][j]=bright;
            }
        }
        return finalBrightness;
    }
    private double brightImg(Image img){
        double sumGreyPixels=0;
        for(int i=0;i<img.getHeight();i++){
            for(int j=0;j<img.getWidth();j++){
                double greyPixel=greyBright(img.getPixel(i,j));
                sumGreyPixels+=greyPixel;
            }
        }
        return sumGreyPixels/((img.getHeight()*img.getWidth())*255);
    }
    private double greyBright(Color color){
        return color.getRed()*0.2126+color.getGreen()*0.7152+color.getBlue()*0.0722;
    }

}
