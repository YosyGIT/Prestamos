package gestionlib;
import java.time.LocalDate;

/**
 * @author YosyGIT
 */
public class GestorBiblioteca {
    private static final int MAX_USUARIOS = 50;
    private static final int MAX_PRESTAMOS = 200;
    private Usuario[] usuarios;
    private Prestamo[] prestamos;
    private int numeroUsuarios = 0;
    private int numeroPrestamos = 0;

    /**
     * Constructor que genera dos arrays de tipo Usuario y Prestamo con un tamaño obligatorio
     */
    public GestorBiblioteca(){
        this.usuarios = new Usuario[MAX_USUARIOS];
        this.prestamos = new Prestamo[MAX_PRESTAMOS];
    }

    /**
     * Metodos get para poder llamar a los datos de la clase
     * @return Devuelven el dato que quieras obtener
     */
    public Prestamo[] getPrestamos() {
        return prestamos;
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    /**
     * Metodo que permite registrar usuarios en el array, comprueba que el socio no este incluido ya y el limite del array
     * @param socio Necesitas un objeto de tipo Usuario creado anteriormente
     *     EXCEPCIONES DEL METODO:
     * @throws UsuarioRepetidoException
     */
    public void registrarUsuario(Usuario socio) throws UsuarioRepetidoException{
        if (numeroUsuarios < MAX_USUARIOS){
            for (int i = 0; i < numeroUsuarios; i++){
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

    /**
     * Metodo que permite realizar prestamos creandolos y registrandolos en el array de prestamos, comprueba que el usuario
     * no este sancionado, que el libro que desean prestar este disponible y que no exceda el limite del array
     * @param codigoLibro Codigo del libro que desea prestar
     * @param tituloLibro Titulo del libro que desea prestar
     * @param fechaPrestamo Fecha del prestamo
     * @param socio Usuario que desea realizar el prestamo
     *     EXCEPCIONES DEL METODO:
     * @throws PrestamoInvalidoException
     * @throws UsuarioSacionadoException
     * @throws LibroNoDisponibleException
     */
    public void realizarPrestamo(String codigoLibro, String tituloLibro, LocalDate fechaPrestamo, Usuario socio)
                                    throws PrestamoInvalidoException, UsuarioSacionadoException, LibroNoDisponibleException{
        if (socio.estaSancionado()){
            throw new UsuarioSacionadoException("::ERROR:: El usuario no puede realizar prestamos con una sanción en curso.");
        }
        for (Prestamo p : prestamos){
            if (p != null && p.getTituloLibro().equalsIgnoreCase(tituloLibro.trim()) && p.getFechaDevolucionReal() == null){
                throw new LibroNoDisponibleException("::ERROR:: El libro no esta disponible");
            }
        }
        if (numeroPrestamos == MAX_PRESTAMOS){
            throw new PrestamoInvalidoException("::ERROR:: No se pueden realizar más prestamos.");
        }

        prestamos[numeroPrestamos] = new Prestamo(codigoLibro, socio, tituloLibro, fechaPrestamo);
        numeroPrestamos++;
    }

    /**
     * Metodo usado para devolver el libro prestado
     * @param codigoLibro Codigo del libro prestado anteriormente
     * @param fechaDevolucion Fecha de devolución real del libro
     * @return Devuelve true en caso de realizarse el prestamo, independientemente de si es con retraso o false
     * en el caso de no encontrar prestamos con el mismo codigo de libro
     *     EXCEPCIONES DEL METODO:
     * @throws PrestamoInvalidoException
     * @throws UsuarioInvalidoException
     */
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

    /**
     * Metodo usado para buscar un socio con su codigo de socio
     * @param codigoSocio Codigo de socio que desea buscar
     * @return Devuelve el socio o null en caso de no encontrarse
     */
    public Usuario buscarSocio(String codigoSocio){
        for (int i = 0; i < numeroUsuarios; i++){
            if (usuarios[i].getNumeroSocio().equals(codigoSocio)){
                return usuarios[i];
            }
        }
        return null;
    }

    /**
     * Metodo toString personalizado con los usuarios y prestamos creados
     * @return Devuelve la cadena de testo con todos los datos
     */
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
