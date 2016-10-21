/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tikape.runko.database.Database;
import tikape.runko.domain.Alue;

/**
 *
 * @author marko
 */
public class AlueDao implements Dao<Alue, Integer> {

    private final Database database;

    public AlueDao(Database database) {
        this.database = database; //Käytetään samaa tietokantaa
    }

    @Override
    public Alue findOne(Integer key) throws SQLException { //Listaa tietyn alueen kaikki viestiketjut

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE Alue.a_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        Alue a = null;
        while (rs.next()) {
            Integer a_id = rs.getInt("a_id");
            String otsikko = rs.getString("otsikko");
            String luoja = rs.getString("luoja");
            String kuvaus = rs.getString("kuvaus");

            //String luomisaika = rs.getTimestamp("luomisaika").toString();

            //a = new Alue(a_id, otsikko, luoja, kuvaus, luomisaika);
        }

        rs.close();
        stmt.close();
        connection.close();

        return a;
    }

    @Override
    public List<Alue> findAll() throws SQLException { //Listaa kaikki alueet

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue ORDER BY Alue.otsikko COLLATE NOCASE ASC");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer a_id = rs.getInt("a_id");
            String otsikko = rs.getString("otsikko");
            String luoja = rs.getString("luoja");
            String kuvaus = rs.getString("kuvaus");
            String luomisaika = rs.getString("luomisaika");

            alueet.add(new Alue(a_id, otsikko, luoja, kuvaus, luomisaika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    @Override
    public void add(HashMap<String, Object> params) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement p = c.prepareStatement("INSERT INTO Alue (otsikko, luoja, kuvaus, luomisaika) VALUES (?, ?, ?, Datetime('now'))");

        p.setObject(1, params.get("otsikko"));
        p.setObject(2, params.get("luoja"));
        p.setObject(3, params.get("kuvaus"));

        int a = p.executeUpdate();

        p.close();
        c.close();
    }

    public String etsiAlueenOtsikko(Integer key) throws SQLException { //Listaa tietyn alueen kaikki viestiketjut

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE Alue.a_id = ?");
        stmt.setObject(1, key);
        String otsikko = "hehe";
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Integer a_id = rs.getInt("a_id");
            otsikko = rs.getString("otsikko");
            String luoja = rs.getString("luoja");
            String kuvaus = rs.getString("kuvaus");

        }

        rs.close();
        stmt.close();
        connection.close();

        return otsikko;
    }
    
        public List<Alue> findAllwithMsgCount() throws SQLException { //Listaa kaikki alueet

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement
        ("SELECT Alue.a_id, Alue.otsikko, Alue.luoja, Alue.kuvaus, Alue.luomisaika, " +
                "COUNT(Viesti.v_id) AS v_maara,  MAX(Viesti.lahetysaika)AS viimeinenV " + 
                "FROM Alue LEFT JOIN Viestiketju ON Alue.a_id = Viestiketju.a_id " +
                "LEFT JOIN Viesti ON Viestiketju.vk_id = Viesti.vk_id " +
                "GROUP BY Alue.a_id " + 
                "ORDER BY Alue.otsikko COLLATE NOCASE ASC");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer a_id = rs.getInt("a_id");
            String otsikko = rs.getString("otsikko");
            String luoja = rs.getString("luoja");
            String kuvaus = rs.getString("kuvaus");
            String luomisaika = rs.getString("luomisaika");
            String ViimeinenViestiAika = rs.getString("viimeinenV");
            int v_maara = rs.getInt("v_maara");
     
            alueet.add(new Alue(a_id, otsikko, luoja, kuvaus, luomisaika, ViimeinenViestiAika, v_maara));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

}
