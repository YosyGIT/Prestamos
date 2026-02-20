package gestionlib;
import java.net.SocketImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author YosyGIT
 */
public class AppBiblioteca {
    /**
     * Metodo principal (main) en el que se encuentra el gestor principal con el que manipulamos
     * toda la información de los prestamos y socios, además de contener el menú.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorBiblioteca gestor1 = new GestorBiblioteca();
        int opcion = -1;

        while(opcion != 8){
            System.out.println("\n1. Registar nuevo usuario");
            System.out.println("2. Realizar préstamo de libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Consultar estado de usuario");
            System.out.println("5. Mostrar prestamo activo");
            System.out.println("6. Mostrar usuarios sancionados");
            System.out.println("7. Actualizar sanciones");
            System.out.println("8. Salir");
            System.out.print("-> ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1:
                    try{
                        registrarUsuario(sc, gestor1);
                    } catch (UsuarioInvalidoException | UsuarioRepetidoException | FormatoInvalidoException e) {
                        System.out.println("\n" + e.getMessage() + "\nPresiona enter para volver al menú");
                        sc.nextLine();
                    }
                break;

                case 2:
                    try{
                        realizarPresatamo(sc, gestor1);
                    } catch (PrestamoInvalidoException | UsuarioInvalidoException | LibroNoDisponibleException |
                             UsuarioSacionadoException | FormatoInvalidoException e) {
                        System.out.println("\n" + e.getMessage() + "\nPresiona enter para volver al menú");
                        sc.nextLine();
                    }
                break;

                case 3:
                    try{
                        System.out.println(devolverLibro(sc, gestor1));
                    } catch (PrestamoInvalidoException | UsuarioInvalidoException | FormatoInvalidoException e) {
                        System.out.println("\n" + e.getMessage() + "\nPresiona enter para volver al menú");
                        sc.nextLine();
                    }
                break;

                case 4:
                    try{
                        System.out.println(estadoUsuario(sc, gestor1) ? "El usuario ESTA sancionado" : "El usuario NO tiene sanciones");
                    } catch (UsuarioInvalidoException e) {
                        System.out.println("\n" + e.getMessage() + "\nPresiona enter para volver al menú");
                        sc.nextLine();
                    }
                break;

                case 5:
                    try{
                        System.out.println(mostrarPrestamos(gestor1));
                    } catch (PrestamoInvalidoException e) {
                        System.out.println("\n" + e.getMessage() + "\nPresiona enter para volver al menú");
                        sc.nextLine();
                    }
                break;

                case 6:
                    try{
                        System.out.println(mostrarSancionados(gestor1));
                    } catch (UsuarioSacionadoException e) {
                        System.out.println("\n" + e.getMessage() + "\nPresiona enter para volver al menú");
                        sc.nextLine();
                    }
                break;

                case 7:
                    try{
                        actualizarSanciones(gestor1);
                    } catch (UsuarioInvalidoException | UsuarioSacionadoException e) {
                        System.out.println("\n" + e.getMessage() + "\nPresiona enter para volver al menú");
                        sc.nextLine();
                    }
                break;

                case 8:
                    System.out.println("********************************");
                    System.out.println("-::SALIENDO DE LA APLICACIÓN::-");
                    System.out.println("********************************");
                break;
            }
        }
    }

    /**
     * Metodo que permite registrar a un socio dentro del gestor de la biblioteca principal
     * @param sc Escaner con el que introducimos los datos del socio.
     * @param g Gestor principal
     *     EXCEPCIONES DEL METODO:
     * @throws UsuarioInvalidoException
     * @throws UsuarioRepetidoException
     * @throws FormatoInvalidoException
     */
    public static void registrarUsuario(Scanner sc, GestorBiblioteca g)
            throws UsuarioInvalidoException, UsuarioRepetidoException, FormatoInvalidoException {
        String nombre, email, numeroSocio, fechaRegistroUsr;
        Usuario u;

        System.out.print("\nIntroduce el nombre: ");
        nombre = sc.nextLine();
        System.out.print("Introduce el email: ");
        email = sc.nextLine();
        System.out.print("Introduce el numero de socio (EJEMPLO: SOC00000):");
        numeroSocio = sc.nextLine();
        System.out.print("Introduce la fecha de registro (EJEMPLO: 23/04/2026): ");
        fechaRegistroUsr = sc.nextLine();



        u = new Usuario(nombre, email, numeroSocio, formatoFecha(fechaRegistroUsr));
        g.registrarUsuario(u);
    }

