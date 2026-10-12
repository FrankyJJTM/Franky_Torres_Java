package vallegrande.edu.pe.agrofrutoslambayeque;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import vallegrande.edu.pe.agrofrutoslambayeque.view.LoginView;

import java.io.IOException;

public class Launcher extends Application {

    // Versión local actual configurada a 1.0.1
    private static final String VERSION_LOCAL = "1.0.1";

    @Override
    public void start(Stage primaryStage) {
        // 1. Consultar la versión de la base de datos en la nube
        String versionNube = obtenerVersionDesdeNube();
        String urlDescarga = obtenerUrlDescargaDesdeNube();

        // 2. Comparar versiones
        if (versionNube != null && !VERSION_LOCAL.equals(versionNube)) {
            mostrarAlertaActualizacion(versionNube);
            ejecutarUpdater(urlDescarga);
            System.exit(0); // Cierra la app actual para que el Updater pueda reemplazar el .jar
            return;
        }

        // 3. Abrir la interfaz de Login si la versión es la correcta
        try {
            LoginView loginView = new LoginView();
            loginView.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String obtenerVersionDesdeNube() {
        // Retorna "1.0.1" para estar alineado con la versión local y evitar el bucle
        return "1.0.1";
    }

    private String obtenerUrlDescargaDesdeNube() {
        // Enlace directo del Release v1.0.1 en GitHub
        return "https://github.com/FrankyJJTM/AgrofrutosLambayeque-Desktop/releases/download/v1.0.1/AgrofrutosLambayeque.jar";
    }

    private void mostrarAlertaActualizacion(String nuevaVersion) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Actualización requerida");
        alert.setHeaderText("Nueva versión disponible (" + nuevaVersion + ")");
        alert.setContentText("La aplicación se actualizará automáticamente y se reiniciará.");
        alert.showAndWait();
    }

    private void ejecutarUpdater(String urlDescarga) {
        try {
            // Llama al programa Updater.jar pasándole la URL directa de descarga
            new ProcessBuilder("java", "-jar", "Updater.jar", urlDescarga).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}