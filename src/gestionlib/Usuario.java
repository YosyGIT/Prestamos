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
    private static int nSocio = 1;

    public Usuario(String nombre, String email, LocalDate fechaRegistro) throws UsuarioInvalidoException {
        this.nombre = nombre;

        if (email.matches(this.regEmail)){
            this.email = email;
        }else {
            throw new UsuarioInvalidoException("::ERROR:: El correo no cumple con los requisitos.");
        }

        if (nSocio < 10){
            this.numeroSocio = regNumSocio + "0000" + nSocio;
        } else if (nSocio < 100) {
            this.numeroSocio = regNumSocio + "000" + nSocio;
        } else if (nSocio < 1000) {
            this.numeroSocio = regNumSocio + "00" + nSocio;
        } else if (nSocio < 10000) {
            this.numeroSocio = regNumSocio + nSocio;
        } else if (nSocio == 99999) {
            throw new UsuarioInvalidoException("::ERROR:: Limite de usuarios alcanzado.");
        }
        nSocio++;

        this.fechaRegistro = fechaRegistro;
    }

    public void sancionar(int tiempoSancion, LocalDate inicio){
        this.sancionado = true;
        this.fechaFinSancion = inicio.plusDays(tiempoSancion);
    }

    public void levantarSancion(){
        this.sancionado = false;
        this.fechaFinSancion = null;
    }

    public boolean estaSancionado(){
        return sancionado;
    }

    @Override
    public String toString(){
        return "";
    }
}
