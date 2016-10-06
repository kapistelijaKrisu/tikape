package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        AlueDao alueDao = new AlueDao(database);

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
            map.put("alueet", alueDao.findOne(Integer.parseInt(req.params("a_id"))));


            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
    }
}
