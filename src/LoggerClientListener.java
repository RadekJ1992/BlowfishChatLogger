import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Klasa odbierająca wiadomości od klientów
 * Created by radoslawjarzynka on 05.11.14.
 */
public class LoggerClientListener extends Thread
{
    private LoggerClientInfo loggerClientInfo;
    private BufferedReader bufferedReader;

    public LoggerClientListener(LoggerClientInfo loggerClientInfo)
            throws IOException
    {
        this.loggerClientInfo = loggerClientInfo;
        Socket socket = loggerClientInfo.socket;
        System.out.println("Listener Created for IP " + socket.getInetAddress());
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run()
    {
        System.out.println("Listener Started for IP " + loggerClientInfo.socket.getInetAddress());
        try {
            while (!isInterrupted()) {
                String message = bufferedReader.readLine();
                if (message == null) {
                    break;
                }
                System.out.println("Received message from " + loggerClientInfo.socket.getInetAddress() + " : " + message);
                Thread.sleep(200);
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(loggerClientInfo.socket.getInetAddress() + " Disconnected!");
        }
    }

}
