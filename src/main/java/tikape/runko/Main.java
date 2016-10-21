package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.dao.*;
import tikape.runko.database.*;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Sivunumerot;
import tikape.runko.domain.Viestiketju;

public class Main {

    public static void main(String[] args) throws Exception {

        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:foorumi.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        Database db = new Database(jdbcOsoite);

        Spark.staticFileLocation("/templates");
        Database database = new Database(jdbcOsoite);
        database.init();
        boolean debug = false;

        AlueDao alueDao = new AlueDao(database);
        ViestiketjuDao vKetjuDao = new ViestiketjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> { //Juuressa näytetään kaikki alueet
            HashMap map = new HashMap<>();
            List<Alue> areas = alueDao.findAllwithMsgCount();

            if (debug) {
                Debug.print(areas, "at main 28");
            }
            map.put("alueet", areas);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alue/:a_id", (req, res) -> { // Näytetään aluekohtaiset viestiketjut
            HashMap map = new HashMap<>();
            List<Viestiketju> vkByArea = vKetjuDao.findAllWithMsgCountByAlyeId(Integer.parseInt(req.params("a_id")));

            map.put("ketjut", vkByArea);

            if (debug) {
                Debug.print(vkByArea, "main at 39");
            }
            map.put("otsikko", alueDao.etsiAlueenOtsikko(Integer.parseInt(req.params("a_id"))));
            map.put("alueet", vkByArea);

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/ketju/:vk_id", (req, res) -> { // Näytetään aluekohtaiset viestit
            HashMap map = new HashMap<>();
            
            
            int sivu;
            if (req.queryParams("sivu") == null) {
                sivu = 1;
            } else {
                sivu = Integer.parseInt(req.queryParams("sivu"));
                if (sivu <= 0) {
                    sivu = 1;
                }
            }
            List vByVk = viestiDao.findAllByViestiKejuId(Integer.parseInt(req.params("vk_id")), sivu);
            int viestienMaara = vKetjuDao.getViestienMaaraKetjus(Integer.parseInt(req.params("vk_id")));
            map.put("viestit", vByVk);
            Viestiketju omistaja = vKetjuDao.findOne(Integer.parseInt(req.params("vk_id")));
            System.out.println(omistaja);
            map.put("omistaja", omistaja);

            map.put("viestit", vByVk);
            map.put("sivunumerot", new Sivunumerot(sivu <= 1 ? 1 : sivu - 1, sivu >= (int) Math.ceil(viestienMaara / 10.0) ? (int) Math.ceil(viestienMaara / 10.0) : sivu + 1));
            map.put("otsikko", vKetjuDao.findOne(Integer.parseInt(req.params("vk_id"))).getNimi());
            map.put("aloitusviesti", vKetjuDao.findOne(Integer.parseInt(req.params("vk_id"))).getAloitusviesti());

            return new ModelAndView(map, "viesti");
        }, new ThymeleafTemplateEngine());

        post("/ketju/:vk_id", (req, res) -> { //lisätään viesti 

            //   if (req.queryParams("teksti").length() > 0 && req.queryParams("nimi").length() > 0){              
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", req.params("vk_id"));
            params.put("nimimerkki", req.queryParams("nimi"));
            params.put("viesti", req.queryParams("teksti"));

            if (debug) {
                Debug.print(params, "main at 63");
            }

            viestiDao.add(params);
            //viestiDao.fixSize(Integer.parseInt(req.params("vk_id")));

            //}
            res.redirect("/ketju/" + req.params("vk_id"));
            return "Kerrotaan siitä tiedon lähettäjälle";
        }
        );

        post("/alue/:a_id", (req, res) -> { //lisätään ketju 

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", req.params("a_id"));
            params.put("otsikko", req.queryParams("otsikko"));
            params.put("luoja", req.queryParams("luoja"));
            params.put("viesti", req.queryParams("viesti"));

            if (debug) {
                Debug.print(params, "main at 63");
            }
            vKetjuDao.add(params);
            vKetjuDao.fixSize(Integer.parseInt(req.params("a_id")));

            res.redirect("/alue/" + req.params("a_id"));
            return "Kerrotaan siitä tiedon lähettäjälle";
        }
        );

        post("/", (req, res) -> { //lisätään alue 

            HashMap<String, Object> params = new HashMap<>();

            params.put("otsikko", req.queryParams("otsikko"));
            params.put("luoja", req.queryParams("luoja"));
            params.put("kuvaus", req.queryParams("kuvaus"));
            
            
            alueDao.add(params);

            res.redirect("/");
            return "Kerrotaan siitä tiedon lähettäjälle";
        }
        );

    }
}
