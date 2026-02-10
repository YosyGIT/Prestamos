package gestionlib;
import java.time.LocalDate;
public class Prestamo {
    private String codigoLibro;
    private static final String regCodigoLibro = "LIB[0-9]{4}";
    private String tituloLibro;
    private Usuario socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;

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
        this.tituloLibro = tituloLibro;

        if (fechaPrestamo == null){
            throw new PrestamoInvalidoException("::ERROR:: La fecha de prestamo no puede ser un campo vacio.");
        }
        if (fechaPrestamo.isAfter(LocalDate.now())){
            throw new PrestamoInvalidoException("::ERROR:: La fecha debe ser posterior o igual al día de hoy");
        }
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = this.fechaPrestamo.plusDays(14);
    }

    public void registrarDevolucion(LocalDate fechaDev) throws PrestamoInvalidoException{
        if (fechaDev == null){
            throw new PrestamoInvalidoException("::ERROR:: La fecha de devolucion no puede ser un campò vacio");
        }
        if (fechaDev.isBefore(fechaPrestamo)){
            throw new PrestamoInvalidoException("::ERROR:: La fecha de devolucion debe ser anterior a la de prestamo.");
        }
        this.fechaDevolucionReal = fechaDev;
    }
}
