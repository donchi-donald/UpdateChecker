package de.awp_consult.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author donald.donchi-fofack
 * @version 1.0.0
 * Eine Klasse, die Programme von URLs herunterlädt und überprüft, ob sie sich seit dem letzten Mal geändert haben.
 */
public interface Util {
    /**
     * Lädt den Inhalt einer Datei von einer URL herunter.
     *
     * @param url die URL der Datei
     * @return der Inhalt der Datei als Byte-Array
     * @throws IOException wenn ein Fehler beim Herunterladen der Datei auftritt
     */
    byte[] downloadFile(String url) throws IOException;

    /**
     * Berechnet den SHA-256-Checksum für den Inhalt eines Byte-Arrays.
     *
     * @param content der Inhalt, für den der Checksum berechnet werden soll
     * @return der berechnete Checksum als Byte-Array
     * @throws NoSuchAlgorithmException wenn das SHA-256-Hashing-Verfahren nicht unterstützt wird
     */
    byte[] getChecksum(byte[] content) throws NoSuchAlgorithmException;

    /**
     * Liest den SHA-256-Checksum aus einer Checksum-Datei.
     *
     * @param filename der Name der Checksum-Datei
     * @return der gelesene Checksum als Byte-Array oder null, wenn die Datei nicht existiert
     * @throws IOException wenn ein Fehler beim Lesen der Datei auftritt
     */
    byte[] readChecksumFromFile(String filename) throws IOException;


    /**
     * Speichert den Inhalt eines Byte-Arrays in einer Datei.
     * @param content der Inhalt, der gespeichert werden soll.
     * @param filename der Dateiname, unter dem der Inhalt gespeichert werden soll.
     * @throws IOException wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    void saveToFile(byte[] content, String filename) throws IOException;

    /**
     * Speichert den übergebenen Checksum-Wert in einer Datei.
     * @param checksum der Checksum-Wert, der gespeichert werden soll.
     * @param filename  der Dateiname, unter dem der Checksum-Wert gespeichert werden soll.
     * @throws IOException wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    void saveChecksumToFile(byte[] checksum, String filename) throws IOException;

    /**
     * Diese Methode lädt Dateien von bestimmten URLs herunter und prüft, ob die Dateien seit dem letzten Mal geändert wurden.
     * Wenn eine Datei aktualisiert wurde, wird die neue Datei heruntergeladen und ihre Integrität geprüft.
     *
     * @throws IOException falls ein Problem beim Herunterladen oder Speichern der Dateien auftritt.
     * @throws NoSuchAlgorithmException falls eine nicht unterstützte Hash-Algorithmus angegeben wird.
     */
    public void downloadAndUpdateFiles(String[] urls,  String[] filenamesWithPath) throws IOException, NoSuchAlgorithmException;

    /**
     * Diese Methode prüft, ob eine Datei seit dem letzten Mal geändert wurde, indem sie die Integrität der alten und neuen Datei vergleicht.
     *
     * @param util die Utility-Klasse, die Methoden zum Herunterladen und Überprüfen von Dateien bereitstellt.
     * @param filename der Name der Datei, die überprüft wird.
     * @param content der Inhalt der heruntergeladenen Datei.
     * @return true, wenn die Datei auf dem neuesten Stand ist, andernfalls false.
     * @throws NoSuchAlgorithmException falls eine nicht unterstützte Hash-Algorithmus angegeben wird.
     */
    public boolean isFileUpToDate(Util util, String filename, byte[] content) throws NoSuchAlgorithmException, IOException;

    /**
     * Diese Methode speichert eine heruntergeladene Datei und ihre Integrität in einer separaten Checksum-Datei.
     *
     * @param util die Utility-Klasse, die Methoden zum Herunterladen und Überprüfen von Dateien bereitstellt.
     * @param filename der Name der heruntergeladenen Datei.
     * @param content der Inhalt der heruntergeladenen Datei.
     * @throws IOException falls ein Problem beim Speichern der Datei auftritt.
     * @throws NoSuchAlgorithmException falls eine nicht unterstützte Hash-Algorithmus angegeben wird.
     */
    public void updateFile(Util util, String filename, byte[] content) throws IOException, NoSuchAlgorithmException;
}
