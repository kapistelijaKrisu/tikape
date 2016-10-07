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
    public List findOne(Object key) throws SQLException { //Listaa tietyn alueen kaikki viestiketjut (alue.html)

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viestiketju.vk_otsikko,COUNT(Viesti),Viestiketju.vk_id,Alue.a_id,vk_otsikko,Viestiketju.luoja,Viestiketju.aloitusviesti,Viestiketju.luomisaika FROM Alue JOIN Viestiketju ON Alue.a_id=Viestiketju.a_id JOIN Viesti ON Viestiketju.vk_id=Viesti.vk_id WHERE Viestiketju.a_id = ? GROUP BY Viestiketju.vk_id");
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
            Integer viestimäärä = rs.getInt("COUNT(Viesti)");
            viestiketjut.add(new Viestiketju(vk_id, a_id, vk_otsikko, luoja, aloitusviesti, luomisaika,viestimäärä));
        }

        rs.close();
        stmt.close();
        connection.close();

        for (Viestiketju viestiketju : viestiketjut) {
            System.out.print(" " + viestiketju.getVk_id() + " ");
            System.out.println(viestiketju.getNimi());
            System.out.println(viestiketju.getViestimäärä());
        }


   

        return viestiketjut;
    }

    @Override
    public List findAll() throws SQLException { //hakee tietokannasta index.html luomiseen tarvittavat tiedot.

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT (SELECT COUNT(Viesti) FROM Alue JOIN Viestiketju ON Alue.a_id=Viestiketju.a_id JOIN Viesti ON Viestiketju.vk_id=Viesti.vk_id GROUP BY Alue.a_id) AS aluekohtainenViestimaara,Alue.a_id,otsikko,Alue.luoja,kuvaus,Alue.luomisaika FROM Alue JOIN Viestiketju ON Alue.a_id=Viestiketju.a_id JOIN Viesti ON Viestiketju.vk_id=Viesti.vk_id GROUP BY Alue.a_id;");
        
        
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(Viesti) AS aluekohtainenViestimaara FROM Alue JOIN Viestiketju ON Alue.a_id=Viestiketju.a_id JOIN Viesti ON Viestiketju.vk_id=Viesti.vk_id GROUP BY Alue.a_id");
        ResultSet setti2 = statement.executeQuery();
        
        PreparedStatement aika = connection.prepareStatement("SELECT Alue.a_id,Max(Viesti.lahetysaika) AS viimeinen FROM Viesti JOIN Viestiketju ON Viesti.vk_id=Viestiketju.vk_id JOIN Alue ON Viestiketju.a_id=Alue.a_id GROUP BY Viestiketju.a_id ORDER BY Viesti.lahetysaika DESC");
        ResultSet ajat = aika.executeQuery();
        
        
        
        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            
            setti2.next();
            ajat.next();
            
            
            Integer a_id = rs.getInt("a_id");
            String otsikko = rs.getString("otsikko");
            String luoja = rs.getString("luoja");
            String kuvaus = rs.getString("kuvaus");
            String luomisaika = rs.getString("luomisaika");
            
            
            String viimeisinviestiAika = ajat.getString("viimeinen");
            String aluekohtainenViestimäärä = setti2.getString("aluekohtainenViestimaara");
            
            System.out.println(a_id + otsikko + luoja + kuvaus + luomisaika);
            
            alueet.add(new Alue(a_id, otsikko, luoja, kuvaus, luomisaika, viimeisinviestiAika,aluekohtainenViestimäärä));
        }
        
        
        
        
        

        rs.close();
        stmt.close();
        statement.close();
        setti2.close();
        aika.close();
        ajat.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Object key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
