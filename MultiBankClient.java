import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class MultiBankClient {

    public static double numberToDisplay;

    public static void main(String[] args)
    {
        try {
            System.out.println("Client started....");
            Socket socket = new Socket("localhost",6666);
            /*BufferedReader inputkeyboard = new BufferedReader(new InputStreamReader(System.in)); */ /*get input from keyboard*/

            /*BufferedReader DisplayCurrentBalanceBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/ /*get info from server*/

            /*String DisplayCurrentBalance = DisplayCurrentBalanceBuffer.readLine();
            System.out.println(DisplayCurrentBalance);*/

            System.out.println("Welcome to Bank service, you are now connected to make transactions");
            System.out.println("Press + for deposit(add) OR - for withdrawal(subtract)...");
            /*Scanner inputkeyboard = new Scanner(socket.getInputStream());*/ /*get input from keyboard using scanner object*/

            /*String inputs = inputkeyboard.readLine();*/

            String request, response;
            Scanner inputFromServer = new Scanner(socket.getInputStream());
            Scanner inputFromkeyboard = new Scanner(System.in);
            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);

            while ((request = inputFromkeyboard.nextLine())!=null)
            {
                output.println(request);
                response = inputFromServer.nextLine();
                System.out.println(response);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
