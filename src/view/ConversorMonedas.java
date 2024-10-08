package view;

import javax.swing.*;
import model.Conversor;

import java.awt.*;

public class ConversorMonedas extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ConversorMonedas frame = new ConversorMonedas();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public ConversorMonedas() {
        setTitle("Conversor de monedas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Cargar la imagen
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/assets/img/icon.png"));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, 450, 300);
        contentPane.add(imageLabel);

        // Seleccionar moneda de origen
        JComboBox<String> comboDe = new JComboBox<>(new String[]{"COP", "USD", "EU"});
        comboDe.setBounds(50, 70, 100, 30);
        contentPane.add(comboDe);

        // Seleccionar moneda de destino
        JComboBox<String> comboA = new JComboBox<>(new String[]{"COP", "USD", "EU"});
        comboA.setBounds(200, 70, 100, 30);
        contentPane.add(comboA);

        // Botón de conversión
        JButton btnConvertir = new JButton("Convertir");
        btnConvertir.setBounds(150, 120, 100, 30);
        contentPane.add(btnConvertir);

        // Listener para el botón de conversión
        btnConvertir.addActionListener(e -> {
            String from = (String) comboDe.getSelectedItem();
            String to = (String) comboA.getSelectedItem();

            if (from.equals(to)) {
                JOptionPane.showMessageDialog(null, "Las monedas de origen y destino deben ser diferentes.");
                return;
            }

            String input = JOptionPane.showInputDialog("Ingrese el monto a convertir:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    double monto = Double.parseDouble(input);
                    double resultado = Conversor.convertir(monto, from, to);
                    JOptionPane.showMessageDialog(null, "Resultado: " + resultado + " " + to);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ingresó un monto.");
            }
        });
    }
}