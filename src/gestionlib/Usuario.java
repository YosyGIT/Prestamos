package gestionlib;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario {
    private String nombre;
    private String email;
    private static final String regEmail = "[A-Za-z0-9]+@[a-z0-9]+\\.(com|es)";
    private String numeroSocio;
    private static final String regNumSocio = "SOC[0-9]{5}";
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion;

    public Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro) throws UsuarioInvalidoException {
        if (!email.matches(this.regEmail)){
            throw new UsuarioInvalidoException("::ERROR:: El formato del correo es incorrecto.");
        }
        this.email = email;
        this.nombre = nombre;

        if (!numeroSocio.matches(regNumSocio)){
            throw new UsuarioInvalidoException("::ERROR:: El formato del número de socio es incorrecto.")
        }
        this.numeroSocio = numeroSocio;
        this.fechaRegistro = fechaRegistro;
    }

    public void sancionar(int tiempoSancion, LocalDate inicio) throws UsuarioInvalidoException{
        if (sancionado){
            throw new UsuarioInvalidoException("::ERROR:: El usuario ya tiene una sancion");
        }
        this.sancionado = true;
        this.fechaFinSancion = inicio.plusDays(tiempoSancion);
    }

    public void levantarSancion() throws UsuarioInvalidoException{
        if (!sancionado){
            throw new UsuarioInvalidoException("::ERROR:: El usuario no tiene ninguna sancion");
        }
        this.sancionado = false;
        this.fechaFinSancion = null;
    }

    public boolean estaSancionado(){
        return sancionado;
    }

    @Override
    public String toString(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("NOMBRE: " + this.nombre +
                "\nEMAIL: " + this.email +
                "\nNºSOCIO: " + this.numeroSocio +
                "\nFECHA DE REGISTRO: " + this.fechaRegistro.format(formato));
    }
}
