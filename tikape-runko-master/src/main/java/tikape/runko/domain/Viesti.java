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
public class Viesti {
    
    private Integer v_id;
    private Integer vk_id;
    private String nimimerkki;
    private String viesti;
    private String lähetysaika;
    
    public Viesti(int v_id, int vk_id, String nimimerkki, String viesti, String lähetysaika){
        
    }

    public Integer getV_id() {
        return v_id;
    }

    public void setV_id(Integer v_id) {
        this.v_id = v_id;
    }

    public Integer getVk_id() {
        return vk_id;
    }

    public void setVk_id(Integer vk_id) {
        this.vk_id = vk_id;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public String getViesti() {
        return viesti;
    }

    public void setViesti(String viesti) {
        this.viesti = viesti;
    }

    public String getLähetysaika() {
        return lähetysaika;
    }

    public void setLähetysaika(String lähetysaika) {
        this.lähetysaika = lähetysaika;
    }
    
}
