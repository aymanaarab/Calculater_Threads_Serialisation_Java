import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket s = new Socket("localhost", 12345);
        System.out.println("Connexion établie avec le serveur");

        OutputStream outputStream = s.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        InputStream inputStream = s.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            Double nombre1 = null, nombre2 = null;

            while (nombre1 == null) {
                try {
                    System.out.print("Entrez le premier nombre : ");
                    nombre1 = Double.valueOf(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre valide.");
                }
            }

            while (nombre2 == null) {
                try {
                    System.out.print("Entrez le deuxième nombre : ");
                    nombre2 = Double.valueOf(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre valide.");
                }
            }

            String operateur = null;
            while (operateur == null || !operateur.matches("[+\\-*/]")) {
                System.out.print("Entrez l'opérateur (+, -, *, /) : ");
                operateur = scanner.nextLine();
                if (!operateur.matches("[+\\-*/]")) {
                    System.out.println("Entrée invalide. Veuillez entrer un opérateur valide (+, -, *, /).");
                }
            }

            Operation operation = new Operation();
            operation.setNombre1(nombre1);
            operation.setNombre2(nombre2);
            operation.setOperateur(operateur);

            objectOutputStream.writeObject(operation);

            String result = (String) objectInputStream.readObject();
            System.out.println("Résultat du serveur : " + result);

            System.out.println("Tapez 'Exit' pour quitter ou 'Enter' pour continuer.");
            String continuer = scanner.nextLine();
            if (continuer.equalsIgnoreCase("Exit")) {
                objectOutputStream.writeObject("exit");
                break;
            }

        }

        s.close();
        System.out.println("Connexion fermée avec le serveur.");
    }
}
