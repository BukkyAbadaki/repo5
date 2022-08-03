import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class HttpServer {
    public void routing(){
        try{
            ServerSocket server =  new ServerSocket(9876);
            System.out.println("Waiting for connection to http://localhost:9876");
            while (true) {
                Socket client = server.accept();
                BufferedReader input =  new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
                PrintWriter output =  new PrintWriter(client.getOutputStream(), true);
                output.print("HTTP/1.1 2OO \r\n");
                output.print("Content-Type: text/html \r\n");
                output.print("Connection: Keep Alive \r\n");
                output.print("\r\n");
                String line;
                List<String> lines =  new ArrayList<>();
                while((line = input.readLine())!=null){
                    if (line.length()==0){
                        break;
                    }
                    lines.add(line.split(" ")[1]);
                    System.out.println(line);
                }
                if (lines.get(0).equals("/index")){
                    output.print("<b> HOMEPAGE </b>");
                }
                else {
                    output.print("<b> BOLD TEXT FOR OUR VIEWING PLEASURE</b>");
                }
                output.close();
                input.close();
                client.close();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
