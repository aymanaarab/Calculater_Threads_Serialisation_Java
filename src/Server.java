import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Serveur démarré, en attente de connexion du client...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Handler(clientSocket).start();
        }


    }
}

class Handler extends Thread {
    private Socket socket;

    public Handler(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            System.out.println("Client connecté depuis : " + socket.getRemoteSocketAddress());

            while (true) {
                Object object = objectInputStream.readObject();

                if (object instanceof String && ((String) object).equalsIgnoreCase("exit")) {
                    System.out.println("Client déconnecté");
                    break;
                } else if (object instanceof Operation) {
                    Operation operation = (Operation) object;
                    Double number1 = operation.getNombre1();
                    Double number2 = operation.getNombre2();
                    String operator = operation.getOperateur();

                    // Validate operator
                    if (!operator.matches("[+\\-*/]")) {
                        objectOutputStream.writeObject("Opérateur invalide. Veuillez entrer +, -, *, ou /.");
                    } else if (number1 == null || number2 == null) {
                        objectOutputStream.writeObject("Les nombres ne peuvent pas être nuls.");
                    } else {
                        // Perform the operation and send the result
                        String result = operation.calcule();
                        objectOutputStream.writeObject(result);
                    }
                } else {
                    objectOutputStream.writeObject("Objet inconnu reçu.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur dans Handler: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Erreur de fermeture du socket: " + e.getMessage());
            }
        }
    }
}
//                           !operateur.equalsIgnoreCase("+") |
//                            !operateur.equalsIgnoreCase("*") |
//                            !operateur.equalsIgnoreCase("-") |
//                            !operateur.equalsIgnoreCase("/"))
//

//                        System.out.println("L'operateur n est pas u caracter ou faux , tappez + , * , - ou /");
//                        objectOutputStream.writeObject("vous mettez un operateur erroné , tapez + - * ou /");