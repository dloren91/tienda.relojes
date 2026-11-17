package com.lorenzo.tienda.relojes.responseData;

import java.util.List;
import java.util.Map;

//tipo de dato para devolver los videojuegos y su total para el js mostrarRelojes
public class InfoRelojes {

    private List<Map<String,Object>> relojes;
    private int totalRelojes;







    public List<Map<String, Object>> getRelojes() {
        return relojes;
    }

    public void setRelojes(List<Map<String, Object>> relojes) {
        this.relojes = relojes;
    }

    public int getTotalRelojes() {
        return totalRelojes;
    }

    public void setTotalRelojes(int totalRelojes) {
        this.totalRelojes = totalRelojes;
    }
}
