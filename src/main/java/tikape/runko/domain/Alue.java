/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import tikape.runko.Debug;

/**
 *
 * @author marko
 */
public class Alue {

    private Integer a_id;
    private String otsikko;
    private String luoja;
    private String kuvaus;
    private String luomisaika;
    private String viimeinenViestiAika;
    private int v_maara;

    public Alue(int a_id, String otsikko, String luoja, String kuvaus, String luomisaika) {
        this.a_id = a_id;
        this.otsikko = otsikko;
        this.luoja = luoja;
        this.kuvaus = kuvaus;
        this.viimeinenViestiAika = null;
        this.luomisaika = luomisaika;
        v_maara = 0;
    }
    
    public Alue(int a_id, String otsikko, String luoja, String kuvaus, String luomisaika, String viimeinenViestiAika, int v_maara) {
        this.a_id = a_id;
        this.otsikko = otsikko;
        this.luoja = luoja;
        this.kuvaus = kuvaus;
        this.luomisaika = luomisaika;
        this.viimeinenViestiAika = viimeinenViestiAika;
        this.v_maara = v_maara;
    }

    public int getV_maara() {
        return v_maara;
    }

    public void setV_maara(int v_maara) {
        this.v_maara = v_maara;
    }
    
    

    @Override
    public String toString() {
        return "Alue: id:" + a_id
                + " otsikko: " + otsikko
                + " luoja: " + luoja
                + " kuvaus: " + kuvaus
                + " aika: " + viimeinenViestiAika;
    }

    public Integer getA_id() {
        return a_id;
    }

    public void setA_id(Integer a_id) {
        this.a_id = a_id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public String getLuoja() {
        return luoja;
    }

    public void setLuoja(String luoja) {
        this.luoja = luoja;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public String getViimV_aika() {
        return viimeinenViestiAika;
    }

    public void setLuomisaika(String luomisasika) {
        this.viimeinenViestiAika = luomisasika;
    }

    public String getLuomisaika() {
        return luomisaika;
    }

    public String getViimeinenViestiAika() {
        return viimeinenViestiAika;
    }

    public void setViimeinenViestiAika(String viimeinenViestiAika) {
        this.viimeinenViestiAika = viimeinenViestiAika;
    }
    
    

}
