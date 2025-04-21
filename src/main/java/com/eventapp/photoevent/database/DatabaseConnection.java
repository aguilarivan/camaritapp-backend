package com.eventapp.photoevent.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private Connection connection;

    public DatabaseConnection() {
        try {
            // Configuración de la base de datos en memoria de H2
            String url = "jdbc:h2:mem:photoevent;DB_CLOSE_DELAY=-1"; // La URL de H2 en memoria
            String user = "sa"; // Usuario por defecto
            String password = ""; // Contraseña vacía por defecto

            // Obtener la conexión a la base de datos
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
