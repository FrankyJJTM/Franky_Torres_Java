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
import vallegrande.edu.pe.agrofrutoslambayeque.model.*;

public class MainView extends Application {

    private Button btnInicio;
    private Button btnContactos;
    private Button btnClientes;
    private Button btnSocios;
    private Button btnProductos;
    private Button btnCerrarSesion;
    private Button btnEliminarTodos;

    private VBox menuLateral;
    private VBox contenedorCards;
    private BorderPane root;

    // --- CONTROLES DE CONTACTOS ---
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

    // --- CONTROLES DE CLIENTES (Customer) ---
    private TextField txtCliNombre = crearTextField("Nombre");
    private TextField txtCliApellido = crearTextField("Apellido");
    private TextField txtCliDoc = crearTextField("DNI o RUC");
    private TextField txtCliTelefono = crearTextField("Teléfono");
    private TextField txtCliCorreo = crearTextField("Correo electrónico");
    private TextField txtCliDireccion = crearTextField("Dirección");
    private CheckBox chkCliActivo = new CheckBox("Cliente Activo");
    private Button btnGuardarCliente = new Button("Guardar Cliente");
    private Integer idClienteSeleccionado = null;

    // --- CONTROLES DE SOCIOS (Partner) ---
    private TextField txtSocNombres = crearTextField("Nombres y Apellidos");
    private TextField txtSocDniRuc = crearTextField("DNI o RUC");
    private TextField txtSocCodigo = crearTextField("Código de Socio");
    private TextField txtSocTelefono = crearTextField("Teléfono");
    private TextField txtSocCertificacion = crearTextField("Estado Certificación");
    private Button btnGuardarSocio = new Button("Guardar Socio");
    private Integer idSocioSeleccionado = null;

    // --- CONTROLES DE PRODUCTOS (Product) ---
    private TextField txtProNombre = crearTextField("Nombre del Producto");
    private TextField txtProVariedad = crearTextField("Variedad");
    private TextField txtProCertificacion = crearTextField("Tipo de Certificación");
    private Button btnGuardarProducto = new Button("Guardar Producto");
    private Integer idProductoSeleccionado = null;

    private Usuario usuarioLogueado;
    private Stage stagePrincipal;

