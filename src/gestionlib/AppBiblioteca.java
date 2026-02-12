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
                    } catch (UsuarioInvalidoException | UsuarioRepetidoException e) {
                        System.out.println(e.getMessage());
                        sc.nextLine();
                    }
                break;

                case 2:

            }
        }
    }

    public static void registrarUsuario(Scanner sc, GestorBiblioteca g) throws UsuarioInvalidoException, UsuarioRepetidoException{
        String nombre, email, numeroSocio, fecha;
        String[] datosFecha;
        LocalDate fechaRegistro;
        Usuario u;

        System.out.print("\nIntroduce el nombre: ");
        nombre = sc.nextLine();
        System.out.print("Introduce el email: ");
        email = sc.nextLine();
        System.out.println("Introduce el numero de socio (EJEMPLO: SOC0000):");
        numeroSocio = sc.nextLine();
        System.out.println("Introduce la fecha de registro (EJEMPLO: 23/04/2026)");
        fecha = sc.nextLine();

        datosFecha = fecha.split("/");
        fechaRegistro = LocalDate.of(Integer.parseInt(datosFecha[2]),Integer.parseInt(datosFecha[1]),Integer.parseInt(datosFecha[0]));

        u = new Usuario(nombre, email, numeroSocio, fechaRegistro);
        g.registrarUsuario(u);
    }

    public static void realizarPresatamo(Scanner sc, GestorBiblioteca g){

    }
}
