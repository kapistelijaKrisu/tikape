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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tikape.runko.Debug;
import tikape.runko.database.Database;
import tikape.runko.domain.Viesti;

/**
 *
 * @author kbvalto
 */
public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE Alue.a_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        Viesti chat = null;
        while (rs.next()) {
            Integer v_id = rs.getInt("v_id");
            Integer vk_id = rs.getInt("vk_id");
            String nimimerkki = rs.getString("nimimerkki");
            String viesti = rs.getString("viesti");
            String lähetysaika = rs.getString("lähetysaika");
            System.out.println(rs);
            chat = new Viesti(v_id, vk_id, nimimerkki, viesti, lähetysaika);
        }

        rs.close();
        stmt.close();
        connection.close();

        return chat;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue ORDER BY lahetysaika DESC");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            Integer v_id = rs.getInt("v_id");
            Integer vk_id = rs.getInt("vk_id");
            String nimimerkki = rs.getString("nimimerkki");
            String viesti = rs.getString("viesti");
            String lähetysaika = rs.getString("lähetysaika");
            System.out.println(rs);
            viestit.add(new Viesti(v_id, vk_id, nimimerkki, viesti, lähetysaika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    public List<Viesti> findAllByViestiKejuId(Integer id, Integer sivu) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE Viesti.vk_id = ? ORDER BY lahetysaika LIMIT ?, 10");
        
        int limitAlku = (sivu - 1) * 10;
        
        stmt.setObject(1, id);
        stmt.setObject(2, limitAlku);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            //    System.out.println(rs.getString("viesti") + " " + rs.getString("vk_id"));
            Integer v_id = rs.getInt("v_id");
            Integer vk_id = rs.getInt("vk_id");
            String nimimerkki = rs.getString("nimimerkki");
            String viesti = rs.getString("viesti");
            String lahetysaika = rs.getString("lahetysaika");
            Viesti v = new Viesti(v_id, vk_id, nimimerkki, viesti, lahetysaika);
            viestit.add(v);
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void add(HashMap<String, Object> params) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement p = c.prepareStatement("INSERT INTO Viesti (vk_id, nimimerkki, viesti, lahetysaika) VALUES (?, ?, ?, Datetime('now'))");

        p.setObject(1, params.get("id"));
        p.setObject(2, params.get("nimimerkki"));
        p.setObject(3, params.get("viesti"));
        int a = p.executeUpdate();

        p.close();
        c.close();
    }

    /*public void fixSize(Integer id) throws SQLException {

        List<Viesti> viestit = findAllByViestiKejuId(id);

        
        if (viestit.size() > 10) {

            Connection connection = database.getConnection();

            for (int i = 10; i < viestit.size(); i++) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM Viesti WHERE Viesti.v_id = ?");
                stmt.setObject(1, viestit.get(i).getV_id());
                stmt.execute();

            }
            connection.close();
        }

    }*/

}
