package vallegrande.edu.pe.agrofrutoslambayeque.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

import vallegrande.edu.pe.agrofrutoslambayeque.controller.MainController;
import vallegrande.edu.pe.agrofrutoslambayeque.model.Contacto;
import vallegrande.edu.pe.agrofrutoslambayeque.model.Usuario;

public class MainView extends Application {

    private Button btnInicio;
    private Button btnContactos;
    private Button btnCerrarSesion;
    private Button btnEliminarTodos;

    private VBox contenedorCards;
    private BorderPane root;

    private TextField txtNombre;
    private TextField txtApellido;
    private TextField txtTelefono;
    private TextField txtCorreo;
    private TextField txtMensaje;
    private Button btnGuardarContacto;
    private Button btnCancelarEdicion;
    private Label lblTituloForm;

    private TextField txtBuscar;
    private Button btnBuscar;
    private Button btnLimpiarBusqueda;

    private Integer idContactoSeleccionado = null;
    private Usuario usuarioLogueado;
    private Stage stagePrincipal;

    public MainView() {
        // Constructor por defecto
    }

    public MainView(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @Override
    public void start(Stage stage) {
        this.stagePrincipal = stage;
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f4f6f9;");

        // --- SIDEBAR MODERNO Y CORPORATIVO ---
        VBox menuLateral = new VBox(12);
        menuLateral.setPadding(new Insets(25, 15, 25, 15));
        menuLateral.setPrefWidth(240);
        menuLateral.setStyle("-fx-background-color: #1e293b;"); // Dark Slate

        Label lblBrand = new Label("AGROFRUTOS");
        lblBrand.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #10b981;");

        Label lblSubBrand = new Label("LAMBAYEQUE S.A.C.");
        lblSubBrand.setStyle("-fx-font-size: 11px; -fx-text-fill: #94a3b8; -fx-font-weight: bold;");

        VBox brandBox = new VBox(2, lblBrand, lblSubBrand);
        brandBox.setPadding(new Insets(0, 0, 20, 10));

        btnInicio = crearBotonMenu("🏠  Inicio");
        btnContactos = crearBotonMenu("📇  Gestión Contactos");

        // Panel con datos del usuario activo
        VBox userProfileBox = new VBox(4);
        userProfileBox.setPadding(new Insets(12));
        userProfileBox.setStyle("-fx-background-color: #334155; -fx-background-radius: 8;");

        Label lblUserNom = new Label(usuarioLogueado != null ? usuarioLogueado.getUsername() : "Invitado");
        lblUserNom.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 13px;");

        String rol = (usuarioLogueado != null) ? usuarioLogueado.getRol() : "VER";
        Label lblUserRol = new Label("Rol: " + rol);
        lblUserRol.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 11px;");

        userProfileBox.getChildren().addAll(lblUserNom, lblUserRol);

        // Botón Regresar / Cerrar Sesión
        btnCerrarSesion = new Button("🚪  Cerrar Sesión");
        btnCerrarSesion.setMaxWidth(Double.MAX_VALUE);
        btnCerrarSesion.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10; -fx-background-radius: 6;");
        btnCerrarSesion.setOnAction(e -> {
            try {
                new LoginView().start(stagePrincipal);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        menuLateral.getChildren().addAll(brandBox, btnInicio, btnContactos, spacer, userProfileBox, btnCerrarSesion);
        root.setLeft(menuLateral);

        contenedorCards = new VBox(15);
        contenedorCards.setPadding(new Insets(25));

        // Formulario e Inputs
        txtNombre = crearTextField("Nombre");
        txtApellido = crearTextField("Apellido");
        txtTelefono = crearTextField("Teléfono");
        txtCorreo = crearTextField("Correo electrónico");
        txtMensaje = crearTextField("Mensaje...");

        btnGuardarContacto = new Button("Guardar Contacto");
        btnGuardarContacto.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 18 8 18; -fx-background-radius: 6;");

        btnCancelarEdicion = new Button("Cancelar");
        btnCancelarEdicion.setStyle("-fx-background-color: #64748b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 18 8 18; -fx-background-radius: 6;");
        btnCancelarEdicion.setVisible(false);

        lblTituloForm = new Label("Agregar Nuevo Contacto");
        lblTituloForm.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        // Barra de Búsqueda
        txtBuscar = crearTextField("Buscar por nombre, apellido o correo...");
        txtBuscar.setPrefWidth(320);

        btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #0284c7; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 16 8 16; -fx-background-radius: 6;");

        btnLimpiarBusqueda = new Button("Limpiar");
        btnLimpiarBusqueda.setStyle("-fx-background-color: #94a3b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 16 8 16; -fx-background-radius: 6;");

        // Botón Eliminar Todos
        btnEliminarTodos = new Button("⚠️ Eliminar Todos los Contactos");
        btnEliminarTodos.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 16 8 16; -fx-background-radius: 6;");

        mostrarInicio();

        new MainController(this);

        Scene scene = new Scene(root, 1020, 680);
        stage.setTitle("AGROFRUTOS LAMBAYEQUE - Panel de Control");
        stage.setScene(scene);
        stage.show();
    }

    private Button crearBotonMenu(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPrefHeight(42);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #cbd5e1; -fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 6; -fx-padding: 0 0 0 15;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #334155; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 6; -fx-padding: 0 0 0 15;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #cbd5e1; -fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 6; -fx-padding: 0 0 0 15;"));
        return btn;
    }

    private TextField crearTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");
        return tf;
    }

    public void mostrarInicio() {
        VBox inicioBox = new VBox(15);
        inicioBox.setAlignment(Pos.CENTER);
        inicioBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-padding: 40;");

        Label titulo = new Label("Bienvenido al Sistema Empresarial Agrofrutos");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        String rol = (usuarioLogueado != null) ? usuarioLogueado.getRol() : "VER";
        Label subtitulo = new Label("Módulo de Gestión de Contactos | Rol Actual: " + rol);
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");

        inicioBox.getChildren().addAll(titulo, subtitulo);

        VBox wrap = new VBox(inicioBox);
        wrap.setPadding(new Insets(40));
        root.setCenter(wrap);
    }

    public void mostrarContactos(List<Contacto> lista) {
        contenedorCards.getChildren().clear();

        Label titulo = new Label("Directorio de Contactos");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.getChildren().addAll(txtBuscar, btnBuscar, btnLimpiarBusqueda);

        String rol = (usuarioLogueado != null) ? usuarioLogueado.getRol() : "VER";

        if (rol.equalsIgnoreCase("ADMIN")) {
            Region space = new Region();
            HBox.setHgrow(space, Priority.ALWAYS);
            searchBox.getChildren().addAll(space, btnEliminarTodos);
        }

        contenedorCards.getChildren().addAll(titulo, searchBox);

        if (rol.equalsIgnoreCase("REGISTRAR") || rol.equalsIgnoreCase("ADMIN")) {
            contenedorCards.getChildren().add(crearFormulario());
        }

        if (!rol.equalsIgnoreCase("REGISTRAR")) {
            for (Contacto c : lista) {
                VBox card = new VBox(8);
                card.setPadding(new Insets(16));
                card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 5, 0, 0, 2);");

                Label nombre = new Label(c.getNombre() + " " + c.getApellido());
                nombre.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #1e293b;");

                Label datos = new Label("📞 " + c.getTelefono() + "   |   ✉ " + c.getCorreo());
                datos.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

                Label msg = new Label("💬 " + c.getMensaje());
                msg.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

                card.getChildren().addAll(nombre, datos, msg);

                if (rol.equalsIgnoreCase("ADMIN") || rol.equalsIgnoreCase("REGISTRAR")) {
                    Button btnEditar = new Button("Editar");
                    btnEditar.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12 4 12; -fx-background-radius: 4;");
                    btnEditar.setOnAction(e -> cargarContactoEnFormulario(c));

                    Button btnEliminar = new Button("Eliminar");
                    btnEliminar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12 4 12; -fx-background-radius: 4;");
                    btnEliminar.setUserData(c.getId());

                    HBox accionesBox = new HBox(8, btnEditar, btnEliminar);
                    accionesBox.setPadding(new Insets(5, 0, 0, 0));
                    card.getChildren().add(accionesBox);
                }

                contenedorCards.getChildren().add(card);
            }
        }

        ScrollPane scrollPane = new ScrollPane(contenedorCards);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f4f6f9;");

        root.setCenter(scrollPane);
    }

    private VBox crearFormulario() {
        VBox formContainer = new VBox(12);
        formContainer.setPadding(new Insets(18));
        formContainer.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-background-radius: 8;");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);

        grid.add(new Label("Apellido:"), 2, 0);
        grid.add(txtApellido, 3, 0);

        grid.add(new Label("Teléfono:"), 0, 1);
        grid.add(txtTelefono, 1, 1);

        grid.add(new Label("Correo:"), 2, 1);
        grid.add(txtCorreo, 3, 1);

        grid.add(new Label("Mensaje:"), 0, 2);
        grid.add(txtMensaje, 1, 2, 3, 1);

        HBox btnBox = new HBox(10, btnGuardarContacto, btnCancelarEdicion);

        formContainer.getChildren().addAll(lblTituloForm, grid, btnBox);
        return formContainer;
    }

    public void cargarContactoEnFormulario(Contacto c) {
        this.idContactoSeleccionado = c.getId();
        txtNombre.setText(c.getNombre());
        txtApellido.setText(c.getApellido());
        txtTelefono.setText(c.getTelefono());
        txtCorreo.setText(c.getCorreo());
        txtMensaje.setText(c.getMensaje());

        lblTituloForm.setText("Editar Contacto (ID: " + c.getId() + ")");
        btnGuardarContacto.setText("Actualizar Contacto");
        btnCancelarEdicion.setVisible(true);
    }

    public void limpiarFormulario() {
        this.idContactoSeleccionado = null;
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtMensaje.clear();

        lblTituloForm.setText("Agregar Nuevo Contacto");
        btnGuardarContacto.setText("Guardar Contacto");
        btnCancelarEdicion.setVisible(false);
    }

    public Button getBtnInicio() { return btnInicio; }
    public Button getBtnContactos() { return btnContactos; }
    public Button getBtnGuardarContacto() { return btnGuardarContacto; }
    public Button getBtnCancelarEdicion() { return btnCancelarEdicion; }
    public Button getBtnBuscar() { return btnBuscar; }
    public Button getBtnLimpiarBusqueda() { return btnLimpiarBusqueda; }
    public Button getBtnEliminarTodos() { return btnEliminarTodos; }

    public String getTxtNombre() { return txtNombre.getText(); }
    public String getTxtApellido() { return txtApellido.getText(); }
    public String getTxtTelefono() { return txtTelefono.getText(); }
    public String getTxtCorreo() { return txtCorreo.getText(); }
    public String getTxtMensaje() { return txtMensaje.getText(); }
    public String getTxtBuscar() { return txtBuscar.getText(); }

    public Integer getIdContactoSeleccionado() { return idContactoSeleccionado; }
    public VBox getContenedorCards() { return contenedorCards; }
}