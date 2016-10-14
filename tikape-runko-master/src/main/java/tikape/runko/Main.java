package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.dao.*;
import tikape.runko.database.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Spark.staticFileLocation("/templates");
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();
        boolean debug = true;

        AlueDao alueDao = new AlueDao(database);
        ViestiketjuDao vKetjuDao = new ViestiketjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> { //Juuressa näytetään kaikki alueet
            HashMap map = new HashMap<>();
            List areas = alueDao.findAll();

            if (debug) {
                Debug.print(areas, "at main 28");
            }
            map.put("alueet", areas);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alue/:a_id", (req, res) -> { // Näytetään aluekohtaiset viestiketjut
            HashMap map = new HashMap<>();
            List vkByArea = vKetjuDao.findAllByAlueId(Integer.parseInt(req.params("a_id")));

            if (debug) {
                Debug.print(vkByArea, "main at 39");
            }
            map.put("alueet", vkByArea);

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/ketju/:vk_id", (req, res) -> { // Näytetään aluekohtaiset viestit
            HashMap map = new HashMap<>();
            List vByVk = viestiDao.findAllByViestiKejuId(Integer.parseInt(req.params("vk_id")));

            if (debug) {
                Debug.print(vByVk, "main at 50");
            }
            map.put("viestit", vByVk);

            return new ModelAndView(map, "viesti");
        }, new ThymeleafTemplateEngine());

        post("/ketju/:vk_id", (req, res) -> { //lisätään viesti 
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", req.params("vk_id"));
            params.put("nimimerkki", req.queryParams("nimi"));
            params.put("viesti", req.queryParams("teksti"));

            if (debug) {
                Debug.print(params, "main at 63");
            }
            viestiDao.add(params);

            res.redirect("/ketju/" + req.params("vk_id"));
            return "Kerrotaan siitä tiedon lähettäjälle";
        }
        );

    }
    /*
    for lazy developing to avoid switching class all the damn time
    
    public static int update(Connection connection, String updateQuery, Object... params) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();

        stmt.close();
        connection.close();
        return changes;
    }
     */
}


/*  
viestin postaus alkuperäinen
System.out.println(req.params("vk_id"));
            System.out.println(req.queryParams("nimi"));
            System.out.println(req.queryParams("teksti"));
            Connection c = database.getConnection();
            
            
                 int a = update(c, "INSERT INTO Viesti (vk_id, nimimerkki, viesti, lahetysaika) VALUES (?, ?, ?, ?)",           
                    Integer.parseInt(req.params("vk_id")),
                    req.queryParams("nimi"),
                    req.queryParams("teksti"),
                    req.queryParams("Datetime('now')"));*/
