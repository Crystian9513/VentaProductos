/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "productoventa")
@NamedQueries({
    @NamedQuery(name = "Productoventa.findAll", query = "SELECT p FROM Productoventa p"),
    @NamedQuery(name = "Productoventa.findByCodigo", query = "SELECT p FROM Productoventa p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Productoventa.findByCantidad", query = "SELECT p FROM Productoventa p WHERE p.cantidad = :cantidad")})
public class Productoventa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private int cantidad;
    @JoinColumn(name = "Factura_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Factura facturacodigo;
    @JoinColumn(name = "productos_Codigo_Producto", referencedColumnName = "Codigo_Producto")
    @ManyToOne(optional = false)
    private Productos productosCodigoProducto;

    public Productoventa() {
    }

    public Productoventa(Integer codigo) {
        this.codigo = codigo;
    }

    public Productoventa(Integer codigo, int cantidad) {
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Factura getFacturacodigo() {
        return facturacodigo;
    }

    public void setFacturacodigo(Factura facturacodigo) {
        this.facturacodigo = facturacodigo;
    }

    public Productos getProductosCodigoProducto() {
        return productosCodigoProducto;
    }

    public void setProductosCodigoProducto(Productos productosCodigoProducto) {
        this.productosCodigoProducto = productosCodigoProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productoventa)) {
            return false;
        }
        Productoventa other = (Productoventa) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + codigo + "";
    }
    
}
