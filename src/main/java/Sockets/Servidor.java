/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 *
 * @author sandy
 */

public class Servidor extends JFrame {
    private JTextArea txtChat;

    public Servidor() {
        initComponents();
        startServer();
    }

    private void initComponents() {
        txtChat = new JTextArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor Chat");

        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(txtChat);

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

    private void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            txtChat.append("Servidor iniciado. Esperando conexiones...\n");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                txtChat.append("Cliente conectado desde: " + clientSocket.getInetAddress().getHostAddress() + "\n");

                // Hilo para manejar la comunicación con el cliente
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    txtChat.append("Cliente: " + inputLine + "\n");
                }
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
    }
}