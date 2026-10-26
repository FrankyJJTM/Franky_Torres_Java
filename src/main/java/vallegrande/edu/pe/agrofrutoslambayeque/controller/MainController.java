package vallegrande.edu.pe.agrofrutoslambayeque.controller;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import vallegrande.edu.pe.agrofrutoslambayeque.model.Contacto;
import vallegrande.edu.pe.agrofrutoslambayeque.model.ContactoDao;
import vallegrande.edu.pe.agrofrutoslambayeque.view.MainView;

import java.util.Optional;

public class MainController {

    private MainView view;
    private ContactoDao dao;

    public MainController(MainView view) {
        this.view = view;
        this.dao = new ContactoDao();
        configurarEventos();
    }

    private void configurarEventos() {
        view.getBtnInicio().setOnAction(e -> view.mostrarInicio());
        view.getBtnContactos().setOnAction(e -> cargarListaContactos());

        view.getBtnGuardarContacto().setOnAction(e -> guardarOActualizarContacto());
        view.getBtnCancelarEdicion().setOnAction(e -> view.limpiarFormulario());

        view.getBtnBuscar().setOnAction(e -> buscarContactos());
        view.getBtnLimpiarBusqueda().setOnAction(e -> cargarListaContactos());

        // Evento para Eliminar Todos los contactos
        if (view.getBtnEliminarTodos() != null) {
            view.getBtnEliminarTodos().setOnAction(e -> eliminarTodosLosContactos());
        }
    }

    private void cargarListaContactos() {
        view.mostrarContactos(dao.listarContactos());
        asignarEventosEliminar();
    }

    private void guardarOActualizarContacto() {
        String nombre = view.getTxtNombre().trim();
        String apellido = view.getTxtApellido().trim();
        String telefono = view.getTxtTelefono().trim();
        String correo = view.getTxtCorreo().trim();
        String mensaje = view.getTxtMensaje().trim();

        if (nombre.isEmpty() || correo.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor ingresa al menos el nombre y el correo.");
            return;
        }

        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setApellido(apellido);
        contacto.setTelefono(telefono);
        contacto.setCorreo(correo);
        contacto.setMensaje(mensaje);

        boolean exito;
        Integer idSeleccionado = view.getIdContactoSeleccionado();

        if (idSeleccionado == null) {
            exito = dao.agregarContacto(contacto);
        } else {
            contacto.setId(idSeleccionado);
            exito = dao.actualizarContacto(contacto);
        }

        if (exito) {
            String msg = (idSeleccionado == null) ? "El contacto se ha registrado correctamente." : "El contacto se ha actualizado correctamente.";
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", msg);
            view.limpiarFormulario();
            cargarListaContactos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo procesar la solicitud en la base de datos.");
        }
    }

    private void buscarContactos() {
        String criterio = view.getTxtBuscar().trim();
        if (criterio.isEmpty()) {
            cargarListaContactos();
            return;
        }
        view.mostrarContactos(dao.buscarContactos(criterio));
        asignarEventosEliminar();
    }

    private void asignarEventosEliminar() {
        for (Node node : view.getContenedorCards().getChildren()) {
            if (node instanceof VBox) {
                VBox card = (VBox) node;
                Node ultimoNodo = card.getChildren().get(card.getChildren().size() - 1);
                if (ultimoNodo instanceof javafx.scene.layout.HBox) {
                    javafx.scene.layout.HBox accionesBox = (javafx.scene.layout.HBox) ultimoNodo;
                    for (Node btnNode : accionesBox.getChildren()) {
                        if (btnNode instanceof Button) {
                            Button btn = (Button) btnNode;
                            if ("Eliminar".equals(btn.getText()) && btn.getUserData() != null) {
                                int id = (int) btn.getUserData();
                                btn.setOnAction(e -> eliminarContacto(id));
                            }
                        }
                    }
                }
            }
        }
    }

    private void eliminarContacto(int id) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Estás seguro de que deseas eliminar este contacto?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean exito = dao.eliminarContacto(id);
            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminado", "El contacto fue eliminado con éxito.");
                cargarListaContactos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el contacto.");
            }
        }
    }

    private void eliminarTodosLosContactos() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Eliminación masiva");
        confirm.setHeaderText("⚠️ ¿Deseas eliminar TODOS los contactos?");
        confirm.setContentText("Esta acción eliminará todos los registros de la base de datos permanentemente.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean exito = dao.eliminarTodos();
            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Directorio Vacío", "Se han eliminado todos los contactos correctamente.");
                cargarListaContactos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un problema al intentar eliminar los contactos.");
            }
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}