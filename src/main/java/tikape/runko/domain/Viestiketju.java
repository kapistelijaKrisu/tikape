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
public class Viestiketju {

    private Integer vk_id;
    private Integer a_id;
    private String nimi;
    private String luoja;
    private String aloitusviesti;
    private String luomisaika;
    private String viimeinenViestiAika;
    private int v_maara;

    public Viestiketju(int vk_id, int a_id, String nimi,
            String luoja, String aloitusviesti, String luomisaika) {
        this.vk_id = vk_id;
        this.a_id = a_id;
        this.nimi = nimi;
        this.luoja = luoja;
        this.aloitusviesti = aloitusviesti;
        this.luomisaika = luomisaika;
        this.viimeinenViestiAika = null;
        this.v_maara = 0;

    }
    
    public Viestiketju(int vk_id, int a_id, String nimi,
            String luoja, String aloitusviesti, String luomisaika, String viimeinenViestiAika, int v_maara) {
        this.vk_id = vk_id;
        this.a_id = a_id;
        this.nimi = nimi;
        this.luoja = luoja;
        this.aloitusviesti = aloitusviesti;
        this.luomisaika = luomisaika;
        this.viimeinenViestiAika = viimeinenViestiAika;
        this.v_maara = v_maara;

    }

    public void setV_maara(int v_maara) {
        this.v_maara = v_maara;
    }

    public int getV_maara() {
        return v_maara;
    }

    
    @Override
    public String toString() {
        return "viestiketju id: " + vk_id
                + " nimi: " + nimi
                + " a_id: " + a_id
                + " luoja: " + luoja
                + " luomisaika: " + luomisaika
                + " aloitusviesti: " + aloitusviesti;

    }

    public Integer getVk_id() {
        return vk_id;
    }

    public void setVk_id(Integer vk_id) {
        this.vk_id = vk_id;
    }

    public Integer getA_id() {
        return a_id;
    }

    public void setA_id(Integer a_id) {
        this.a_id = a_id;
    }

    public String getNimi() { //Tämän perkeleen pitää olla nimenomaan muodossa getNimi eikä esim. getVk_otsikko
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getLuoja() {
        return luoja;
    }

    public void setLuoja(String luoja) {
        this.luoja = luoja;
    }

    public String getAloitusviesti() {
        return aloitusviesti;
    }

    public void setAloitusviesti(String aloitusviesti) {
        this.aloitusviesti = aloitusviesti;
    }

    public String getViimV_aika() {
        return viimeinenViestiAika;
    }

    public void setLuomisaika(String luomisasika) {
        this.viimeinenViestiAika = luomisasika;
    }


}
