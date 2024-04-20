package image;

import java.awt.*;
import java.io.IOException;
/**
 * Represents an image with padding.
 */
public class PaddedImage extends Image {
    private int paddedWidth;
    private int paddedHeight;
    private Color[][] paddedPixelArray;
    /**
     * Constructs a PaddedImage object from a file.
     * @param filename the filename of the image
     * @throws IOException if an error occurs while reading the image
     */
    public PaddedImage(String filename) throws IOException {
        super(filename);
        paddedWidth = super.getWidth();
        paddedHeight = super.getHeight();
        padImage();

    }

    private void padImage() {
        int origWidth = paddedWidth;
        int origHeight = paddedHeight;
        if (!(paddedWidth > 0 && ((paddedWidth & (paddedWidth - 1)) == 0))) {
            paddedWidth = findingNumberPower2(paddedWidth);
        }
        if (!(paddedHeight > 0 && ((paddedHeight & (paddedHeight - 1)) == 0))) {
            paddedHeight = findingNumberPower2(paddedHeight);
        }
        this.paddedPixelArray = new Color[this.paddedHeight][this.paddedWidth];

        fillOriginalImage(paddedPixelArray, origWidth, paddedWidth, origHeight, paddedHeight);
        addRows(paddedPixelArray,origHeight, paddedHeight, paddedWidth);
        addCols(paddedPixelArray,origWidth, paddedWidth, paddedHeight);
    }

    private void fillOriginalImage(Color[][] pixelArray, int width, int newWidth,
                                          int height, int newHeight) {
        for (int i = (newHeight - height) / 2; i < newHeight - (newHeight - height) / 2; i++) {
            for (int j = (newWidth - width) / 2; j < newWidth - (newWidth - width) / 2; j++) {
                pixelArray[i][j] = super.getPixel(i - (newHeight - height) / 2, j -
                        (newWidth - width) / 2);
            }
        }
    }


    private void addRows(Color[][] pixelArray, int height, int paddleHeight, int paddleWidth) {
        for (int i = 0; i < (paddleHeight - height) / 2; i++) {
            for (int j = 0; j < paddleWidth; j++) {
                pixelArray[i][j] = Color.white;
                pixelArray[paddleHeight - 1 - i][j] = Color.white;
            }
        }
    }

    private void addCols(Color[][] pixelArray, int width, int paddleWidth, int paddleHeight) {
        for (int i = 0; i < (paddleWidth - width) / 2; i++) {
            for (int j = 0; j < paddleHeight; j++) {
                pixelArray[j][i] = Color.white;
                pixelArray[j][paddleHeight - i - 1] = Color.white;
            }
        }
    }
    private int findingNumberPower2(int number){
        int lowerPowerOfTwo = 1;
        while (lowerPowerOfTwo * 2 <= number) {
            lowerPowerOfTwo *= 2;
        }
        return lowerPowerOfTwo*2;
    }

    @Override
    public int getWidth() {
        return paddedWidth;
    }

    @Override
    public int getHeight() {
        return paddedHeight;
    }

    @Override
    public Color getPixel(int x, int y) {
        return paddedPixelArray[x][y];
    }
}
