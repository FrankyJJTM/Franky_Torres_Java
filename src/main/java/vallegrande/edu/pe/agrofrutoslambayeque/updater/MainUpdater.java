package vallegrande.edu.pe.agrofrutoslambayeque.updater;

import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class MainUpdater {

    public static void main(String[] args) {
        if (args.length == 0) return;

        String fileURL = args[0]; // Recibe la URL del nuevo .jar
        String targetFile = "AgrofrutosLambayeque.jar";

        try {
            // Espera 2 segundos a que el programa principal termine de cerrarse del todo
            Thread.sleep(2000);

            System.out.println("Descargando actualización...");
            URL url = new URL(fileURL);

            // Descarga y reemplaza el archivo .jar local
            try (InputStream in = url.openStream();
                 FileOutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("Descarga completada. Reiniciando app...");

            // Vuelve a abrir la aplicación principal ya actualizada
            new ProcessBuilder("java", "-jar", targetFile).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}