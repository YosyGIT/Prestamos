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

    public Prestamo[] getPrestamos() {
        return prestamos;
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public void registrarUsuario(Usuario socio) throws UsuarioRepetidoException{
        if (numeroUsuarios < MAX_USUARIOS){
            for (int i = 0; i <= numeroUsuarios; i++){
                if (socio.equals(this.usuarios[i])){
                    throw new UsuarioRepetidoException("::ERROR:: El usuario ya se encuntra en la base de datos.");
                }
            }

            usuarios[numeroUsuarios] = socio;
            numeroUsuarios++;
        }else {
            throw new UsuarioRepetidoException("::ERROR:: Limite de usuarios alcanzado.");
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

        prestamos[numeroPrestamos] = new Prestamo(codigoLibro, socio, tituloLibro, fechaPrestamo);
        numeroPrestamos++;
    }

    public boolean devolverLibro(String codigoLibro, LocalDate fechaDevolucion) throws PrestamoInvalidoException, UsuarioInvalidoException {
        for (Prestamo p : prestamos){
            if (p.getCodigoLibro().equals(codigoLibro) && fechaDevolucion.isBefore(p.getFechaPrestamo())){
                throw new PrestamoInvalidoException("::ERROR:: La fecha de devolucion es anterior a la del prestamo.");
            }
            if (p.getCodigoLibro().equals(codigoLibro)){
                if (p.estaRetrasado()){
                    p.registrarDevolucion(fechaDevolucion);
                    p.getSocio().sancionar(p.calcularDiasRetraso(), fechaDevolucion);
                    return true;
                } else{
                    p.registrarDevolucion(fechaDevolucion);
                    return true;
                }
            }
        }
        return false;
    }

    public Usuario buscarSocio(String codigoSocio){
        for (int i = 0; i <= numeroUsuarios; i++){
            if (usuarios[i].getNumeroSocio().equals(codigoSocio)){
                return usuarios[i];
            }
        }
        return null;
    }

    @Override
    public String toString(){
        String respuesta = "-::USUARIOS::-";
        for (Usuario u : usuarios){
            if (u != null){
                respuesta += "\n" + u.toString() + "\n";
            }
        }

        respuesta += "\n-::PRESTAMOS::-";
        for (Prestamo p : prestamos){
            if (p != null){
                respuesta += "\n" + p.toString() + "\n";
            }
        }

        return respuesta;
    }
}
