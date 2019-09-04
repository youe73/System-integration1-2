import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiBankServer {


    /*public static double account = 5000.00;
    public static PrintWriter showOutput=null;
    public static Scanner inputFromClient=null;*/
    public static ServerSocket serverSocket = null;
    /*public static Socket socket=null;*/
    public static ExecutorService createThreads = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {

    try
    {
        System.out.println("Waiting for clients to connect....");
        serverSocket = new ServerSocket(6666);


        while (true)
        {
            Socket socket = new Socket();
            socket = serverSocket.accept();

            multiClientHandler clientThreads = new multiClientHandler(socket);
            createThreads.execute(clientThreads);
        }



    } catch(
    Exception e)

    {
        e.printStackTrace();
    }

}
   /* static double addbalance(double number)
    {
        account = account + number;
        return account;
    }

    static double subbalance(double number)
    {
        account = account -number;

        return account;
    }

    static double updatebalance()
    {
        return account;
    }
    */
}

class multiClientHandler implements Runnable {

    public static double account = 5000.00;
    public static PrintWriter showOutput=null;
    public static Scanner inputFromClient=null;
    public static ServerSocket serverSocket = null;
    public static Socket socket=null;
    /*Socket socket;*/
    /*Scanner in;
    PrintWriter out;*/

    multiClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        inputFromClient = new Scanner(socket.getInputStream());
        PrintWriter outputToClient = new PrintWriter(socket.getOutputStream(),true);
    }

    @Override
    public void run() {

        try {

            System.out.println(socket);
            System.out.println("Connection established....");
            String serverIP = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Server ip: " + serverIP);

            showOutput = new PrintWriter(socket.getOutputStream(), true); /*sending message (account status) to client*/

            BufferedReader inputs = new BufferedReader(new InputStreamReader(socket.getInputStream())); /*get data from socket inputstream/client*/
            inputFromClient = new Scanner(socket.getInputStream());
            PrintWriter outputToClient = new PrintWriter(socket.getOutputStream(), true);

            String request, warning;
            double deposite, response, update;

            while (inputFromClient.hasNext()) {

                request = inputFromClient.nextLine();
                account = updatebalance();

                if (request.equals("quit")) {
                    outputToClient.println("Terminate, client!");
                    System.out.println("Server says: " + request + " client!");
                    break;

                } else if (request.equals("+")) {
                    outputToClient.println(request + "Enter amount to deposit....your account is: " + account);
                    deposite = Double.parseDouble(inputFromClient.nextLine());
                    response = addbalance(deposite);
                    outputToClient.println(response);


                } else if (request.equals("-")) {
                    account = updatebalance();
                    outputToClient.println(request + "Enter amount to withdraw....your account is: " + account);
                    deposite = Double.parseDouble(inputFromClient.nextLine());

                    if (account < 0.0) {
                        warning = "Withdrawal Request Rejected!!.....Your account is negative";
                        outputToClient.println(warning);
                        System.out.println("From Server:" + warning);
                        return;
                    } else {

                        response = subbalance(deposite);
                        outputToClient.println("From Server: your updated account is: " + response);

                    }

                } else {

                    System.out.println("Not valid action....reconnect again");
                    return;

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    static double addbalance(double number)
    {
        account = account + number;
        return account;
    }

    static double subbalance(double number)
    {
        account = account -number;

        return account;
    }

    static double updatebalance()
    {
        return account;
    }
}