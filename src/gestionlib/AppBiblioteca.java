package gestionlib;
import java.time.LocalDate;
import java.util.Scanner;
public class AppBiblioteca {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorBiblioteca gestor1 = new GestorBiblioteca();
        int opcion = -1;

        while(opcion != 8){
            System.out.println("1. Registar nuevo usuario");
            System.out.println("2. Realizar préstamo de libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Consultar estado de usuario");
            System.out.println("5. Mostrar préstamo activo");
            System.out.println("6. Mostrar usuario sancionados");
            System.out.println("7. Actualizar sanciones");
            System.out.println("8. Salir");
            System.out.print("-> ");
            opcion = sc.nextInt();

            switch (opcion){
                case 1:
                    try{
                        registrarUsuario(sc, gestor1);
                    } catch (UsuarioInvalidoException | UsuarioRepetidoException | FormatoInvalidoException e) {
                        System.out.println(e.getMessage());
                        sc.nextLine();
                    }
                break;

                case 2:

            }
        }
    }

    public static void registrarUsuario(Scanner sc, GestorBiblioteca g)
            throws UsuarioInvalidoException, UsuarioRepetidoException, FormatoInvalidoException {
        String nombre, email, numeroSocio, fechaRegistroUsr;
        Usuario u;

        System.out.print("\nIntroduce el nombre: ");
        nombre = sc.nextLine();
        System.out.print("Introduce el email: ");
        email = sc.nextLine();
        System.out.println("Introduce el numero de socio (EJEMPLO: SOC0000):");
        numeroSocio = sc.nextLine();
        System.out.println("Introduce la fechaRegistroUsr de registro (EJEMPLO: 23/04/2026)");
        fechaRegistroUsr = sc.nextLine();



        u = new Usuario(nombre, email, numeroSocio, formatoFecha(fechaRegistroUsr));
        g.registrarUsuario(u);
    }

    public static void realizarPresatamo(Scanner sc, GestorBiblioteca g)
            throws PrestamoInvalidoException, UsuarioSacionadoException, LibroNoDisponibleException, UsuarioInvalidoException, FormatoInvalidoException {
        String codigoLibro, tituloLibro, fechaPrestamo, nSocio;
        Usuario socio;

        System.out.print("\nIntroduce el codigo del libro: ");
        codigoLibro = sc.nextLine();
        System.out.print("Introduce el titulo del libro: ");
        tituloLibro = sc.nextLine();
        System.out.print("Introduce la fechaPrestamo del prestamo: ");
        fechaPrestamo = sc.nextLine();
        System.out.println("Introduce el numero de socio: ");
        nSocio = sc.nextLine();

        if (g.buscarSocio(nSocio) == null){
            throw new UsuarioInvalidoException("::ERROR:: No se ecuentra ningun socio con el numero de socio [" + nSocio + "]");
        }
        socio = g.buscarSocio(nSocio);

        g.realizarPrestamo(codigoLibro, tituloLibro, formatoFecha(fechaPrestamo), socio);
    }

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

    public static void devolverLibro(Scanner sc, GestorBiblioteca g) throws PrestamoInvalidoException, UsuarioInvalidoException, FormatoInvalidoException{
        String codigoLibro, fechaDevolucion;

        System.out.print("\nIntroduce el codigo del libro: ");
        codigoLibro = sc.nextLine();
        System.out.print("Introduce la fecha de devolucion: ");
        fechaDevolucion = sc.nextLine();

        g.devolverLibro(codigoLibro, formatoFecha(fechaDevolucion));
    }
}
