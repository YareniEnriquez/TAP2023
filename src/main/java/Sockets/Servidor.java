/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

/**
 *
 * @author sandy
 */
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Servidor extends JFrame {
    private ServerSocket servidor;
    private Socket cliente;
    private BufferedReader entrada;
    private PrintWriter salida;
    private JTextArea areaTexto;
    private JTextField campoTexto;
    private JButton btnEnviar;

    public Servidor() {
        setTitle("Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        areaTexto = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane);

        campoTexto = new JTextField();
        add(campoTexto, "South");

        btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(e -> enviarMensaje());
        add(btnEnviar, "East");

        setVisible(true);

        try {
            servidor = new ServerSocket(5000);
            areaTexto.append("Servidor esperando conexiones...\n");

            cliente = servidor.accept();
            areaTexto.append("Cliente conectado\n");

            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            salida = new PrintWriter(cliente.getOutputStream(), true);

            // Hilo para recibir mensajes del cliente
            Thread thread = new Thread(() -> {
                String mensaje;
                try {
                    while ((mensaje = entrada.readLine()) != null) {
                        areaTexto.append("Cliente: " + mensaje + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensaje() {
        String mensaje = campoTexto.getText();
        salida.println(mensaje);
        areaTexto.append("Servidor: " + mensaje + "\n");
        campoTexto.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Servidor());
    }
}
