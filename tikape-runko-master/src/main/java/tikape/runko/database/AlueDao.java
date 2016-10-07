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
import tikape.runko.domain.Viestiketju;

/**
 *
 * @author marko
 */
public class AlueDao implements Dao {

    private Database database;

    public AlueDao(Database database) {
        this.database = database; //Käytetään samaa tietokantaa
    }

    @Override
    public List findOne(Object key) throws SQLException { //Listaa tietyn alueen kaikki viestiketjut

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue JOIN Viestiketju ON Alue.a_id=Viestiketju.a_id WHERE Alue.a_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            Integer vk_id = rs.getInt("vk_id");
            Integer a_id = rs.getInt("a_id");
            String vk_otsikko = rs.getString("vk_otsikko");
            String luoja = rs.getString("luoja");
            String aloitusviesti = rs.getString("aloitusviesti");
            String luomisaika = rs.getString("luomisaika");
            viestiketjut.add(new Viestiketju(vk_id, a_id, vk_otsikko, luoja, aloitusviesti, luomisaika));
        }

        rs.close();
        stmt.close();
        connection.close();

        for (Viestiketju viestiketju : viestiketjut) {
            System.out.print(" " + viestiketju.getVk_id() + " ");
            System.out.println(viestiketju.getNimi());
        }

        return viestiketjut;
    }

    @Override
    public List findAll() throws SQLException { //Listaa kaikki alueet

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");

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
    public void delete(Object key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
