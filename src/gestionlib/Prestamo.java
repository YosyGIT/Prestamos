package gestionlib;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author YosyGIT
 */
public class Prestamo {
    private String codigoLibro;
    private static final String regCodigoLibro = "LIB[0-9]{4}";
    private String tituloLibro;
    private Usuario socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;

    /**
     * Constructor que obliga a crear un objeto de tipo Prestamo con parametros y captura posibles errores
     * @param codigoLibro Codigo del libro con formato expecifico
     * @param socio Usuario que debe ser creado desde el menu
     * @param tituloLibro Titulo del libro
     * @param fechaPrestamo Fecha del prestamo que debe ser anterior o igual al día actual
     *     EXCEPCIONES DEL METODO:
     * @throws PrestamoInvalidoException
     */
    public Prestamo (String codigoLibro, Usuario socio, String tituloLibro, LocalDate fechaPrestamo)
                    throws PrestamoInvalidoException {
        if (!codigoLibro.matches(regCodigoLibro)){
            throw new PrestamoInvalidoException("::ERROR:: El formato del codigo del libro es incorrecto.");
        }
        this.codigoLibro = codigoLibro;

        if (socio == null){
            throw new PrestamoInvalidoException("::ERROR:: El socio no puede ser un campo vacio.");
        }
        this.socio = socio;

        if (tituloLibro.trim().isEmpty()){
            throw new PrestamoInvalidoException("::ERROR:: No puede ser un campo vacio.");
        }
        this.tituloLibro = tituloLibro.trim();

        if (fechaPrestamo == null){
            throw new PrestamoInvalidoException("::ERROR:: La fecha de prestamo no puede ser un campo vacio.");
        }
        if (fechaPrestamo.isAfter(LocalDate.now())){
            throw new PrestamoInvalidoException("::ERROR:: La fecha debe ser anterior o igual al día de hoy");
        }
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = this.fechaPrestamo.plusDays(14);
    }

    /**
     * Metodos get para poder llamar a los datos de la clase
     * @return Devuelven el dato que quieras obtener
     */
    public String getTituloLibro() {
        return tituloLibro;
    }

    public String getCodigoLibro() {
        return codigoLibro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public Usuario getSocio() {
        return socio;
    }

    /**
     * Metodo que permite registrar la entrega del libro
     * @param fechaDev Se introduce una fecha de devolucion, que debe ser posterior a la fecha del prestamo
     *     EXCEPCIONES DEL METODO:
     * @throws PrestamoInvalidoException
     */
    public void registrarDevolucion(LocalDate fechaDev) throws PrestamoInvalidoException{
        if (fechaDev == null){
            throw new PrestamoInvalidoException("::ERROR:: La fecha de devolucion no puede ser un campo vacio");
        }
        if (fechaDev.isBefore(fechaPrestamo)){
            throw new PrestamoInvalidoException("::ERROR:: La fecha de devolucion debe ser anterior a la de prestamo.");
        }
        this.fechaDevolucionReal = fechaDev;
    }

    /**
     * Metodo que ayuda a calcular el retraso que tiene la entrega en numero de dias
     * @return Devuelve un entero con todos los dias de retraso
     */
    public int calcularDiasRetraso(){
        int dias;
        if (fechaDevolucionReal == null){
            return (int)(ChronoUnit.DAYS.between(this.fechaPrestamo, LocalDate.now()));
        }
        dias = (int)(ChronoUnit.DAYS.between(this.fechaDevolucionPrevista, this.fechaDevolucionReal));
        if (dias <= 0){
            return 0;
        }
        return dias;
    }

    /**
     * Metodo que ayuda a saber si la devolucion del libro cuenta con retraso o no
     * @return Devuelve true o false
     */
    public boolean estaRetrasado(){
        return this.fechaDevolucionPrevista.isAfter(LocalDate.now()) && this.fechaDevolucionReal == null;
    }

    /**
     * Metodo toString personalizado que muestra los datos del prestamo
     * @return Devuelve una cadena de texto con sus datos
     */
    @Override
    public String toString(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "\t-::DATOS DEL PRESTAMO::-" +
                "\nSOCIO [" + this.socio.getNombre() + "]" +
                "\nTITULO DEL LIBRO: " + this.tituloLibro + "\t| CODIGO DEL LIBRO: " + this.codigoLibro +
                "\nFECHA DEL PRESTAMO: " + this.fechaPrestamo.format(formato) + "\t| FECHA DE DEVOLUCION PREVISTA: " +
                this.fechaDevolucionPrevista.format(formato) +
                "\nFECHA DE DEVOLUCION: " + (this.fechaDevolucionReal!=null?this.fechaDevolucionReal.format(formato):"No se ha devuelto aún") +
                "\t| RETRASO: " + (estaRetrasado()?calcularDiasRetraso() + " días.":"No hay retraso.");
    }
}
