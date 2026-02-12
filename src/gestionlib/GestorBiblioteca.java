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
                                    throws PrestamoInvalidoException, UsuarioSacionadoException, LibroNoDisponibleException{
        if (socio.estaSancionado()){
            throw new UsuarioSacionadoException("::ERROR:: El usuario no puede realizar prestamos con una sanción en curso.");
        }
        for (Prestamo p : prestamos){
            if (p.getTituloLibro().equalsIgnoreCase(tituloLibro.trim()) && p.getFechaDevolucionReal() == null){
                throw new LibroNoDisponibleException("::ERROR:: El libro no esta disponible");
            }
        }
        if (numeroPrestamos == MAX_PRESTAMOS){
            throw new PrestamoInvalidoException("::ERROR:: No se pueden realizar más prestamos.");
        }
        for (int i = 0; i <= numeroPrestamos + 1; i++){
            if (prestamos[i] != null){
                prestamos[i] = new Prestamo(codigoLibro, socio, tituloLibro, fechaPrestamo);
            }
        }
    }

    public boolean devolverLibro(String codigoLibro, LocalDate fechaDevolucion) throws PrestamoInvalidoException{
        int diasRetraso;
        for (Prestamo p : prestamos){
            if (p.getCodigoLibro().equals(codigoLibro) && fechaDevolucion.isBefore(p.getFechaPrestamo())){
                throw new PrestamoInvalidoException("::ERROR:: La fecha de devolucion es anterior a la del prestamo.");
            }
            if (p.getCodigoLibro().equals(codigoLibro)){
                if (p.estaRetrasado()){
                    diasRetraso = p.calcularDiasRetraso();
                }
            }
        }

    }

    public Usuario buscarSocio(String codigoSocio){
        for (int i = 0; i <= numeroUsuarios + 1; i++){
            if (usuarios[i].getNumeroSocio().equals(codigoSocio)){
                return usuarios[i];
            }
        }
        return null;
    }
}
