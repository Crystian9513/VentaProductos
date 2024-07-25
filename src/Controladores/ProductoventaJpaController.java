/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Factura;
import Modelo.Productos;
import Modelo.Productoventa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class ProductoventaJpaController implements Serializable {

    public ProductoventaJpaController( ) {
       this.emf = Persistence.createEntityManagerFactory("ProductosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productoventa productoventa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura facturacodigo = productoventa.getFacturacodigo();
            if (facturacodigo != null) {
                facturacodigo = em.getReference(facturacodigo.getClass(), facturacodigo.getCodigo());
                productoventa.setFacturacodigo(facturacodigo);
            }
            Productos productosCodigoProducto = productoventa.getProductosCodigoProducto();
            if (productosCodigoProducto != null) {
                productosCodigoProducto = em.getReference(productosCodigoProducto.getClass(), productosCodigoProducto.getCodigoProducto());
                productoventa.setProductosCodigoProducto(productosCodigoProducto);
            }
            em.persist(productoventa);
            if (facturacodigo != null) {
                facturacodigo.getProductoventaList().add(productoventa);
                facturacodigo = em.merge(facturacodigo);
            }
            if (productosCodigoProducto != null) {
                productosCodigoProducto.getProductoventaList().add(productoventa);
                productosCodigoProducto = em.merge(productosCodigoProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productoventa productoventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productoventa persistentProductoventa = em.find(Productoventa.class, productoventa.getCodigo());
            Factura facturacodigoOld = persistentProductoventa.getFacturacodigo();
            Factura facturacodigoNew = productoventa.getFacturacodigo();
            Productos productosCodigoProductoOld = persistentProductoventa.getProductosCodigoProducto();
            Productos productosCodigoProductoNew = productoventa.getProductosCodigoProducto();
            if (facturacodigoNew != null) {
                facturacodigoNew = em.getReference(facturacodigoNew.getClass(), facturacodigoNew.getCodigo());
                productoventa.setFacturacodigo(facturacodigoNew);
            }
            if (productosCodigoProductoNew != null) {
                productosCodigoProductoNew = em.getReference(productosCodigoProductoNew.getClass(), productosCodigoProductoNew.getCodigoProducto());
                productoventa.setProductosCodigoProducto(productosCodigoProductoNew);
            }
            productoventa = em.merge(productoventa);
            if (facturacodigoOld != null && !facturacodigoOld.equals(facturacodigoNew)) {
                facturacodigoOld.getProductoventaList().remove(productoventa);
                facturacodigoOld = em.merge(facturacodigoOld);
            }
            if (facturacodigoNew != null && !facturacodigoNew.equals(facturacodigoOld)) {
                facturacodigoNew.getProductoventaList().add(productoventa);
                facturacodigoNew = em.merge(facturacodigoNew);
            }
            if (productosCodigoProductoOld != null && !productosCodigoProductoOld.equals(productosCodigoProductoNew)) {
                productosCodigoProductoOld.getProductoventaList().remove(productoventa);
                productosCodigoProductoOld = em.merge(productosCodigoProductoOld);
            }
            if (productosCodigoProductoNew != null && !productosCodigoProductoNew.equals(productosCodigoProductoOld)) {
                productosCodigoProductoNew.getProductoventaList().add(productoventa);
                productosCodigoProductoNew = em.merge(productosCodigoProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productoventa.getCodigo();
                if (findProductoventa(id) == null) {
                    throw new NonexistentEntityException("The productoventa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productoventa productoventa;
            try {
                productoventa = em.getReference(Productoventa.class, id);
                productoventa.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoventa with id " + id + " no longer exists.", enfe);
            }
            Factura facturacodigo = productoventa.getFacturacodigo();
            if (facturacodigo != null) {
                facturacodigo.getProductoventaList().remove(productoventa);
                facturacodigo = em.merge(facturacodigo);
            }
            Productos productosCodigoProducto = productoventa.getProductosCodigoProducto();
            if (productosCodigoProducto != null) {
                productosCodigoProducto.getProductoventaList().remove(productoventa);
                productosCodigoProducto = em.merge(productosCodigoProducto);
            }
            em.remove(productoventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productoventa> findProductoventaEntities() {
        return findProductoventaEntities(true, -1, -1);
    }

    public List<Productoventa> findProductoventaEntities(int maxResults, int firstResult) {
        return findProductoventaEntities(false, maxResults, firstResult);
    }

    private List<Productoventa> findProductoventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productoventa.class));
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

    public Productoventa findProductoventa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productoventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoventaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productoventa> rt = cq.from(Productoventa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
