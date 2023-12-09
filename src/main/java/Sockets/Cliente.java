/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import java.awt.Component;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 *
 * @author sandy
 */
    
public class Cliente extends JFrame {
    private JButton btnEnviar;
    private JTextField txtMensaje;
    private JTextArea txtChat;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Component jScrollPane1;


    public Cliente() {
        initComponents();
        try {
            // Conexión al servidor en localhost y puerto 8080
            socket = new Socket("localhost", 8080);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Hilo para recibir mensajes del servidor
            Thread receiverThread = new Thread(new Receiver());
            receiverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                enviarMensaje();
            }
        });
    }

    private void initComponents() {
        txtMensaje = new JTextField();
        btnEnviar = new JButton();
        txtChat = new JTextArea();

        // Código para configurar los componentes, tamaños, ubicaciones, etc.

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente Chat");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        pack();
    }

    private void enviarMensaje() {
        String mensaje = txtMensaje.getText();
        out.println(mensaje);
        txtMensaje.setText("");
    }

    class Receiver implements Runnable {
        public void run() {
            try {
                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    txtChat.append("Servidor: " + mensaje + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);
            }
        });
    }
}
