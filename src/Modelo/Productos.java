/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "productos")
@NamedQueries({
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p"),
    @NamedQuery(name = "Productos.findByCodigoProducto", query = "SELECT p FROM Productos p WHERE p.codigoProducto = :codigoProducto"),
    @NamedQuery(name = "Productos.findByNombreProducto", query = "SELECT p FROM Productos p WHERE p.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "Productos.findByDescripcion", query = "SELECT p FROM Productos p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Productos.findByUnidadMedida", query = "SELECT p FROM Productos p WHERE p.unidadMedida = :unidadMedida"),
    @NamedQuery(name = "Productos.findByPrecioUnitario", query = "SELECT p FROM Productos p WHERE p.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "Productos.findByCantidadIngreso", query = "SELECT p FROM Productos p WHERE p.cantidadIngreso = :cantidadIngreso"),
    @NamedQuery(name = "Productos.findByCantidadDisponible", query = "SELECT p FROM Productos p WHERE p.cantidadDisponible = :cantidadDisponible"),
    @NamedQuery(name = "Productos.findByFechaIngreso", query = "SELECT p FROM Productos p WHERE p.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Productos.findByFechaCaducidad", query = "SELECT p FROM Productos p WHERE p.fechaCaducidad = :fechaCaducidad")})
public class Productos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Codigo_Producto")
    private Integer codigoProducto;
    @Basic(optional = false)
    @Column(name = "Nombre_Producto")
    private String nombreProducto;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "Unidad_Medida")
    private String unidadMedida;
    @Basic(optional = false)
    @Column(name = "Precio_Unitario")
    private int precioUnitario;
    @Basic(optional = false)
    @Column(name = "Cantidad_Ingreso")
    private int cantidadIngreso;
    @Basic(optional = false)
    @Column(name = "Cantidad_Disponible")
    private int cantidadDisponible;
    @Basic(optional = false)
    @Column(name = "Fecha_Ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Basic(optional = false)
    @Column(name = "Fecha_Caducidad")
    @Temporal(TemporalType.DATE)
    private Date fechaCaducidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productosCodigoProducto")
    private List<Productoventa> productoventaList;
    @JoinColumn(name = "Categorias_Codigo", referencedColumnName = "Codigo")
    @ManyToOne(optional = false)
    private Categorias categoriasCodigo;
    @JoinColumn(name = "Proveedores_Codigo_Rut", referencedColumnName = "Codigo_Rut")
    @ManyToOne(optional = false)
    private Proveedores proveedoresCodigoRut;

    public Productos() {
    }

    public Productos(Integer codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Productos(Integer codigoProducto, String nombreProducto, String descripcion, String unidadMedida, int precioUnitario, int cantidadIngreso, int cantidadDisponible, Date fechaIngreso, Date fechaCaducidad) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.cantidadIngreso = cantidadIngreso;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaIngreso = fechaIngreso;
        this.fechaCaducidad = fechaCaducidad;
    }

    public Integer getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Integer codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidadIngreso() {
        return cantidadIngreso;
    }

    public void setCantidadIngreso(int cantidadIngreso) {
        this.cantidadIngreso = cantidadIngreso;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public List<Productoventa> getProductoventaList() {
        return productoventaList;
    }

    public void setProductoventaList(List<Productoventa> productoventaList) {
        this.productoventaList = productoventaList;
    }

    public Categorias getCategoriasCodigo() {
        return categoriasCodigo;
    }

    public void setCategoriasCodigo(Categorias categoriasCodigo) {
        this.categoriasCodigo = categoriasCodigo;
    }

    public Proveedores getProveedoresCodigoRut() {
        return proveedoresCodigoRut;
    }

    public void setProveedoresCodigoRut(Proveedores proveedoresCodigoRut) {
        this.proveedoresCodigoRut = proveedoresCodigoRut;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoProducto != null ? codigoProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.codigoProducto == null && other.codigoProducto != null) || (this.codigoProducto != null && !this.codigoProducto.equals(other.codigoProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreProducto;
    }
    
}
