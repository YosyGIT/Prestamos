package gestionlib;
import java.time.LocalDate;
public class Usuario {
    private String nombre;
    private String email;
    private String regEmail = "[A-Za-z0-9]+@[a-z0-9].(com|es)";
    private String numeroSocio;
    private String regNumSocio = "SOC";
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion;
    private static int nSocio = 0;

    public Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro){
        this.nombre = nombre;

        if (email.matches(this.regEmail)){
            this.email = email;
        }else {
            throw new IllegalArgumentException("::ERROR:: El correo esta mal introducido, reviselo e intentelo de nuevo.");
        }
    }
}
