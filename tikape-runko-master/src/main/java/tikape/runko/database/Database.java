package tikape.runko.database;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
       if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();
        
        lista.add("CREATE TABLE Viesti(v_id INTEGER PRIMARY KEY AUTOINCREMENT, vk_id INTEGER, nimimerkki VARCHAR, viesti VARCHAR, lahetysaika DATE, FOREIGN KEY(vk_id) REFERENCES Viestiketju(vk_id));");
        lista.add("CREATE TABLE Viestiketju (vk_id INTEGER PRIMARY KEY AUTOINCREMENT, a_id INTEGER, otsikko VARCHAR, luoja VARCHAR, aloitusviesti VARCHAR, luomisaika DATE, FOREIGN KEY(a_id) REFERENCES Alue(a_id));");
        lista.add("CREATE TABLE Alue (a_id INTEGER PRIMARY KEY AUTOINCREMENT, otsikko VARCHAR, luoja VARCHAR, kuvaus VARCHAR, luomisaika DATE);");
        
        

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        

        return lista;
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();
        
        lista.add("CREATE TABLE Viesti(v_id INTEGER PRIMARY KEY AUTOINCREMENT, vk_id INTEGER, nimimerkki VARCHAR, viesti VARCHAR, lahetysaika DATE, FOREIGN KEY(vk_id) REFERENCES Viestiketju(vk_id));");
        lista.add("CREATE TABLE Viestiketju (vk_id INTEGER PRIMARY KEY AUTOINCREMENT, a_id INTEGER, otsikko VARCHAR, luoja VARCHAR, aloitusviesti VARCHAR, luomisaika DATE, FOREIGN KEY(a_id) REFERENCES Alue(a_id));");
        lista.add("CREATE TABLE Alue (a_id INTEGER PRIMARY KEY AUTOINCREMENT, otsikko VARCHAR, luoja VARCHAR, kuvaus VARCHAR, luomisaika DATE);");
        
        return lista;
    }
}
