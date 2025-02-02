/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Categorias;
import Modelo.Productos;
import Modelo.Proveedores;
import Modelo.Productoventa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class ProductosJpaController implements Serializable {

    public ProductosJpaController( ) {
         this.emf = Persistence.createEntityManagerFactory("ProductosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productos productos) throws PreexistingEntityException, Exception {
        if (productos.getProductoventaList() == null) {
            productos.setProductoventaList(new ArrayList<Productoventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias categoriasCodigo = productos.getCategoriasCodigo();
            if (categoriasCodigo != null) {
                categoriasCodigo = em.getReference(categoriasCodigo.getClass(), categoriasCodigo.getCodigo());
                productos.setCategoriasCodigo(categoriasCodigo);
            }
            Proveedores proveedoresCodigoRut = productos.getProveedoresCodigoRut();
            if (proveedoresCodigoRut != null) {
                proveedoresCodigoRut = em.getReference(proveedoresCodigoRut.getClass(), proveedoresCodigoRut.getCodigoRut());
                productos.setProveedoresCodigoRut(proveedoresCodigoRut);
            }
            List<Productoventa> attachedProductoventaList = new ArrayList<Productoventa>();
            for (Productoventa productoventaListProductoventaToAttach : productos.getProductoventaList()) {
                productoventaListProductoventaToAttach = em.getReference(productoventaListProductoventaToAttach.getClass(), productoventaListProductoventaToAttach.getCodigo());
                attachedProductoventaList.add(productoventaListProductoventaToAttach);
            }
            productos.setProductoventaList(attachedProductoventaList);
            em.persist(productos);
            if (categoriasCodigo != null) {
                categoriasCodigo.getProductosList().add(productos);
                categoriasCodigo = em.merge(categoriasCodigo);
            }
            if (proveedoresCodigoRut != null) {
                proveedoresCodigoRut.getProductosList().add(productos);
                proveedoresCodigoRut = em.merge(proveedoresCodigoRut);
            }
            for (Productoventa productoventaListProductoventa : productos.getProductoventaList()) {
                Productos oldProductosCodigoProductoOfProductoventaListProductoventa = productoventaListProductoventa.getProductosCodigoProducto();
                productoventaListProductoventa.setProductosCodigoProducto(productos);
                productoventaListProductoventa = em.merge(productoventaListProductoventa);
                if (oldProductosCodigoProductoOfProductoventaListProductoventa != null) {
                    oldProductosCodigoProductoOfProductoventaListProductoventa.getProductoventaList().remove(productoventaListProductoventa);
                    oldProductosCodigoProductoOfProductoventaListProductoventa = em.merge(oldProductosCodigoProductoOfProductoventaListProductoventa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductos(productos.getCodigoProducto()) != null) {
                throw new PreexistingEntityException("Productos " + productos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productos productos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos persistentProductos = em.find(Productos.class, productos.getCodigoProducto());
            Categorias categoriasCodigoOld = persistentProductos.getCategoriasCodigo();
            Categorias categoriasCodigoNew = productos.getCategoriasCodigo();
            Proveedores proveedoresCodigoRutOld = persistentProductos.getProveedoresCodigoRut();
            Proveedores proveedoresCodigoRutNew = productos.getProveedoresCodigoRut();
            List<Productoventa> productoventaListOld = persistentProductos.getProductoventaList();
            List<Productoventa> productoventaListNew = productos.getProductoventaList();
            List<String> illegalOrphanMessages = null;
            for (Productoventa productoventaListOldProductoventa : productoventaListOld) {
                if (!productoventaListNew.contains(productoventaListOldProductoventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Productoventa " + productoventaListOldProductoventa + " since its productosCodigoProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoriasCodigoNew != null) {
                categoriasCodigoNew = em.getReference(categoriasCodigoNew.getClass(), categoriasCodigoNew.getCodigo());
                productos.setCategoriasCodigo(categoriasCodigoNew);
            }
            if (proveedoresCodigoRutNew != null) {
                proveedoresCodigoRutNew = em.getReference(proveedoresCodigoRutNew.getClass(), proveedoresCodigoRutNew.getCodigoRut());
                productos.setProveedoresCodigoRut(proveedoresCodigoRutNew);
            }
            List<Productoventa> attachedProductoventaListNew = new ArrayList<Productoventa>();
            for (Productoventa productoventaListNewProductoventaToAttach : productoventaListNew) {
                productoventaListNewProductoventaToAttach = em.getReference(productoventaListNewProductoventaToAttach.getClass(), productoventaListNewProductoventaToAttach.getCodigo());
                attachedProductoventaListNew.add(productoventaListNewProductoventaToAttach);
            }
            productoventaListNew = attachedProductoventaListNew;
            productos.setProductoventaList(productoventaListNew);
            productos = em.merge(productos);
            if (categoriasCodigoOld != null && !categoriasCodigoOld.equals(categoriasCodigoNew)) {
                categoriasCodigoOld.getProductosList().remove(productos);
                categoriasCodigoOld = em.merge(categoriasCodigoOld);
            }
            if (categoriasCodigoNew != null && !categoriasCodigoNew.equals(categoriasCodigoOld)) {
                categoriasCodigoNew.getProductosList().add(productos);
                categoriasCodigoNew = em.merge(categoriasCodigoNew);
            }
            if (proveedoresCodigoRutOld != null && !proveedoresCodigoRutOld.equals(proveedoresCodigoRutNew)) {
                proveedoresCodigoRutOld.getProductosList().remove(productos);
                proveedoresCodigoRutOld = em.merge(proveedoresCodigoRutOld);
            }
            if (proveedoresCodigoRutNew != null && !proveedoresCodigoRutNew.equals(proveedoresCodigoRutOld)) {
                proveedoresCodigoRutNew.getProductosList().add(productos);
                proveedoresCodigoRutNew = em.merge(proveedoresCodigoRutNew);
            }
            for (Productoventa productoventaListNewProductoventa : productoventaListNew) {
                if (!productoventaListOld.contains(productoventaListNewProductoventa)) {
                    Productos oldProductosCodigoProductoOfProductoventaListNewProductoventa = productoventaListNewProductoventa.getProductosCodigoProducto();
                    productoventaListNewProductoventa.setProductosCodigoProducto(productos);
                    productoventaListNewProductoventa = em.merge(productoventaListNewProductoventa);
                    if (oldProductosCodigoProductoOfProductoventaListNewProductoventa != null && !oldProductosCodigoProductoOfProductoventaListNewProductoventa.equals(productos)) {
                        oldProductosCodigoProductoOfProductoventaListNewProductoventa.getProductoventaList().remove(productoventaListNewProductoventa);
                        oldProductosCodigoProductoOfProductoventaListNewProductoventa = em.merge(oldProductosCodigoProductoOfProductoventaListNewProductoventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productos.getCodigoProducto();
                if (findProductos(id) == null) {
                    throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos productos;
            try {
                productos = em.getReference(Productos.class, id);
                productos.getCodigoProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Productoventa> productoventaListOrphanCheck = productos.getProductoventaList();
            for (Productoventa productoventaListOrphanCheckProductoventa : productoventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productos (" + productos + ") cannot be destroyed since the Productoventa " + productoventaListOrphanCheckProductoventa + " in its productoventaList field has a non-nullable productosCodigoProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categorias categoriasCodigo = productos.getCategoriasCodigo();
            if (categoriasCodigo != null) {
                categoriasCodigo.getProductosList().remove(productos);
                categoriasCodigo = em.merge(categoriasCodigo);
            }
            Proveedores proveedoresCodigoRut = productos.getProveedoresCodigoRut();
            if (proveedoresCodigoRut != null) {
                proveedoresCodigoRut.getProductosList().remove(productos);
                proveedoresCodigoRut = em.merge(proveedoresCodigoRut);
            }
            em.remove(productos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productos> findProductosEntities() {
        return findProductosEntities(true, -1, -1);
    }

    public List<Productos> findProductosEntities(int maxResults, int firstResult) {
        return findProductosEntities(false, maxResults, firstResult);
    }

    private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Productos findProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos> rt = cq.from(Productos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
