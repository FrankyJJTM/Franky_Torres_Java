package vallegrande.edu.pe.agrofrutoslambayeque;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import vallegrande.edu.pe.agrofrutoslambayeque.view.LoginView;

import java.io.IOException;

public class Launcher extends Application {

    // Versión fija que tiene este ejecutable .jar local
    private static final String VERSION_LOCAL = "1.0.0";

    @Override
    public void start(Stage primaryStage) {
        // 1. Consultar la versión de la base de datos en la nube
        String versionNube = obtenerVersionDesdeNube(); // Reemplazar con tu consulta DAO/BD
        String urlDescarga = obtenerUrlDescargaDesdeNube(); // Reemplazar con tu consulta DAO/BD

        // 2. Comparar versiones
        if (versionNube != null && !VERSION_LOCAL.equals(versionNube)) {
            mostrarAlertaActualizacion(versionNube);
            ejecutarUpdater(urlDescarga);
            System.exit(0); // Cierra la app actual para que el Updater pueda reemplazar el .jar
            return;
        }

        // 3. Si la versión es correcta, abre la interfaz normal
        try {
            LoginView loginView = new LoginView();
            loginView.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String obtenerVersionDesdeNube() {
        // AQUÍ haces la consulta SQL a tu BD en la nube (ej. SELECT valor FROM config WHERE clave = 'version_actual')
        // Por ahora devuelve "1.0.0" para pruebas
        return "1.0.0";
    }

    private String obtenerUrlDescargaDesdeNube() {
        // AQUÍ consultas el enlace de GitHub Releases / Google Drive grabado en la BD
        return "https://midominio.com/AgrofrutosLambayeque.jar";
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
        // Ahora iniciamos la app desde el start() de JavaFX
        launch(args);
    }
}