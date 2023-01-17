package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public final class App {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        try(OutputStream os = socket.getOutputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            Console cons = System.console();
            String readInput = "", receivedMessaged = "";

            try(InputStream is = socket.getInputStream()) {
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);

                while(!readInput.equalsIgnoreCase("close")) {
                    readInput = cons.readLine("Enter a command: ");
                    dos.writeUTF(readInput);
                    dos.flush();

                    receivedMessaged = dis.readUTF();
                    System.out.println(receivedMessaged);
                }
                dis.close();;
                bis.close();
            } catch (IOException ex) {
                socket.close();
            }
            dos.close();
            bos.close();
        } catch (EOFException ex) {
            socket.close();
        }
    }
}