    /**
     * Metodo con el que realizamos el prestamo correspondiente, se necesita crear un socio antes de
     * relizar dicho prestamo.
     * @param sc Escaner con el que introducimos los datos del socio.
     * @param g Gestor principal
     *     EXCEPCIONES DEL METODO:
     * @throws PrestamoInvalidoException
     * @throws UsuarioSacionadoException
     * @throws LibroNoDisponibleException
     * @throws UsuarioInvalidoException
     * @throws FormatoInvalidoException
     */
    public static void realizarPresatamo(Scanner sc, GestorBiblioteca g)
            throws PrestamoInvalidoException, UsuarioSacionadoException, LibroNoDisponibleException, UsuarioInvalidoException, FormatoInvalidoException {
        String codigoLibro, tituloLibro, fechaPrestamo, nSocio;
        Usuario socio;

        System.out.print("\nIntroduce el codigo del libro (EJEMPLO: LIB0000): ");
        codigoLibro = sc.nextLine();
        System.out.print("Introduce el titulo del libro: ");
        tituloLibro = sc.nextLine();
        System.out.print("Introduce la fechaPrestamo del prestamo (EJEMPLO: 23/04/2026): ");
        fechaPrestamo = sc.nextLine();
        System.out.print("Introduce el numero de socio: ");
        nSocio = sc.nextLine();

        if (g.buscarSocio(nSocio) == null){
            throw new UsuarioInvalidoException("::ERROR:: No se ecuentra ningun socio con el numero de socio [" + nSocio + "]");
        }
        socio = g.buscarSocio(nSocio);

        g.realizarPrestamo(codigoLibro, tituloLibro, formatoFecha(fechaPrestamo), socio);
        System.out.println("Prestamo realizado correctamente");
    }

    /**
     * Metodo para compronar el formato de la fecha en el resto de metodos
     * @param fechaSc Fecha que comprobamos introducida como parametro del metodo
     * @return retorna una fecha para poder ser utilizado el metodo como parametro de otro.
     *     EXCEPCIONES DEL METODO:
     * @throws FormatoInvalidoException
     */
    public static LocalDate formatoFecha(String fechaSc) throws FormatoInvalidoException{
        LocalDate fecha;
        String[] datosFecha;

        if (!fechaSc.matches("^([1-9]|0[1-9]|[12][0-9]|30|31)/([1-9]|0[1-9]|1[012])/\\d{4}$")){
            throw new FormatoInvalidoException("::ERROR:: El formato de la fecha es invalido.");
        }
        datosFecha = fechaSc.split("/");
        fecha = LocalDate.of(Integer.parseInt(datosFecha[2]),Integer.parseInt(datosFecha[1]),Integer.parseInt(datosFecha[0]));
        return fecha;
    }

    /**
     * Metodo utilizado para devolver un libro y devuelve un mensaje con los días de retraso y la fecha de fin de sanción,
     * en caso de no tener retraso mostrara que fue realizado con exito y en caso de no encontrar ninguna devolución posible
     * mostrara que no se ha encontrado ninguna devolución pendiente.
     * @param sc Escaner con el que introducimos los datos del socio.
     * @param g Gestor principal
     * @return Mensajes que devuelve dependiendo de la condición de la devolución
     *     EXCEPCIONES DEL METODO:
     * @throws PrestamoInvalidoException
     * @throws UsuarioInvalidoException
     * @throws FormatoInvalidoException
     */
    public static String devolverLibro(Scanner sc, GestorBiblioteca g)
                        throws PrestamoInvalidoException, UsuarioInvalidoException, FormatoInvalidoException{
        String codigoLibro, fechaDevolucion;

        boolean exito;
        Usuario socio;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha;

        System.out.print("\nIntroduce el codigo del libro: ");
        codigoLibro = sc.nextLine();
        System.out.print("Introduce la fecha de devolucion: ");
        fechaDevolucion = sc.nextLine();
        fecha = formatoFecha(fechaDevolucion);

        exito = g.devolverLibro(codigoLibro, fecha);

        if (exito){
            for (Prestamo p : g.getPrestamos()){
                if (p != null && p.getCodigoLibro().equalsIgnoreCase(codigoLibro) && p.getFechaDevolucionPrevista().equals(fecha)){
                    if (p.calcularDiasRetraso() > 0){
                        socio = p.getSocio();
                        return "Devolución registrada con " + p.calcularDiasRetraso() + " días de retraso" +
                                "\nUsuario sancionador por " + p.calcularDiasRetraso() + " días (hasta el " +
                                socio.getFechaFinSancion().format(formato) + ")";
                    } else {
                        return "Devolución realizada con exito y dentro de la fecha de devolución.";
                    }

                }
            }
        }

        return "No se ha encontrado ninguna devolución pendiente.";
    }

