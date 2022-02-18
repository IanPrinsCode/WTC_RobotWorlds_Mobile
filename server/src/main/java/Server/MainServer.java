package Server;

import Display.Print;
import Handlers.SocketClientHandler;
import Services.DatabaseController;
import World.World;
import io.swagger.v3.oas.models.PathItem;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainServer {

    public final static ArrayList<String> userNames = new ArrayList<>();

    private static World world;

    public static DataOutputStream Output;

    public static void main(String[] args) throws IOException, ParseException, SQLException {
        ServerConfig serverConfig = new ServerConfig(readConfigFile());
        serverConfig.setConfigFromArgs(args);

        if (!serverConfig.isOutput()){ disableOutput();}

        world = new World(serverConfig);
        ApiServer apiServer = new ApiServer(world, serverConfig.getApiPort());
        apiServer.start();

        final DatabaseController controller = new DatabaseController(world);
        controller.createWorldTable();

        Runnable cliRun = new ServerCommandLine(world);
        Thread cliThread = new Thread(cliRun);
        cliThread.start();

        ServerSocket serverSocket = new ServerSocket(serverConfig.getPort());


        Print.outputText("\nServer running & waiting for client connections."
                +"\nServer is listening on port: " + serverConfig.getPort()
                +"\nHTTP Server is listening on port:"+ serverConfig.getApiPort());


        while(true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);
                Output = new DataOutputStream(socket.getOutputStream());


                Runnable r = new SocketClientHandler(socket, world);
                Thread task = new Thread(r);

                ServerCommandLine.serverThreads.add(task);

                task.start();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static World getWorld() {
        return world;
    }

    public static void endServer() {System.exit(0);}

    private static JSONObject readConfigFile() throws IOException {
        InputStream is = MainServer.class.getResourceAsStream("/config.json");
        StringBuilder stringBuilder = new StringBuilder();

        int i;
        while((i=is.read())!=-1){
            stringBuilder.append((char)i);
        }
        JSONTokener tokenizer = new JSONTokener(String.valueOf(stringBuilder));

        return new JSONObject(tokenizer);
    }

    private static void disableOutput(){
        PrintStream dummyStream = new PrintStream(new OutputStream(){
            public void write(int b) {
                // NO-OP
            }
        });

        System.setOut(dummyStream);
    }
}
