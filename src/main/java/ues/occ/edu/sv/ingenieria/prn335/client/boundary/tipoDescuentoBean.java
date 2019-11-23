/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.client.boundary;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.TipoDescuento;
import ues.occ.edu.sv.ingenieria.prn335.client.client.AbstractClient;
import ues.occ.edu.sv.ingenieria.prn335.client.client.TipoDescuentoClient;

/**
 *
 * @author christian
 */
@Named(value = "BeanTipoDescuento")
@ViewScoped
public class tipoDescuentoBean extends BackingBean<TipoDescuento> implements Serializable {

    @Inject
    TipoDescuentoClient sucCli;
    private TipoDescuento tipo;
    private TipoDescuento selec;
    
    WebTarget raiz;
    Client cliente;
    String url="http://localhost:8080/Server/webresources/";
    
    
    @PostConstruct
    @Override
    public void iniciar() {
        cliente = ClientBuilder.newClient();
        raiz = cliente.target(url);
        tipo = new TipoDescuento();
        super.iniciar();
    }
    
    @Override
    protected AbstractClient<TipoDescuento> getClient(){
    return sucCli;
    }

    @Override
    public Object clavePorDatos(TipoDescuento object) {
        if (object != null) {
            return object.getIdTipoDescuento();
        }
        return null;
    }

    @Override
    public TipoDescuento datosPorClave(String rowKey) {
        if (rowKey != null && !rowKey.trim().isEmpty()) {
            try {
                return this.getLazy().getWrappedData().stream().filter(r -> r.getIdTipoDescuento().toString().compareTo(rowKey) == 0).collect(Collectors.toList()).get(0);
            } catch (Exception ex) {

            }
        }
        return null;
    }
    
    
    
    public void guardar() {
     System.out.println(".........................................................");
//        super.crear(tipo);
        
        
        
       // sucCli.create(tipo);
       
       FacesContext context = FacesContext.getCurrentInstance();
        Response respuesta = raiz.path("/tipodescuento").request().post(Entity.json(tipo));
        if (respuesta.getStatus() == 201) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "El registro ha sido creado con exito"));
        } else if (respuesta.getStatus() == 500) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El registro no ha sido creado"));
        }
        iniciar();
        
    }
    
//    public TipoDescuento creador(){
//        System.out.println(".....................................REGISTRO:  "+tipo);
//        
//        Client client=ClientBuilder.newClient();
//        
//        if (tipo!=null) {
//            try {
//                if (client!=null) {
//                    WebTarget target =client.target("http://localhost:8080/Server/webresources/tipodescuento");
//                    TipoDescuento salida = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(tipo, MediaType.APPLICATION_JSON), TipoDescuento.class);
//                    if (salida!=null) {
//                        System.out.println("..........................."+salida);
//                        return salida;
//                    }
//                }
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
//        }
//        }
//        return null;
  //  }

    public void seleccionarRegistro(SelectEvent event) {
        selec = (TipoDescuento) event.getObject();
        setTipo(selec);
    }

    public void desceleccionarRegistro(UnselectEvent event) {
        this.selec = new TipoDescuento();
        this.lazy.setRowIndex(-1);
    }

    @Override
    public LazyDataModel<TipoDescuento> getLazy() {
        return super.getLazy();
    }

    

    public TipoDescuento getTipo() {
        return tipo;
    }

    public void setTipo(TipoDescuento tipo) {
        this.tipo = tipo;
    }
    
    
    public TipoDescuento getSelec() {
        return selec;
    }

    public void setSelec(TipoDescuento selec) {
        this.selec = selec;
    }
}
