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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tikape.runko.Debug;
import tikape.runko.database.Database;
import tikape.runko.domain.Viestiketju;

/**
 *
 * @author kbvalto
 */
public class ViestiketjuDao implements Dao<Viestiketju, Integer> {

    private Database database;

    public ViestiketjuDao(Database database) {
        this.database = database;
    }

    @Override
    public Viestiketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE vk_id = ?");
        stmt.setObject(1, key);
 
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Integer id = rs.getInt("vk_id");
        Integer a_id = rs.getInt("a_id");
        String otsikko = rs.getString("vk_otsikko");
        String luoja = rs.getString("luoja");
        String aloitus_viesti = rs.getString("aloitusviesti");
        String luomisaika = rs.getString("luomisaika");
 
        Viestiketju viestiketju = new Viestiketju(id, a_id, otsikko, luoja, aloitus_viesti, luomisaika);
 
        rs.close();
        stmt.close();
        connection.close();
 
        return viestiketju;
    }

    public List<Viestiketju> findAllByAlueId(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE a_id = ? ORDER BY luomisaika DESC");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> vks = new ArrayList<>();

        while (rs.next()) {

            Integer id = rs.getInt("vk_id");
            Integer a_id = rs.getInt("a_id");
            String nimi = rs.getString("vk_otsikko");
            String luoja = rs.getString("luoja");
            String aloitus_viesti = rs.getString("aloitusviesti");
            String luomisaika = rs.getString("luomisaika");

            Viestiketju viestiketju = new Viestiketju(id, a_id, nimi, luoja, aloitus_viesti, luomisaika);
            vks.add(viestiketju);
        }

        rs.close();
        stmt.close();
        connection.close();

        return vks;
    }

    @Override
    public List<Viestiketju> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju ORDER BY luomisaika DESC");

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("vk_id");
            Integer a_id = rs.getInt("a_id");
            String nimi = rs.getString("vk_otsikko");
            String luoja = rs.getString("luoja");
            String aloitus_viesti = rs.getString("aloitusviesti");
            Timestamp luomisaika = rs.getTimestamp("luomisaika");

            Viestiketju viestiketju = new Viestiketju(id, a_id, nimi, luoja, aloitus_viesti, luomisaika.toString());

            viestiketjut.add(viestiketju);
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;
    }

    @Override
    public void add(HashMap<String, Object> params) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement p = c.prepareStatement("INSERT INTO Viestiketju (a_id, vk_otsikko, luoja, luomisaika, aloitusviesti) VALUES (?, ?, ?, Datetime('now'), ?)");

        p.setObject(1, params.get("id"));
        p.setObject(2, params.get("otsikko"));
        p.setObject(3, params.get("luoja"));
        p.setObject(4, params.get("viesti"));
        int a = p.executeUpdate();
        System.out.println(a);
        p.close();
        c.close();
    }

    public void fixSize(Integer id) throws SQLException {

        List<Viestiketju> ketjut = findAllByAlueId(id);

        if (ketjut.size() > 10) {

            Connection connection = database.getConnection();

            for (int i = 10; i < ketjut.size(); i++) {
                PreparedStatement stmtV = connection.prepareStatement("DELETE FROM Viesti WHERE Viesti.vk_id = ?");
                stmtV.setObject(1, ketjut.get(i).getVk_id());
                stmtV.execute();
                
                PreparedStatement stmtVK = connection.prepareStatement("DELETE FROM Viestiketju WHERE Viestiketju.vk_id = ?");
                stmtVK.setObject(1, ketjut.get(i).getVk_id());
                stmtVK.execute();
                stmtVK.close();
            }
            connection.close();
        }

    }

    public int getViestienMaaraKetjus(int vk_id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(vk_id) FROM Viesti WHERE Viesti.vk_id = ?");
        stmt.setObject(1, vk_id);
        ResultSet rs = stmt.executeQuery();
     
        int count = rs.getInt(1);
        rs.close();
        connection.close();
        return count;
    
    }

    public List<Viestiketju> findAllWithMsgCountByAlyeId(int alueId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement
        ("SELECT Viestiketju.vk_id, Viestiketju.vk_otsikko, Viestiketju.luoja, ViestiKetju.a_id, " + 
                "Viestiketju.aloitusviesti, Viestiketju.luomisaika, " +
                "COUNT(Viesti.v_id) AS v_maara,  MAX(Viesti.lahetysaika) AS viimeinenV " + 
                "FROM Viestiketju LEFT JOIN Viesti ON Viestiketju.vk_id = Viesti.vk_id " +
                "WHERE Viestiketju.a_id = ? " +
                "GROUP BY Viestiketju.vk_id " + 
                "ORDER BY luomisaika DESC");  

        stmt.setObject(1, alueId);
        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();
        
        while (rs.next()) {
            Integer id = rs.getInt("vk_id");
            Integer a_id = rs.getInt("a_id");
            String nimi = rs.getString("vk_otsikko");
            String luoja = rs.getString("luoja");
            String aloitus_viesti = rs.getString("aloitusviesti");
            String luomisaika = rs.getString("luomisaika");
            String viimeinenVAika = rs.getString("viimeinenV");
            int v_maara = rs.getInt("v_maara");

            Viestiketju viestiketju = new Viestiketju(id, a_id, nimi, luoja, aloitus_viesti, luomisaika, viimeinenVAika, v_maara + 1);

            viestiketjut.add(viestiketju);
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;
    }
}