    /**
     * Metodo para comprobar el estado de un socio
     * @param sc Escaner con el que introducimos los datos del socio.
     * @param g Gestor principal
     * @return Devuelve true o false dependiendo de si esta sancionado o no
     *     EXCEPCIONES DEL METODO:
     * @throws UsuarioInvalidoException
     */
    public static boolean estadoUsuario(Scanner sc, GestorBiblioteca g) throws UsuarioInvalidoException{
        String nSocio;
        Usuario socio;

        System.out.print("\nIntroduce el numero del socio: ");
        nSocio = sc.nextLine();

        if (g.buscarSocio(nSocio) == null){
            throw new UsuarioInvalidoException("::ERROR:: No se ecuentra ningun socio con el numero de socio [" + nSocio + "]");
        }
        socio = g.buscarSocio(nSocio);

        if (socio.estaSancionado()){
            return true;
        }
        return false;
    }

    /**
     * Metodo que muestra los prestamos pendientes de devolver
     * @param g Gestor principal
     * @return Devuelve un String que va incrementandose el tamaño con cada prestamo que este pendiente
     *     EXCEPCIONES DEL METODO:
     * @throws PrestamoInvalidoException
     */
    public static String mostrarPrestamos(GestorBiblioteca g) throws PrestamoInvalidoException{
        String prestamos = "";
        for (Prestamo p : g.getPrestamos()){
            if (p != null && p.getFechaDevolucionReal() == null){
                prestamos += "\n" + p.toString();
            }
        }
        if (prestamos.isEmpty()){
            throw new PrestamoInvalidoException("::ERROR:: No hay prestamos.");
        }
        return prestamos;
    }

    /**
     * Metodo que muestra los usuarios sancionados
     * @param g Gestor principal
     * @return Devuelve un String que va incrementandose el tamaño con cada usuarios que este sancionado
     *     EXCEPCIONES DEL METODO:
     * @throws UsuarioSacionadoException
     */
    public static String mostrarSancionados (GestorBiblioteca g) throws UsuarioSacionadoException{
        String sancionados = "";
        for (Usuario u : g.getUsuarios()){
            if (u != null && u.estaSancionado()){
                sancionados += "\n" + u.toString();
            }
        }
        if (sancionados.isEmpty()){
            throw new UsuarioSacionadoException("::ERROR:: No hay usuarios sancionados.");
        }
        return sancionados;
    }

    /**
     * Metodo que levanta todas las sonciones que tenga una fecha de devolución anterior a la fecha actual
     * @param g Gestor principal
     *     EXCEPCIONES DEL METODO:
     * @throws UsuarioInvalidoException
     * @throws UsuarioSacionadoException
     */
    public static void actualizarSanciones (GestorBiblioteca g) throws UsuarioInvalidoException, UsuarioSacionadoException {
        int contadorActualizaciones = 0;
        for (Usuario u : g.getUsuarios()){
            if (u != null && u.getFechaFinSancion() != null && u.getFechaFinSancion().isBefore(LocalDate.now())){
                u.levantarSancion();
                contadorActualizaciones++;
            }
        }

        if (contadorActualizaciones == 0){
            throw new UsuarioSacionadoException("::ERROR:: No hay usuarios sancionados para actulizar su sancion");
        }else {
            System.out.println("\nSanciones actulizadas con exito.");
        }
    }

}
