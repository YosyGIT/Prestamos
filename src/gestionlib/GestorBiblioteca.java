package gestionlib;
import java.time.LocalDate;
public class GestorBiblioteca {
    private static final int MAX_USUARIOS = 50;
    private static final int MAX_PRESTAMOS = 200;
    private Usuario[] usuarios;
    private Prestamo[] prestamos;
    private int numeroUsuarios = 0;
    private int numeroPrestamos = 0;

    public GestorBiblioteca(){
        this.usuarios = new Usuario[MAX_USUARIOS];
        this.prestamos = new Prestamo[MAX_PRESTAMOS];
    }

    public void registrarUsuario(Usuario socio) throws UsuarioRepetidoException{
        for (int i = 0; i <= numeroUsuarios; i++){
            if (socio.equals(this.usuarios)){
                throw new UsuarioRepetidoException("::ERROR:: El usuario ya se encuntra en la base de datos.");
            }
        }

        for (int i = 0; i <= numeroUsuarios + 1; i++){
            if (this.usuarios[i] == null){
                this.usuarios[i] = socio;
            }
        }
    }

    public void realizarPrestamo(String codigoLibro, String tituloLibro, LocalDate fechaPrestamo, Usuario socio)
                                    throws PrestamoInvalidoException, UsuarioRepetidoException, LibroNoDisponibleException{
        final String regCodigoLibro = "LIB[0-9]{4}";
        if (!codigoLibro.matches(regCodigoLibro)){
            throw new PrestamoInvalidoException("::ERROR:: El formato del codigo del libro es incorrecto.");
        }

        if (tituloLibro.trim().isEmpty()){
            throw new PrestamoInvalidoException("::ERROR:: No puede ser un campo vacio.");
        }
    }
}
