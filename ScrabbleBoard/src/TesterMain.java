import sun.misc.IOUtils;

import java.io.InputStream;
import java.net.InetAddress;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class TesterMain {
    public static void main(String[] args) throws Exception {
        //ClassLoader.getSystemClassLoader().getResourceAsStream("scrabblevalues.txt");
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("scrabblevalues.txt");


        //System.out.println(IOUtils.toString(inputStream, encoding));
    }
}
