package de.awp_consult;

import de.awp_consult.util.Util;
import de.awp_consult.util.UtilImpl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author donald.donchi-fofack
 * @version 1.0.0
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, NoSuchAlgorithmException {
        Util util = new UtilImpl();
        util.downloadAndUpdateFiles();
    }
}
