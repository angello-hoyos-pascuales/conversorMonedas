package model;

public class Conversor {

    // Método que define las tasas de cambio entre las monedas
    public static double obtenerTasaCambio(String from, String to) {
        if (from.equals("COP") && to.equals("USD")) {
            return 0.00022; // 1 COP = 0.00022 USD
        } else if (from.equals("USD") && to.equals("COP")) {
            return 4500; // 1 USD = 4500 COP
        } else if (from.equals("COP") && to.equals("EU")) {
            return 0.00019; // 1 COP = 0.00019 EU
        } else if (from.equals("EU") && to.equals("COP")) {
            return 5200; // 1 EU = 5200 COP
        } else if (from.equals("USD") && to.equals("EU")) {
            return 0.85; // 1 USD = 0.85 EU
        } else if (from.equals("EU") && to.equals("USD")) {
            return 1.18; // 1 EU = 1.18 USD
        }
        // Si las monedas son iguales o no se encuentra la tasa, devolvemos 1
        return 1;
    }

    public static double convertir(double monto, String from, String to) {
        double tasaCambio = obtenerTasaCambio(from, to);
        return monto * tasaCambio;
    }
}
