package de.awp_consult.util;

import de.awp_consult.config.Config;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilImpl implements Util{
    /**
     * Lädt den Inhalt einer Datei von einer URL herunter.
     *
     * @param url die URL der Datei
     * @return der Inhalt der Datei als Byte-Array
     * @throws IOException wenn ein Fehler beim Herunterladen der Datei auftritt
     */
    @Override
    public byte[] downloadFile(String url) throws IOException {
        InputStream inputStream = new URL(url).openStream();
        try {
            return IOUtils.toByteArray(inputStream);
        } finally {
            inputStream.close();
        }
    }

    /**
     * Berechnet den SHA-256-Checksum für den Inhalt eines Byte-Arrays.
     *
     * @param content der Inhalt, für den der Checksum berechnet werden soll
     * @return der berechnete Checksum als Byte-Array
     * @throws NoSuchAlgorithmException wenn das SHA-256-Hashing-Verfahren nicht unterstützt wird
     */
    @Override
    public byte[] getChecksum(byte[] content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(content);
    }

    /**
     * Liest den SHA-256-Checksum aus einer Checksum-Datei.
     *
     * @param filename der Name der Checksum-Datei
     * @return der gelesene Checksum als Byte-Array oder null, wenn die Datei nicht existiert
     * @throws IOException wenn ein Fehler beim Lesen der Datei auftritt
     */
    @Override
    public byte[] readChecksumFromFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            return null;
        }
        return Files.readAllBytes(path);
    }

    /**
     * Speichert den Inhalt eines Byte-Arrays in einer Datei.
     *
     * @param content  der Inhalt, der gespeichert werden soll.
     * @param filename der Dateiname, unter dem der Inhalt gespeichert werden soll.
     * @throws IOException wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    @Override
    public void saveToFile(byte[] content, String filename) throws IOException {
        FileUtils.writeByteArrayToFile(new File(filename), content);
    }

    /**
     * Speichert den übergebenen Checksum-Wert in einer Datei.
     *
     * @param checksum der Checksum-Wert, der gespeichert werden soll.
     * @param filename der Dateiname, unter dem der Checksum-Wert gespeichert werden soll.
     * @throws IOException wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    @Override
    public void saveChecksumToFile(byte[] checksum, String filename) throws IOException {
        String checksumHex = Hex.encodeHexString(checksum);
        FileUtils.writeStringToFile(new File(filename), checksumHex, "UTF-8");
    }


    /**
     * Diese Methode prüft, ob eine Datei seit dem letzten Mal geändert wurde, indem sie die Integrität der alten und neuen Datei vergleicht.
     *
     * @param util     die Utility-Klasse, die Methoden zum Herunterladen und Überprüfen von Dateien bereitstellt.
     * @param filename der Name der Datei, die überprüft wird.
     * @param content  der Inhalt der heruntergeladenen Datei.
     * @return true, wenn die Datei auf dem neuesten Stand ist, andernfalls false.
     * @throws NoSuchAlgorithmException falls eine nicht unterstützte Hash-Algorithmus angegeben wird.
     */
    @Override
    public boolean isFileUpToDate(Util util, String filename, byte[] content) throws NoSuchAlgorithmException, IOException {
            byte[] newChecksum = util.getChecksum(content);
            byte[] oldChecksum = util.readChecksumFromFile(filename + ".sha256");

            if (oldChecksum != null && MessageDigest.isEqual(oldChecksum, newChecksum)) {
                System.out.println(filename + " ist auf dem neuesten Stand.");
                return true;
            }

            return false;
    }

    /**
     * Diese Methode speichert eine heruntergeladene Datei und ihre Integrität in einer separaten Checksum-Datei.
     *
     * @param util     die Utility-Klasse, die Methoden zum Herunterladen und Überprüfen von Dateien bereitstellt.
     * @param filename der Name der heruntergeladenen Datei.
     * @param content  der Inhalt der heruntergeladenen Datei.
     * @throws IOException              falls ein Problem beim Speichern der Datei auftritt.
     * @throws NoSuchAlgorithmException falls eine nicht unterstützte Hash-Algorithmus angegeben wird.
     */
    @Override
    public void updateFile(Util util, String filename, byte[] content) throws IOException, NoSuchAlgorithmException {
            util.saveToFile(content, filename);
            util.saveChecksumToFile(util.getChecksum(content), filename + ".sha256");
            System.out.println(filename + " wurde aktualisiert.");
    }

    /**
     * Diese Methode lädt Dateien von bestimmten URLs herunter und prüft, ob die Dateien seit dem letzten Mal geändert wurden.
     * Wenn eine Datei aktualisiert wurde, wird die neue Datei heruntergeladen und ihre Integrität geprüft.
     *
     * @throws IOException              falls ein Problem beim Herunterladen oder Speichern der Dateien auftritt.
     * @throws NoSuchAlgorithmException falls eine nicht unterstützte Hash-Algorithmus angegeben wird.
     */
    @Override
    public void downloadAndUpdateFiles() throws IOException, NoSuchAlgorithmException {
        Util util = new UtilImpl();

        String[] urls = {
                Config.TOTAL_COMMANDER_DOWNLOAD_URL,
                Config.FIREFOX_DOWNLOAD_URL
        };
        String[] filenamesWithPath = {
                Config.PATH_MY_FILE+Config.TOTAL_COMMANDER_FILENAME,
                Config.PATH_MY_FILE+Config.FIREFOX_FILENAME
        };

        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            String filename = filenamesWithPath[i];

            try {
                byte[] content = util.downloadFile(url);

                if (isFileUpToDate(util, filename, content)) {
                    continue;
                }

                updateFile(util, filename, content);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }


}