    public MainView() {
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

        // --- SIDEBAR MODERNO ---
        menuLateral = new VBox(12);
        menuLateral.setPadding(new Insets(25, 15, 25, 15));
        menuLateral.setPrefWidth(240);
        menuLateral.setStyle("-fx-background-color: #1e293b;");

        Label lblBrand = new Label("AGROFRUTOS");
        lblBrand.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #10b981;");

        Label lblSubBrand = new Label("LAMBAYEQUE S.A.C.");
        lblSubBrand.setStyle("-fx-font-size: 11px; -fx-text-fill: #94a3b8; -fx-font-weight: bold;");

        VBox brandBox = new VBox(2, lblBrand, lblSubBrand);
        brandBox.setPadding(new Insets(0, 0, 20, 10));

        btnInicio = crearBotonMenu("🏠  Inicio");
        btnContactos = crearBotonMenu("📇  Gestión Contactos");
        btnClientes = crearBotonMenu("👥  Gestión Clientes");
        btnSocios = crearBotonMenu("🌾  Gestión Socios");
        btnProductos = crearBotonMenu("📦  Gestión Productos");

        // Perfil de Usuario Activo
        VBox userProfileBox = new VBox(4);
        userProfileBox.setPadding(new Insets(12));
        userProfileBox.setStyle("-fx-background-color: #334155; -fx-background-radius: 8;");

        Label lblUserNom = new Label(usuarioLogueado != null ? usuarioLogueado.getUsername() : "Invitado");
        lblUserNom.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 13px;");

        String rol = (usuarioLogueado != null && usuarioLogueado.getRol() != null) ? usuarioLogueado.getRol() : "VER";
        Label lblUserRol = new Label("Rol: " + rol);
        lblUserRol.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 11px;");

        userProfileBox.getChildren().addAll(lblUserNom, lblUserRol);

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

        // Construir Menú dinámico evaluando el Rol
        menuLateral.getChildren().addAll(brandBox, btnInicio);

        if (rol.equals("ADMIN") || rol.equals("CONTACTS")) {
            menuLateral.getChildren().add(btnContactos);
        }
        if (rol.equals("ADMIN") || rol.equals("CUSTOMERS")) {
            menuLateral.getChildren().add(btnClientes);
        }
        if (rol.equals("ADMIN") || rol.equals("PARTNERS")) {
            menuLateral.getChildren().add(btnSocios);
        }
        if (rol.equals("ADMIN") || rol.equals("PRODUCTS")) {
            menuLateral.getChildren().add(btnProductos);
        }

        menuLateral.getChildren().addAll(spacer, userProfileBox, btnCerrarSesion);
        root.setLeft(menuLateral);

        contenedorCards = new VBox(15);
        contenedorCards.setPadding(new Insets(25));

        // Formulario de Contactos
        txtNombre = crearTextField("Nombre");
        txtApellido = crearTextField("Apellido");
        txtTelefono = crearTextField("Teléfono");
        txtCorreo = crearTextField("Correo electrónico");
        txtMensaje = crearTextField("Mensaje...");

        btnGuardarContacto = new Button("Guardar Contacto");
        btnGuardarContacto.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 18; -fx-background-radius: 6;");

        btnCancelarEdicion = new Button("Cancelar");
        btnCancelarEdicion.setStyle("-fx-background-color: #64748b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 18; -fx-background-radius: 6;");
        btnCancelarEdicion.setVisible(false);

        lblTituloForm = new Label("Agregar Nuevo Contacto");
        lblTituloForm.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        txtBuscar = crearTextField("Buscar por nombre, apellido o correo...");
        txtBuscar.setPrefWidth(320);

        btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #0284c7; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 16; -fx-background-radius: 6;");

        btnLimpiarBusqueda = new Button("Limpiar");
        btnLimpiarBusqueda.setStyle("-fx-background-color: #94a3b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 16; -fx-background-radius: 6;");

        btnEliminarTodos = new Button("⚠️ Eliminar Todos los Contactos");
        btnEliminarTodos.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 16; -fx-background-radius: 6;");

        // Eventos de Navegación del Menú
        btnInicio.setOnAction(e -> mostrarInicio());
        btnClientes.setOnAction(e -> {
            limpiarCamposCliente();
            mostrarClientes(new CustomerDao().listar());
        });
        btnSocios.setOnAction(e -> {
            limpiarCamposSocio();
            mostrarSocios(new PartnerDao().listar());
        });
        btnProductos.setOnAction(e -> {
            limpiarCamposProducto();
            mostrarProductos(new ProductDao().listar());
        });

        // Eventos Guardar/Actualizar
        btnGuardarCliente.setOnAction(e -> {
            Customer c = new Customer();
            c.setName(txtCliNombre.getText());
            c.setLastName(txtCliApellido.getText());
            c.setDocumentNumber(txtCliDoc.getText());
            c.setPhone(txtCliTelefono.getText());
            c.setMail(txtCliCorreo.getText());
            c.setAddress(txtCliDireccion.getText());
            c.setActive(chkCliActivo.isSelected());

            if (idClienteSeleccionado != null) {
                c.setCustomerId(idClienteSeleccionado);
                new CustomerDao().actualizar(c);
            } else {
                new CustomerDao().guardar(c);
            }
            limpiarCamposCliente();
            mostrarClientes(new CustomerDao().listar());
        });

        btnGuardarSocio.setOnAction(e -> {
            Partner p = new Partner();
            p.setFirstNamesLastNames(txtSocNombres.getText());
            p.setDniRuc(txtSocDniRuc.getText());
            p.setMemberCode(txtSocCodigo.getText());
            p.setPhone(txtSocTelefono.getText());
            p.setCertificationStatus(txtSocCertificacion.getText());

            if (idSocioSeleccionado != null) {
                p.setMemberId(idSocioSeleccionado);
                new PartnerDao().actualizar(p);
            } else {
                new PartnerDao().guardar(p);
            }
            limpiarCamposSocio();
            mostrarSocios(new PartnerDao().listar());
        });

        btnGuardarProducto.setOnAction(e -> {
            Product p = new Product();
            p.setProductName(txtProNombre.getText());
            p.setVariety(txtProVariedad.getText());
            p.setCertificationType(txtProCertificacion.getText());

            if (idProductoSeleccionado != null) {
                p.setProductId(idProductoSeleccionado);
                new ProductDao().actualizar(p);
            } else {
                new ProductDao().guardar(p);
            }
            limpiarCamposProducto();
            mostrarProductos(new ProductDao().listar());
        });

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

    private HBox crearHeaderModulo(String tituloTexto) {
        Label titulo = new Label(tituloTexto);
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnVolver = new Button("← Volver a Inicio");
        btnVolver.setStyle("-fx-background-color: #475569; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 16; -fx-background-radius: 6;");
        btnVolver.setOnAction(e -> mostrarInicio());

        HBox header = new HBox(10, titulo, spacer, btnVolver);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    public void mostrarInicio() {
        VBox contenedorInicio = new VBox(25);
        contenedorInicio.setAlignment(Pos.TOP_CENTER);
        contenedorInicio.setPadding(new Insets(30));

        Label titulo = new Label("Bienvenido al Sistema Empresarial Agrofrutos");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        String rol = (usuarioLogueado != null && usuarioLogueado.getRol() != null) ? usuarioLogueado.getRol() : "VER";
        Label subtitulo = new Label("Seleccione el módulo CRUD al que desea acceder (Rol Activo: " + rol + ")");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");

        VBox headerBox = new VBox(6, titulo, subtitulo);
        headerBox.setAlignment(Pos.CENTER);

        GridPane gridModulos = new GridPane();
        gridModulos.setHgap(20);
        gridModulos.setVgap(20);
        gridModulos.setAlignment(Pos.CENTER);

        VBox cardContactos = crearCardModulo("📇 Módulo Contactos", "Gestión de mensajes y contactos del sitio web.", "#0284c7", () -> {
            mostrarContactos(new ContactoDao().listarContactos());
        });

        VBox cardClientes = crearCardModulo("👥 Módulo Clientes", "Registro y control de clientes de la empresa.", "#10b981", () -> {
            limpiarCamposCliente();
            mostrarClientes(new CustomerDao().listar());
        });

        VBox cardSocios = crearCardModulo("🌾 Módulo Socios", "Administración de socios agricultores y certificados.", "#f59e0b", () -> {
            limpiarCamposSocio();
            mostrarSocios(new PartnerDao().listar());
        });

        VBox cardProductos = crearCardModulo("📦 Módulo Productos", "Catálogo y variedades de productos agrícolas.", "#8b5cf6", () -> {
            limpiarCamposProducto();
            mostrarProductos(new ProductDao().listar());
        });

        if (rol.equals("ADMIN") || rol.equals("CONTACTS")) gridModulos.add(cardContactos, 0, 0);
        if (rol.equals("ADMIN") || rol.equals("CUSTOMERS")) gridModulos.add(cardClientes, 1, 0);
        if (rol.equals("ADMIN") || rol.equals("PARTNERS")) gridModulos.add(cardSocios, 0, 1);
        if (rol.equals("ADMIN") || rol.equals("PRODUCTS")) gridModulos.add(cardProductos, 1, 1);

        contenedorInicio.getChildren().addAll(headerBox, gridModulos);

        ScrollPane scrollPane = new ScrollPane(contenedorInicio);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f4f6f9;");

        root.setCenter(scrollPane);
    }

    private VBox crearCardModulo(String titulo, String descripcion, String colorHex, Runnable accion) {
        VBox card = new VBox(12);
        card.setPrefSize(320, 140);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-border-color: #e2e8f0; -fx-border-radius: 10; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 6, 0, 0, 2);");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + colorHex + ";");

        Label lblDesc = new Label(descripcion);
        lblDesc.setWrapText(true);
        lblDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");

        Button btnIngresar = new Button("Acceder al CRUD →");
        btnIngresar.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 12; -fx-background-radius: 6;");
        btnIngresar.setOnAction(e -> accion.run());

        card.getChildren().addAll(lblTitulo, lblDesc, btnIngresar);
        card.setOnMouseClicked(e -> accion.run());

        return card;
    }

    // --- MÓDULO CLIENTES ---
    public void mostrarClientes(List<Customer> lista) {
        contenedorCards.getChildren().clear();

        HBox header = crearHeaderModulo("Directorio de Clientes");

        VBox formContainer = new VBox(12);
        formContainer.setPadding(new Insets(18));
        formContainer.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-background-radius: 8;");

        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(12);
        grid.add(new Label("Nombre:"), 0, 0); grid.add(txtCliNombre, 1, 0);
        grid.add(new Label("Apellido:"), 2, 0); grid.add(txtCliApellido, 3, 0);
        grid.add(new Label("DNI/RUC:"), 0, 1); grid.add(txtCliDoc, 1, 1);
        grid.add(new Label("Teléfono:"), 2, 1); grid.add(txtCliTelefono, 3, 1);
        grid.add(new Label("Correo:"), 0, 2); grid.add(txtCliCorreo, 1, 2);
        grid.add(new Label("Dirección:"), 2, 2); grid.add(txtCliDireccion, 3, 2);

        chkCliActivo.setStyle("-fx-font-weight: bold; -fx-text-fill: #1e293b;");
        grid.add(chkCliActivo, 0, 3, 2, 1);

        btnGuardarCliente.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 18; -fx-background-radius: 6;");
        formContainer.getChildren().addAll(new Label("Gestión de Cliente"), grid, btnGuardarCliente);

        contenedorCards.getChildren().addAll(header, formContainer);

        for (Customer c : lista) {
            VBox card = new VBox(8);
            card.setPadding(new Insets(16));
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8;");

            // Título de la tarjeta
            Label nom = new Label(c.getName() + " " + c.getLastName() + " (Doc: " + c.getDocumentNumber() + ")");
            nom.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #1e293b;");

            // Indicador / Badge de Estado (ACTIVO / INACTIVO)
            Label lblEstado = new Label(c.isActive() ? "ACTIVO" : "INACTIVO");
            lblEstado.setStyle(c.isActive()
                    ? "-fx-background-color: #d1fae5; -fx-text-fill: #065f46; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 3 8; -fx-background-radius: 4;"
                    : "-fx-background-color: #fee2e2; -fx-text-fill: #991b1b; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 3 8; -fx-background-radius: 4;");

            Region spacerCard = new Region();
            HBox.setHgrow(spacerCard, Priority.ALWAYS);

            HBox titleBox = new HBox(10, nom, spacerCard, lblEstado);
            titleBox.setAlignment(Pos.CENTER_LEFT);

            Label datos = new Label("📞 " + c.getPhone() + "  |  ✉ " + c.getMail() + "  |  📍 " + c.getAddress());
            datos.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

            Button btnEditar = new Button("Editar");
            btnEditar.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12; -fx-background-radius: 4;");
            btnEditar.setOnAction(e -> {
                this.idClienteSeleccionado = c.getCustomerId();
                txtCliNombre.setText(c.getName());
                txtCliApellido.setText(c.getLastName());
                txtCliDoc.setText(c.getDocumentNumber());
                txtCliTelefono.setText(c.getPhone());
                txtCliCorreo.setText(c.getMail());
                txtCliDireccion.setText(c.getAddress());
                chkCliActivo.setSelected(c.isActive());
                btnGuardarCliente.setText("Actualizar Cliente");
            });

            Button btnEliminar = new Button("Desactivar");
            btnEliminar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12; -fx-background-radius: 4;");
            btnEliminar.setOnAction(e -> {
                new CustomerDao().eliminar(c.getCustomerId());
                limpiarCamposCliente();
                mostrarClientes(new CustomerDao().listar());
            });

            HBox accionesBox = new HBox(8, btnEditar, btnEliminar);
            card.getChildren().addAll(titleBox, datos, accionesBox);
            contenedorCards.getChildren().add(card);
        }

        ScrollPane scrollPane = new ScrollPane(contenedorCards);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f4f6f9;");
        root.setCenter(scrollPane);
    }

    // --- MÓDULO SOCIOS ---
    public void mostrarSocios(List<Partner> lista) {
        contenedorCards.getChildren().clear();

        HBox header = crearHeaderModulo("Directorio de Socios Agricultores");

        VBox formContainer = new VBox(12);
        formContainer.setPadding(new Insets(18));
        formContainer.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-background-radius: 8;");

        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(12);
        grid.add(new Label("Nombres/Apellidos:"), 0, 0); grid.add(txtSocNombres, 1, 0, 3, 1);
        grid.add(new Label("DNI/RUC:"), 0, 1); grid.add(txtSocDniRuc, 1, 1);
        grid.add(new Label("Código Socio:"), 2, 1); grid.add(txtSocCodigo, 3, 1);
        grid.add(new Label("Teléfono:"), 0, 2); grid.add(txtSocTelefono, 1, 2);
        grid.add(new Label("Certificación:"), 2, 2); grid.add(txtSocCertificacion, 3, 2);

        btnGuardarSocio.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 18; -fx-background-radius: 6;");
        formContainer.getChildren().addAll(new Label("Gestión de Socio"), grid, btnGuardarSocio);

        contenedorCards.getChildren().addAll(header, formContainer);

        for (Partner p : lista) {
            VBox card = new VBox(8);
            card.setPadding(new Insets(16));
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8;");

            Label nom = new Label(p.getFirstNamesLastNames() + " (Código: " + p.getMemberCode() + ")");
            nom.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #1e293b;");

            Label datos = new Label("🆔 DNI/RUC: " + p.getDniRuc() + "  |  📞 " + p.getPhone() + "  |  📜 Certificación: " + p.getCertificationStatus());
            datos.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

            Button btnEditar = new Button("Editar");
            btnEditar.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12; -fx-background-radius: 4;");
            btnEditar.setOnAction(e -> {
                this.idSocioSeleccionado = p.getMemberId();
                txtSocNombres.setText(p.getFirstNamesLastNames());
                txtSocDniRuc.setText(p.getDniRuc());
                txtSocCodigo.setText(p.getMemberCode());
                txtSocTelefono.setText(p.getPhone());
                txtSocCertificacion.setText(p.getCertificationStatus());
                btnGuardarSocio.setText("Actualizar Socio");
            });

            Button btnEliminar = new Button("Eliminar");
            btnEliminar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12; -fx-background-radius: 4;");
            btnEliminar.setOnAction(e -> {
                new PartnerDao().eliminar(p.getMemberId());
                limpiarCamposSocio();
                mostrarSocios(new PartnerDao().listar());
            });

            HBox accionesBox = new HBox(8, btnEditar, btnEliminar);
            card.getChildren().addAll(nom, datos, accionesBox);
            contenedorCards.getChildren().add(card);
        }

        ScrollPane scrollPane = new ScrollPane(contenedorCards);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f4f6f9;");
        root.setCenter(scrollPane);
    }

    // --- MÓDULO PRODUCTOS ---
    public void mostrarProductos(List<Product> lista) {
        contenedorCards.getChildren().clear();

        HBox header = crearHeaderModulo("Catálogo de Productos");

        VBox formContainer = new VBox(12);
        formContainer.setPadding(new Insets(18));
        formContainer.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-background-radius: 8;");

        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(12);
        grid.add(new Label("Producto:"), 0, 0); grid.add(txtProNombre, 1, 0);
        grid.add(new Label("Variedad:"), 2, 0); grid.add(txtProVariedad, 3, 0);
        grid.add(new Label("Certificación:"), 0, 1); grid.add(txtProCertificacion, 1, 1, 3, 1);

        btnGuardarProducto.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 18; -fx-background-radius: 6;");
        formContainer.getChildren().addAll(new Label("Gestión de Producto"), grid, btnGuardarProducto);

        contenedorCards.getChildren().addAll(header, formContainer);

        for (Product p : lista) {
            VBox card = new VBox(8);
            card.setPadding(new Insets(16));
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8;");

            Label nom = new Label("🍊 " + p.getProductName() + " - Variedad: " + p.getVariety());
            nom.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #1e293b;");

            Label datos = new Label("🏷 Certificación: " + p.getCertificationType());
            datos.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

            Button btnEditar = new Button("Editar");
            btnEditar.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12; -fx-background-radius: 4;");
            btnEditar.setOnAction(e -> {
                this.idProductoSeleccionado = p.getProductId();
                txtProNombre.setText(p.getProductName());
                txtProVariedad.setText(p.getVariety());
                txtProCertificacion.setText(p.getCertificationType());
                btnGuardarProducto.setText("Actualizar Producto");
            });

            Button btnEliminar = new Button("Eliminar");
            btnEliminar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12; -fx-background-radius: 4;");
            btnEliminar.setOnAction(e -> {
                new ProductDao().eliminar(p.getProductId());
                limpiarCamposProducto();
                mostrarProductos(new ProductDao().listar());
            });

            HBox accionesBox = new HBox(8, btnEditar, btnEliminar);
            card.getChildren().addAll(nom, datos, accionesBox);
            contenedorCards.getChildren().add(card);
        }

        ScrollPane scrollPane = new ScrollPane(contenedorCards);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f4f6f9;");
        root.setCenter(scrollPane);
    }

    // --- MÓDULO CONTACTOS ---
    public void mostrarContactos(List<Contacto> lista) {
        contenedorCards.getChildren().clear();

        HBox header = crearHeaderModulo("Directorio de Contactos");

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        searchBox.getChildren().addAll(txtBuscar, btnBuscar, btnLimpiarBusqueda, space, btnEliminarTodos);
        contenedorCards.getChildren().addAll(header, searchBox, crearFormulario());

        for (Contacto c : lista) {
            VBox card = new VBox(8);
            card.setPadding(new Insets(16));
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8;");

            Label nombre = new Label(c.getNombre() + " " + c.getApellido());
            nombre.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #1e293b;");

            Label datos = new Label("📞 " + c.getTelefono() + "   |   ✉ " + c.getCorreo());
            datos.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

            Label msg = new Label("💬 " + c.getMensaje());
            msg.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

            Button btnEditar = new Button("Editar");
            btnEditar.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12; -fx-background-radius: 4;");
            btnEditar.setOnAction(e -> cargarContactoEnFormulario(c));

            Button btnEliminar = new Button("Eliminar");
            btnEliminar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 4 12; -fx-background-radius: 4;");
            btnEliminar.setUserData(c.getId());

            HBox accionesBox = new HBox(8, btnEditar, btnEliminar);
            accionesBox.setPadding(new Insets(5, 0, 0, 0));

            card.getChildren().addAll(nombre, datos, msg, accionesBox);
            contenedorCards.getChildren().add(card);
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
        grid.setHgap(12); grid.setVgap(12);

        grid.add(new Label("Nombre:"), 0, 0); grid.add(txtNombre, 1, 0);
        grid.add(new Label("Apellido:"), 2, 0); grid.add(txtApellido, 3, 0);
        grid.add(new Label("Teléfono:"), 0, 1); grid.add(txtTelefono, 1, 1);
        grid.add(new Label("Correo:"), 2, 1); grid.add(txtCorreo, 3, 1);
        grid.add(new Label("Mensaje:"), 0, 2); grid.add(txtMensaje, 1, 2, 3, 1);

        HBox btnBox = new HBox(10, btnGuardarContacto, btnCancelarEdicion);

        formContainer.getChildren().addAll(lblTituloForm, grid, btnBox);
        return formContainer;
    }

    private void limpiarCamposCliente() {
        this.idClienteSeleccionado = null;
        txtCliNombre.clear();
        txtCliApellido.clear();
        txtCliDoc.clear();
        txtCliTelefono.clear();
        txtCliCorreo.clear();
        txtCliDireccion.clear();
        chkCliActivo.setSelected(true);
        btnGuardarCliente.setText("Guardar Cliente");
    }

    private void limpiarCamposSocio() {
        this.idSocioSeleccionado = null;
        txtSocNombres.clear();
        txtSocDniRuc.clear();
        txtSocCodigo.clear();
        txtSocTelefono.clear();
        txtSocCertificacion.clear();
        btnGuardarSocio.setText("Guardar Socio");
    }

    private void limpiarCamposProducto() {
        this.idProductoSeleccionado = null;
        txtProNombre.clear();
        txtProVariedad.clear();
        txtProCertificacion.clear();
        btnGuardarProducto.setText("Guardar Producto");
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
    public Button getBtnClientes() { return btnClientes; }
    public Button getBtnSocios() { return btnSocios; }
    public Button getBtnProductos() { return btnProductos; }
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