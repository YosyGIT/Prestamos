package gestionlib;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author YosyGIT
 */
public class Usuario {
    private String nombre;
    private String email;
    private static final String regEmail = "[A-Za-z0-9]+@[a-z0-9]+\\.(com|es|COM|ES)";
    private String numeroSocio;
    private static final String regNumSocio = "SOC[0-9]{5}";
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion;

    /**
     * Constructor que crea al usuario comprobando el formato del email y el numero de socio
     * @param nombre Nombre del socio
     * @param email Email del socio
     * @param numeroSocio Numero de socio
     * @param fechaRegistro Fecha de registro
     *     EXCEPCIONES DEL METODO:
     * @throws UsuarioInvalidoException
     */
    public Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro) throws UsuarioInvalidoException {
        if (!email.matches(regEmail) && !numeroSocio.matches(regNumSocio)){
            throw new UsuarioInvalidoException("::ERROR:: El formato del email y el numero de socio son incorrectos.");
        }
        if (!email.matches(regEmail)){
            throw new UsuarioInvalidoException("::ERROR:: El formato del correo es incorrecto.");
        }
        this.email = email;
        this.nombre = nombre;

        if (!numeroSocio.matches(regNumSocio)){
            throw new UsuarioInvalidoException("::ERROR:: El formato del número de socio es incorrecto.");
        }
        this.numeroSocio = numeroSocio;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Metodos get para poder llamar a los datos de la clase
     * @return Devuelven el dato que quieras obtener
     */
    public String getNombre() {
        return nombre;
    }

    public String getNumeroSocio(){
        return numeroSocio;
    }

    public LocalDate getFechaFinSancion() {
        return fechaFinSancion;
    }

    /**
     * Metodo que permite sancionar al usuario, comprueba si el usuario ya tiene una sancion
     * @param tiempoSancion Tiempo de sancion que deseamos darle
     * @param inicio Fecha de inicio de la sanción
     *     EXCEPCIONES DEL METODO:
     * @throws UsuarioInvalidoException
     */
    public void sancionar(int tiempoSancion, LocalDate inicio) throws UsuarioInvalidoException{
        if (sancionado){
            throw new UsuarioInvalidoException("::ERROR:: El usuario ya tiene una sancion");
        }
        this.sancionado = true;
        this.fechaFinSancion = inicio.plusDays(tiempoSancion);
    }

    /**
     * Metodo que permite levantar una sancion, en caso de no tener el usuario ninguna sanción lanzara una excepción
     *     EXCEPCIONES DEL METODO:
     * @throws UsuarioInvalidoException
     */
    public void levantarSancion() throws UsuarioInvalidoException{
        if (!sancionado){
            throw new UsuarioInvalidoException("::ERROR:: El usuario no tiene ninguna sancion");
        }
        this.sancionado = false;
        this.fechaFinSancion = null;
    }

    /**
     * Metodo que permite comprobar si el usuario esta sancionado
     * @return Devuelve true en caso de estar sancionado y false en caso de no estarlo
     */
    public boolean estaSancionado(){
        return sancionado;
    }

    /**
     * Metodo toString personalizado con los datos del usuario
     * @return Devuelve una cadena de texto con todos sus datos
     */
    @Override
    public String toString(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("NOMBRE: " + this.nombre +
                "\nEMAIL: " + this.email +
                "\nNºSOCIO: " + this.numeroSocio +
                "\nFECHA DE REGISTRO: " + this.fechaRegistro.format(formato));
    }
}
