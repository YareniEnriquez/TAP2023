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

public class Cliente extends JFrame {
    private Socket servidor;
    private BufferedReader entrada;
    private PrintWriter salida;
    private JTextArea areaTexto;
    private JTextField campoTexto;
    private JButton btnEnviar;

    public Cliente(){
        setTitle("Cliente");
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
            servidor = new Socket("localhost", 5000);

            entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            salida = new PrintWriter(servidor.getOutputStream(), true);

            // Hilo para recibir mensajes del servidor
            Thread thread = new Thread(() -> {
                String mensaje;
                try {
                    while ((mensaje = entrada.readLine()) != null) {
                        areaTexto.append("Servidor: " + mensaje + "\n");
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
        areaTexto.append("Cliente: " + mensaje + "\n");
        campoTexto.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cliente());
    }
}

