package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;




public class Conversor {

    private JComboBox<String> cbConversores;
    private JPanel panel;
    private JPanel contentPane;

    public Conversor() {
        panel = new JPanel();
        contentPane = new JPanel(new BorderLayout());
        cbConversores = new JComboBox<>();
        setupConversoresComboBox();
        setupContinuarButton();
        setupTitleLabel();
    }

    private void setupConversoresComboBox() {
        cbConversores.setMaximumRowCount(3);
        cbConversores.setModel(new DefaultComboBoxModel<>(new String[]{"Conversor de monedas", "Conversor de unidades"}));
        cbConversores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(cbConversores);
    }

    private void setupContinuarButton() {
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String opcion = (String) cbConversores.getSelectedItem();
                if ("Conversor de unidades".equals(opcion)) {
                    convertirUnidades();
                } else if ("Conversor de monedas".equals(opcion)) {
                    convertirMonedas();
                }
            }
        });
        panel.add(btnContinuar);
    }

    private void setupTitleLabel() {
        JLabel lblTitulo = new JLabel("<html>Bienvenido al conversor de unidades<br>Seleccione una opción de conversión</html>");
        lblTitulo.setVerticalAlignment(SwingConstants.TOP);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.PLAIN, 14));
        lblTitulo.setBorder(new EmptyBorder(50, 0, 0, 0));
        contentPane.add(lblTitulo, BorderLayout.NORTH);
    }

    private void convertirUnidades() {
        List<List<String>> lstOpciones = initializeUnidadOptions();
        String[] valores = createConversorOptions(lstOpciones);
        Object opcionMoneda = JOptionPane.showInputDialog(
                null,
                "Seleccione una opción de conversión:",
                "Conversor de unidades",
                JOptionPane.PLAIN_MESSAGE,
                null,
                valores,
                valores[0]);

        if (opcionMoneda != null) {
            Object input = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el valor que deseas convertir: ",
                    "Conversor de unidades",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null);

            if (input != null) {
                realizarConversionUnidades(opcionMoneda, input, lstOpciones);
            }
        }
    }

    private void convertirMonedas() {
        List<List<String>> lstOpciones = initializeMonedaOptions();
        String[] valores = createConversorOptions(lstOpciones);
        Object opcionMoneda = JOptionPane.showInputDialog(
                null,
                "Seleccione una opción de conversión:",
                "Conversor de monedas",
                JOptionPane.PLAIN_MESSAGE,
                null,
                valores,
                valores[0]);

        if (opcionMoneda != null) {
            Object input = JOptionPane.showInputDialog(
                    null,
                    "Ingrese la cantidad de dinero que deseas convertir: ",
                    "Conversor de monedas",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null);

            if (input != null) {
                realizarConversionMonedas(opcionMoneda, input, lstOpciones);
            }
        }
    }

    private List<List<String>> initializeUnidadOptions() {
        return Arrays.asList(
                new ArrayList<>(Arrays.asList("M", "KM", "Metro", "Kilometro", "1000")),
                new ArrayList<>(Arrays.asList("M", "MILLA", "Metro", "Milla", "1609.34")),
                new ArrayList<>(Arrays.asList("M", "CM", "Metro", "Centímetro", "0.01")),
                new ArrayList<>(Arrays.asList("M", "MM", "Metro", "Milímetro", "0.001")),
                new ArrayList<>(Arrays.asList("M", "DM", "Metro", "Decametro", "0.1"))
        );
    }

    private List<List<String>> initializeMonedaOptions() {
        return Arrays.asList(
                new ArrayList<>(Arrays.asList("COP", "USD", "Peso colombiano", "Dolar estadounidense", "4506.23")),
                new ArrayList<>(Arrays.asList("COP", "EU", "Peso colombiano", "Euro", "4947.14")),
                new ArrayList<>(Arrays.asList("COP", "GBP", "Peso colombiano", "Libra esterlina", "5670.51")),
                new ArrayList<>(Arrays.asList("COP", "JPY", "Peso colombiano", "Yen japonés", "33.31")),
                new ArrayList<>(Arrays.asList("COP", "KRW", "Peso colombiano", "Won surcoreano", "3.40"))
        );
    }

    private String[] createConversorOptions(List<List<String>> lstOpciones) {
        String[] valores = new String[lstOpciones.size() * 2];
        int indiceValores = 0;
        for (List<String> lista : lstOpciones) {
            valores[indiceValores++] = "De " + lista.get(2) + " a " + lista.get(3);
            valores[indiceValores++] = "De " + lista.get(3) + " a " + lista.get(2);
        }
        return valores;
    }

    private void realizarConversionUnidades(Object opcionMoneda, Object input, List<List<String>> lstOpciones) {
        try {
            validateInput(input);
            String opcionConversion = (String) opcionMoneda;
            String[] subCadenas = parseConversionOption(opcionConversion);
            double valor = Double.parseDouble(input.toString());
            double cambio = findConversionRate(subCadenas, lstOpciones);
            double conversion = computeConversion(valor, cambio, subCadenas[0].equals("Metro"));

            showResult("El resultado es " + formatDecimal(conversion) + " " + subCadenas[1] + "s");
        } catch (NumberFormatException ex) {
            showError("El valor ingresado no es un número válido.");
        }
    }

    private void realizarConversionMonedas(Object opcionMoneda, Object input, List<List<String>> lstOpciones) {
        try {
            validateInput(input);
            String opcionConversion = (String) opcionMoneda;
            String[] subCadenas = parseConversionOption(opcionConversion);
            double valor = Double.parseDouble(input.toString());
            double cambio = findConversionRate(subCadenas, lstOpciones);
            double conversion = computeConversion(valor, cambio, subCadenas[0].equals("Peso colombiano"));

            showResult("Tienes $ " + formatDecimal(conversion) + " " + subCadenas[1]);
        } catch (NumberFormatException ex) {
            showError("El valor ingresado no es un número válido.");
        }
    }

    private void validateInput(Object input) throws NumberFormatException {
        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
        if (!pattern.matcher(input.toString()).matches()) {
            throw new NumberFormatException();
        }
    }

    private String[] parseConversionOption(String opcionConversion) {
        opcionConversion = opcionConversion.replace("De ", "").replace(" a ", ";");
        return opcionConversion.split(";");
    }

    private double findConversionRate(String[] subCadenas, List<List<String>> lstOpciones) {
        for (List<String> lista : lstOpciones) {
            if ((subCadenas[0].equals(lista.get(2)) && subCadenas[1].equals(lista.get(3))) ||
                    (subCadenas[0].equals(lista.get(3)) && subCadenas[1].equals(lista.get(2)))) {
                return Double.parseDouble(lista.get(4));
            }
        }
        return 0;
    }

    private double computeConversion(double valor, double cambio, boolean isInitialUnit) {
        return isInitialUnit ? valor / cambio : valor * cambio;
    }

    private String formatDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value);
    }

    private void showResult(String message) {
        JOptionPane.showMessageDialog(null, message, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar usando el programa?", "Confirmación", JOptionPane.YES_NO_CANCEL_OPTION);
        if (respuesta == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Programa finalizado", "Información", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel() {
        return panel;
    }
}