/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.client.client;

import javax.ejb.Stateless;
import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.TipoDescuento;

/**
 *
 * @author christian
 */
@Stateless
public class TipoDescuentoClient extends AbstractClient<TipoDescuento>{
    
    /**
     * pasamos el path de la clase en el servidor al ClienteAbstracto
     */
    public TipoDescuentoClient() {
        this.entidad="tipodescuento"; 
    }
    
    
    
    
}
