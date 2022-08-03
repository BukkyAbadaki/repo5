import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientUser implements Runnable{
    public static ArrayList<ClientUser>clients = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public ClientUser(Socket socket){
        try {

           this.socket = socket;
         this.bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         this.clientUserName = bufferedReader.readLine();
         clients.add(this);
         broadCastMessage("Server: " +clientUserName + " has entered the chat! ");

        }catch (IOException e){
            closeEveryThing(socket,bufferedReader,bufferedWriter);
        }
    }
    @Override
    public void run() {
    String messageFromClient;
    while (socket.isConnected()){
        try {
            messageFromClient = bufferedReader.readLine();
            broadCastMessage(messageFromClient);
        } catch (IOException e) {
            closeEveryThing(socket, bufferedReader, bufferedWriter);
            break;
        }
    }
    }
    public void broadCastMessage(String messageToSend){
        for (ClientUser client : clients){

                try {
                    if (!client.clientUserName.equals(clientUserName)) {
                        client.bufferedWriter.write(messageToSend);
                        client.bufferedWriter.newLine();
                        client.bufferedWriter.flush();
                    }
                } catch (IOException e) {
                   closeEveryThing(socket,bufferedReader,bufferedWriter);
                }

            }
        }
        public  void removeClient(){
        clients.remove(this);
        broadCastMessage("Server" + clientUserName + "he has left the chat");
    }
    public void  closeEveryThing(Socket socket, BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        removeClient();
        try {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}





