package tikape.runko;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.*;
import tikape.runko.domain.Viesti;

public class Main {

    public static void main(String[] args) throws Exception {
   
        
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        AlueDao alueDao = new AlueDao(database);
        ViestiketjuDao vKetjuDao = new ViestiketjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> { //Juuressa näytetään kaikki alueet
            HashMap map = new HashMap<>();
            
            map.put("alueet", alueDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        get("/alue/:a_id", (req, res) -> { // Näytetään aluekohtaiset viestiketjut
            HashMap map = new HashMap<>();
           // System.out.println(Integer.parseInt(req.params("a_id")));
            map.put("alueet", vKetjuDao.findAllByAlueId(Integer.parseInt(req.params("a_id"))));

            
            return new ModelAndView(map, "alue");
            
        }, new ThymeleafTemplateEngine());
        
        get("/ketju/:vk_id", (req, res) -> { // Näytetään aluekohtaiset viestit
            HashMap map = new HashMap<>();
         
            map.put("viestit", viestiDao.findAllByViestiKejuId(Integer.parseInt(req.params("vk_id"))));
            
            return new ModelAndView(map, "viesti");
            
        }, new ThymeleafTemplateEngine());
        
     /* post("/postaus", (req, res) -> {
            HashMap map = new HashMap<>();
            Connection c = database.getConnection();
            
            update(c, "INSERT INTO Viesti(vk_id, nimimerkki, viesti, lahetysaika) VALUES(?, ?, ?, ?)", 
                    Integer.parseInt(req.params("a_id")),
                    req.queryParams("username"),
                    req.queryParams("viesti"),
                    req.queryParams("Datetime('now')"));
                    
                    
            res.redirect("/ketju/vk_id");
            return "";
        });
     */   
    }
    
     public static int update(Connection connection, String updateQuery, Object... params) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        int changes = stmt.executeUpdate();
    
        stmt.close();
        return changes;
    }
     
    
}
