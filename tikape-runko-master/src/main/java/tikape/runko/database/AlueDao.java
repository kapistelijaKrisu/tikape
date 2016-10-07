/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;

/**
 *
 * @author marko
 */
public class AlueDao implements Dao <Alue, Integer> {

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
         
            String luomisaika = rs.getTimestamp("luomisaika").toString();
            
            a = new Alue (a_id, otsikko, luoja, kuvaus, luomisaika);
        }

        rs.close();
        stmt.close();
        connection.close();

        return a;
    }

    @Override
    public List<Alue> findAll() throws SQLException { //Listaa kaikki alueet

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue ORDER BY luomisaika DESC");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer a_id = rs.getInt("a_id");
            String otsikko = rs.getString("otsikko");
            String luoja = rs.getString("luoja");
            String kuvaus = rs.getString("kuvaus");
            String luomisaika = rs.getString("luomisaika");
            System.out.println(rs);
            alueet.add(new Alue(a_id, otsikko, luoja, kuvaus, luomisaika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
