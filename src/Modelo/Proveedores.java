/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "proveedores")
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p"),
    @NamedQuery(name = "Proveedores.findByCodigoRut", query = "SELECT p FROM Proveedores p WHERE p.codigoRut = :codigoRut"),
    @NamedQuery(name = "Proveedores.findByNombreRazonSocial", query = "SELECT p FROM Proveedores p WHERE p.nombreRazonSocial = :nombreRazonSocial"),
    @NamedQuery(name = "Proveedores.findByDireccion", query = "SELECT p FROM Proveedores p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Proveedores.findByTelefono1", query = "SELECT p FROM Proveedores p WHERE p.telefono1 = :telefono1"),
    @NamedQuery(name = "Proveedores.findByTelefono2", query = "SELECT p FROM Proveedores p WHERE p.telefono2 = :telefono2")})
public class Proveedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Codigo_Rut")
    private Integer codigoRut;
    @Basic(optional = false)
    @Column(name = "Nombre_RazonSocial")
    private String nombreRazonSocial;
    @Basic(optional = false)
    @Column(name = "Direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "Telefono1")
    private int telefono1;
    @Basic(optional = false)
    @Column(name = "Telefono2")
    private int telefono2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedoresCodigoRut")
    private List<Productos> productosList;

    public Proveedores() {
    }

    public Proveedores(Integer codigoRut) {
        this.codigoRut = codigoRut;
    }

    public Proveedores(Integer codigoRut, String nombreRazonSocial, String direccion, int telefono1, int telefono2) {
        this.codigoRut = codigoRut;
        this.nombreRazonSocial = nombreRazonSocial;
        this.direccion = direccion;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
    }

    public Integer getCodigoRut() {
        return codigoRut;
    }

    public void setCodigoRut(Integer codigoRut) {
        this.codigoRut = codigoRut;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(int telefono1) {
        this.telefono1 = telefono1;
    }

    public int getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(int telefono2) {
        this.telefono2 = telefono2;
    }

    public List<Productos> getProductosList() {
        return productosList;
    }

    public void setProductosList(List<Productos> productosList) {
        this.productosList = productosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoRut != null ? codigoRut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.codigoRut == null && other.codigoRut != null) || (this.codigoRut != null && !this.codigoRut.equals(other.codigoRut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreRazonSocial;
    }
    
}
