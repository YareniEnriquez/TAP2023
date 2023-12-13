/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

import javazoom.jl.player.Player;

import javax.swing.JFrame;

public class MP3Player extends JFrame {
    private Player player;

    public MP3Player() {
        super("Reproductor de MP3");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        JButton abrirButton = new JButton("Abrir");
        abrirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String archivoMP3 = fileChooser.getSelectedFile().getPath();
                        reproducirMP3(archivoMP3);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JButton detenerButton = new JButton("Detener");
        detenerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detenerMP3();
            }
        });

        panel.add(abrirButton);
        panel.add(detenerButton);

        add(panel);
    }

    private void reproducirMP3(String archivoMP3) {
        try {
            FileInputStream fis = new FileInputStream(archivoMP3);
            player = new Player(fis);
            new Thread(() -> {
                try {
                    player.play();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void detenerMP3() {
        if (player != null) {
            player.close();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MP3Player().setVisible(true);
            }
        });
    }
}