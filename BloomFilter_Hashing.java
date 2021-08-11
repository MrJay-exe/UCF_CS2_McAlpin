import java.util.*;
import java.io.*;

public class Hw03 {

    public static int UCFxram(String input, int len) throws UnsupportedEncodingException {

        // initializing in everything

        int randVal1 = 0xbcde98ef;
        int randVal2 = 0x7890face;
        int hashVal = 0xfa01bc96;
        int roundedEnd = len & 0xfffffffc;
        int tempData = 0;

        //input string in ascii array of character in bytes.
        byte[] d = null;
        d = input.getBytes("US-ASCII");

        //ucfxram calculates the 32 bit integer has value from the input.

        for (int i = 0; i < roundedEnd; i += 4) {

            tempData = (d[i] & 0xff) | ((d[i + 1] & 0xff) << 8) | ((d[i + 2] & 0xff) << 16) | ((d[i + 3] << 24));
            tempData = tempData * randVal1;
            tempData = Integer.rotateLeft(tempData, 12);
            tempData = tempData * randVal2;
            hashVal = hashVal ^ tempData;
            hashVal = Integer.rotateLeft(hashVal, 13);
            hashVal = hashVal * 5 + 0x46b6456e;
        }

        tempData = 0;

        if ((len & 0x03) == 3) {
            tempData = (d[roundedEnd + 2] & 0xff) << 16;
            len = len - 1;
        }
        if ((len & 0x03) == 2) {
            tempData |= (d[roundedEnd + 1] & 0xff) << 8;
            len = len - 1;
        }
        if ((len & 0x03) == 1) {

            tempData |= (d[roundedEnd] & 0xff);
            tempData = tempData * randVal1;
            tempData = Integer.rotateLeft(tempData, 14);
            tempData = tempData * randVal2;
            hashVal = hashVal ^ tempData;
        }

        hashVal = hashVal ^ len;
        hashVal = hashVal & 0xb6acbe58;
        hashVal = hashVal ^ hashVal >>> 13;
        hashVal = hashVal * 0x53ea2b2c;
        hashVal = hashVal ^ hashVal >>> 16;

        //returns the hash value
        return hashVal;

    }

    static void complexityIndicator() {
        // this is the complexity indicator
        System.err.println("ja441813;2;2");

    }


    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        // main is for reading in the file and outputting the results
        File file = new File(args[0]);
        String input;

        Scanner scan = new Scanner(file);

        int hashValue = 0;
        complexityIndicator();

        while (scan.hasNextLine()) {

            input = scan.nextLine();
            input = input.replaceAll("\n", "").replaceAll("\r", "");
            hashValue = UCFxram(input, input.length());

            System.out.format("%10x:%s\n", hashValue, input);

        }

        System.out.println("Input file processed");

    }


}
