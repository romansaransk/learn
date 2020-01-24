import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class UserHandler{

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Date debounce;

    private String calc(String input){
        String[] splitedinput = input.split(" ");
        if (splitedinput.length != 3){
            return "Invalid input";
        }
        if (splitedinput[1].equals("add")){
            try{
                float one = Float.parseFloat(splitedinput[0]);
                float two = Float.parseFloat(splitedinput[2]);
                return Float.toString(one+two);
            } catch (Exception t){
                return "Invalid input";
            }
        } else if (splitedinput[1].equals("mult")){
            try{
                float one = Float.parseFloat(splitedinput[0]);
                float two = Float.parseFloat(splitedinput[2]);
                return Float.toString(one*two);
            } catch (Exception t){
                return "Invalid input";
            }

        }else if (splitedinput[1].equals("subs")){
            try{
                float one = Float.parseFloat(splitedinput[0]);
                float two = Float.parseFloat(splitedinput[2]);
                return Float.toString(one-two);
            } catch (Exception t){
                return "Invalid input";
            }

        }else if (splitedinput[1].equals("divide")){
            try{
                float one = Float.parseFloat(splitedinput[0]);
                float two = Float.parseFloat(splitedinput[2]);
                return Float.toString(one/two);
            } catch (Exception t){
                return "Invalid input";
            }

        } else{
            return "Unsupported operation";
        }
    }

//

    public UserHandler (Socket socket) throws IOException{
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        debounce = new Date();
        run();
    }
    public void run() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                String output = calc(line);
                out.println("Your result: " + output);
                debounce = new Date();
                }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public class task9 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(8888);
        try {
                System.out.println("Server has started on 127.0.0.1:8888.\r\nWaiting for a connection...");
            while(true) {
                try {
                    System.out.println("Waiting for the client request");
                    Socket socket = server.accept();
                    try {
                        new UserHandler(socket);
                    } catch (IOException e){
                        socket.close();
                    }

                } catch (IOException e){
                    server.close();
                    System.out.println("Error on accept socket!");
                    break;
                }
            }

        } catch (Exception t){
            System.out.println("Exception : " + t);
        }


    }
}