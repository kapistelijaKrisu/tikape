/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

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

    public Alue(int a_id, String otsikko, String luoja, String kuvaus, String luomisaika) {
        this.a_id = a_id;
        this.otsikko = otsikko;
        this.luoja = luoja;
        this.kuvaus = kuvaus;
        this.luomisaika = luomisaika;
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

    public String getLuomisaika() {
        return luomisaika;
    }

    public void setLuomisaika(String luomisasika) {
        this.luomisaika = luomisasika;
    }

}
