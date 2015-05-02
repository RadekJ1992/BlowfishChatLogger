/**
 * created on 19:59:40 3 lis 2014 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Główna klasa aplikacji przekaźnika
 */
public class Server {

    public static final int LOGGER_PORT = 9000;

    /**
     * Rozpoczęcie pracy serwera
     */
    public void StartServer() {
        // Open server socket for listening
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(LOGGER_PORT);
            System.out.println("Logger started on port " + LOGGER_PORT);
        } catch (IOException se) {
            System.err.println("Can not start listening on port " + LOGGER_PORT);
            se.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Created Handler for Clients");
        Handler handlerForClients = new Handler(serverSocket);
        Thread clientsHandlerThread = new Thread (handlerForClients);

        clientsHandlerThread.start();
    }

    /**
     * punkt wejścia aplikacji
     * @param args
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.StartServer();
    }

    /**
     * Wewnętrzna klasa obsługująca klientów przekaźnika
     */
    private class Handler implements Runnable {

        private ServerSocket loggerClientSocket;

        public Handler(ServerSocket loggerClientSocket) {
            this.loggerClientSocket = loggerClientSocket;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Socket Opened");
                    Socket socket = loggerClientSocket.accept();
                    LoggerClientInfo loggerClientInfo = new LoggerClientInfo();
                    loggerClientInfo.socket = socket;
                    LoggerClientListener loggerClientListener =
                            new LoggerClientListener(loggerClientInfo);
                    loggerClientInfo.loggerClientListener = loggerClientListener;
                    loggerClientListener.start();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}

