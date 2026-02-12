package gestionlib;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
        this.tituloLibro = tituloLibro.trim();

        if (fechaPrestamo == null){
            throw new PrestamoInvalidoException("::ERROR:: La fecha de prestamo no puede ser un campo vacio.");
        }
        if (fechaPrestamo.isAfter(LocalDate.now())){
            throw new PrestamoInvalidoException("::ERROR:: La fecha debe ser posterior o igual al día de hoy");
        }
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = this.fechaPrestamo.plusDays(14);
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public String getCodigoLibro() {
        return codigoLibro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public Usuario getSocio() {
        return socio;
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

    public boolean estaRetrasado(){
        return this.fechaDevolucionPrevista.isAfter(LocalDate.now());
    }

    @Override
    public String toString(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "\t-::DATOS DEL PRESTAMO::-" +
                "\nSOCIO [" + this.socio.getNombre() + "]" +
                "\nTITULO DEL LIBRO: [" + this.tituloLibro + "]\t| CODIGO DEL LIBRO: " + this.codigoLibro +
                "\nFECHA DEL PRESTAMO: [" + this.fechaPrestamo.format(formato) + "]\t| FECHA DE DEVOLUCION PREVISTA: " +
                this.fechaDevolucionPrevista.format(formato) +
                "\nFECHA DE DEVOLUCION: " + this.fechaDevolucionReal.format(formato) + "\t| RETRASO: " +
                (estaRetrasado()?calcularDiasRetraso() + " días.":"No hay retraso.");
    }
}
