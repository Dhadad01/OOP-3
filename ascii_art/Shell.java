package ascii_art;

import java.io.IOException;
/**
 * She'll class for running the ASCII art generation program.
 */
public class Shell {
    private static final String defaultImage ="cat.jpeg";
    private static final char[] defaultNumbers ={'0','1','2','3','4','5','6','7','8','9'};
    private static final int defaultRes =128;
    /** 
     * Runs the shell for the ASCII art generation program.
     * @throws Exception if there is an issue during program execution
     */
    public void run() throws Exception {
        System.out.print(">>> ");
        String input=KeyboardInput.readLine();
        InputFactory fac=new InputFactory(defaultNumbers,
                defaultImage, defaultRes);
        while(!input.equals("exit")){
            try{
                fac.getInput(input);
            }
            catch (InputExceptions e){
                System.out.println(e.getMessage());

            }
            catch(IOException e){
                System.out.println("Did not execute due to problem with image file.");
            }

            System.out.print(">>> ");
            input=KeyboardInput.readLine();
        }
    }
    /**
     * Main method to start the program.
     * @param args command line arguments (not used)
     * @throws Exception if there is an issue during program execution
     */
    public static void main(String[] args) throws Exception {
        Shell shell=new Shell();
        shell.run();
    }

}
