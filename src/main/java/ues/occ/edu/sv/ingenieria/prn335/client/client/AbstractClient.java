/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.client.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author christian
 */
public abstract class AbstractClient<T> implements Serializable{

    Client client;
    String entidad;
    String clase;
    
    private Class<T> entityClass;
    
    
    public AbstractClient(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected final static String urlServer="http://localhost:8080/Server/webresources/";

    public AbstractClient() {
        try {
            client = ClientBuilder.newClient();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public List<T> findRange(int first, int pagesize) {
        List salida = null;
        try {
            WebTarget target = client.target(urlServer+entidad).queryParam("first", first).queryParam("pagesize", pagesize);
            salida = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<T>>() {
            });
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (salida == null) {
                salida = new ArrayList();
            }
        }
        return salida;
    }

    public int count() {
        try {
            WebTarget target = client.target(urlServer+entidad).path("count");
            Integer salida = target.request(MediaType.TEXT_PLAIN).get(Integer.class);
            return salida;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
    
     public Response create(Object requestEntity) throws ClientErrorException {
        WebTarget target = client.target(urlServer+entidad);
        
        Response respuesta = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        return respuesta;
    }
    
    @Override
    protected void finalize() throws Throwable {
        try {
            if (this.client != null) {
                this.client.close();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    
    
}
