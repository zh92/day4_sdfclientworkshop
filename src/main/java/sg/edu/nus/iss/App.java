package sg.edu.nus.iss;

import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public final class App {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("locahost", 12345);

        try(OutputStream os = socket.getOutputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            Console cons = System.console();
            String readInput = "";

            while(!readInput.equalsIgnoreCase("close")) {
                readInput = cons.readLine("Enter a command: ");
                dos.writeUTF(readInput);
                dos.flush();
            }

        } catch (EOFException ex) {
            socket.close();
        }
    }
}
