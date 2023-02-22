package de.awp_consult.config;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigTest {


    @Test
    public void shouldCheckExistenceOfFolderMyFile() {
        String folder = Config.PATH_MY_FILE;
        File file = new File(folder);
        assertTrue("Der Ordner" + folder + " existiert nicht.", file.exists());

    }

    @Test
    public void shouldCheckExistenceOfSubFolderTemp() {
        String folder = Config.PATH_TMP;
        File file = new File(folder);
        assertTrue("Der Ordner" + folder + " existiert nicht.", file.exists());

    }

    @Test
    public void shouldTestValidityOfUrlTotalCommader() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(Config.TOTAL_COMMANDER_DOWNLOAD_URL);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue("Erwarteter Statuscode 200, aber erhalten " + statusCode, statusCode == 200);
    }

    @Test
    public void shouldTestValidityOfUrlFirefox() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(Config.FIREFOX_DOWNLOAD_URL);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue("Erwarteter Statuscode 200, aber erhalten " + statusCode, statusCode == 200);
    }

    @Test
    public void testDownloadFileNameFirefox() {
        assertEquals(Config.FIREFOX_FILENAME, "firefox_setup.exe");
    }

    @Test
    public void testDownloadFileNameTotalCommander() {
        assertEquals(Config.TOTAL_COMMANDER_FILENAME, "total_commander_setup.exe");
    }

}
