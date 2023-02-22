package de.awp_consult.util;

import de.awp_consult.config.Config;
import junit.framework.TestCase;
import org.junit.Before;

import java.io.IOException;

public class UtilImplTest extends TestCase {

    private  Util util;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        util =  new UtilImpl();

    }

    public void testDownloadFile() throws IOException {
        byte[] bytes = util.downloadFile(Config.FIREFOX_DOWNLOAD_URL);
        assertTrue("FirefoxDownload not work",bytes.length>0);
        byte[] bytes2 = util.downloadFile(Config.TOTAL_COMMANDER_DOWNLOAD_URL);
        assertTrue("Total commander Download not work",bytes2.length>0);
    }

    public void testGetChecksum() {
    }

    public void testReadChecksumFromFile() {
    }

    public void testSaveToFile() {
    }

    public void testSaveChecksumToFile() {
    }

    public void testDownloadAndUpdateFiles() {
    }

    public void testIsFileUpToDate() {
    }

    public void testUpdateFile() {
    }
}
