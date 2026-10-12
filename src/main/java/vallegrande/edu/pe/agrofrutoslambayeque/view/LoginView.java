package vallegrande.edu.pe.agrofrutoslambayeque.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vallegrande.edu.pe.agrofrutoslambayeque.model.Usuario;
import vallegrande.edu.pe.agrofrutoslambayeque.model.UsuarioDao;

public class LoginView extends Application {

    // Define aquí la versión local actual
    private static final String APP_VERSION = "v1.0.0";

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(12);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2c3e50;");

        Label lblTitulo = new Label("AGROFRUTOS");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblSubtitulo = new Label("Iniciar Sesión");
        lblSubtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        TextField txtUser = new TextField();
        txtUser.setPromptText("Usuario");
        txtUser.setMaxWidth(250);

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Contraseña");
        txtPass.setMaxWidth(250);

        Button btnLogin = new Button("Ingresar");
        btnLogin.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 20 8 20; -fx-background-radius: 5;");
        btnLogin.setMaxWidth(250);

        Label lblMsg = new Label();
        lblMsg.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");

        // Etiqueta visual para la versión
        Label lblVersion = new Label("Versión " + APP_VERSION);
        lblVersion.setStyle("-fx-font-size: 11px; -fx-text-fill: #7f8c8d; -fx-padding: 10 0 0 0;");

        UsuarioDao usuarioDao = new UsuarioDao();

        btnLogin.setOnAction(e -> {
            String u = txtUser.getText().trim();
            String p = txtPass.getText().trim();

            if (u.isEmpty() || p.isEmpty()) {
                lblMsg.setText("Ingresa usuario y contraseña.");
                return;
            }

            Usuario usuario = usuarioDao.autenticar(u, p);
            if (usuario != null) {
                MainView mainView = new MainView(usuario);
                mainView.start(stage);
            } else {
                lblMsg.setText("Credenciales incorrectas.");
            }
        });

        root.getChildren().addAll(lblTitulo, lblSubtitulo, txtUser, txtPass, btnLogin, lblMsg, lblVersion);

        Scene scene = new Scene(root, 360, 410);
        stage.setTitle("Login - Agrofrutos Lambayeque (" + APP_VERSION + ")");
        stage.setScene(scene);
        stage.show();
    }
}