package de.awp_consult.util;

import de.awp_consult.config.Config;
import junit.framework.TestCase;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UtilImplTest extends TestCase {

    private  Util util;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        util =  new UtilImpl();
        String[] urls = {
                Config.TOTAL_COMMANDER_DOWNLOAD_URL,
                Config.FIREFOX_DOWNLOAD_URL
        };
        String[] filenamesWithPath = {
                Config.PATH_TMP+Config.TOTAL_COMMANDER_FILENAME,
                Config.PATH_TMP+Config.FIREFOX_FILENAME
        };
        util.downloadAndUpdateFiles(urls, filenamesWithPath);

    }

    public void testDownloadFile() throws IOException {
        byte[] bytes = util.downloadFile(Config.FIREFOX_DOWNLOAD_URL);
        assertTrue("FirefoxDownload not work",bytes.length>0);
        byte[] bytes2 = util.downloadFile(Config.TOTAL_COMMANDER_DOWNLOAD_URL);
        assertTrue("Total commander Download not work",bytes2.length>0);
    }



    public void testDownloadAndUpdateFiles() {
        File file1 = new File(Config.PATH_TMP+Config.TOTAL_COMMANDER_FILENAME);
        File file2 = new File(Config.PATH_TMP+Config.FIREFOX_FILENAME);
        assertTrue(file1.exists());
        assertTrue(file2.exists());
    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        Files.deleteIfExists(Paths.get(Config.PATH_TMP+Config.TOTAL_COMMANDER_FILENAME));
        Files.deleteIfExists(Paths.get(Config.PATH_TMP+Config.FIREFOX_FILENAME));
        Files.deleteIfExists(Paths.get(Config.PATH_TMP+Config.TOTAL_COMMANDER_FILENAME+".sha256"));
        Files.deleteIfExists(Paths.get(Config.PATH_TMP+Config.FIREFOX_FILENAME+".sha256"));
        System.out.println("Alles wurde im temp erfolgreich gelöscht!!");
    }
}
