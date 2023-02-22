package de.awp_consult;

import de.awp_consult.config.Config;
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
        String[] urls = {
                Config.TOTAL_COMMANDER_DOWNLOAD_URL,
                Config.FIREFOX_DOWNLOAD_URL
        };
        String[] filenamesWithPath = {
                Config.PATH_MY_FILE+Config.TOTAL_COMMANDER_FILENAME,
                Config.PATH_MY_FILE+Config.FIREFOX_FILENAME
        };
        Util util = new UtilImpl();
        util.downloadAndUpdateFiles(urls, filenamesWithPath);
    }
}
