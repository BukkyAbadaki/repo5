import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private void startServer(){
        try {
            while (!serverSocket.isClosed()){
               Socket socket=serverSocket.accept();
                System.out.println("A new client has connected");
                ClientUser clientUser = new ClientUser(socket);

                Thread thread = new Thread(clientUser);
                thread.start();

            }
        }catch (IOException e){


        }
    }
    public void classServerSocket(){
        try {
            if(serverSocket !=null){
                serverSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}




