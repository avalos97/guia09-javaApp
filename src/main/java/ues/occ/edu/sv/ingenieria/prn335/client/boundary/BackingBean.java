/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.client.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import ues.occ.edu.sv.ingenieria.prn335.client.client.AbstractClient;

/**
 *
 * @author christian
 */
public abstract class BackingBean<T> {

    protected T registro;

    public abstract Object clavePorDatos(T object);

    public abstract T datosPorClave(String rowKey);

    List<T> List = new ArrayList<>();
    protected LazyDataModel<T> lazy;

    protected abstract AbstractClient<T> getClient();

    public void iniciar() {
        ModeloLazy();
    }

    public LazyDataModel<T> ModeloLazy() {
        try {
            lazy = new LazyDataModel<T>() {
                @Override
                public Object getRowKey(T object) {
                    return clavePorDatos(object);
                }

                @Override
                public T getRowData(String rowKey) {
                    return datosPorClave(rowKey);
                }

                @Override
                public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<T> salida = new ArrayList<>();
                    try {
                        if (getClient() != null) {
                            this.setRowCount(getClient().count());
                            salida = getClient().findRange(first, first);
                        }

                    } catch (Exception e) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
            };
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        this.lazy.setRowIndex(-1);
        return null;
    }

    public void crear(T entity) {
        if (entity != null) {
            if (getClient() != null) {
                try {
                    getClient().create(entity);
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
    }

    public T getRegistro() {
        return registro;
    }

    public void setRegistro(T registro) {
        this.registro = registro;
    }

    public LazyDataModel<T> getLazy() {
        return lazy;
    }

    public void setLazy(LazyDataModel<T> lazy) {
        this.lazy = lazy;
    }
}
