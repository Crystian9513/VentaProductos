/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Clientes;
import Modelo.Factura;
import Modelo.Usuarios;
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
public class FacturaJpaController implements Serializable {

    public FacturaJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("ProductosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        if (factura.getProductoventaList() == null) {
            factura.setProductoventaList(new ArrayList<Productoventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientesCedula = factura.getClientesCedula();
            if (clientesCedula != null) {
                clientesCedula = em.getReference(clientesCedula.getClass(), clientesCedula.getCedula());
                factura.setClientesCedula(clientesCedula);
            }
            Usuarios usuariosCedula = factura.getUsuariosCedula();
            if (usuariosCedula != null) {
                usuariosCedula = em.getReference(usuariosCedula.getClass(), usuariosCedula.getCedula());
                factura.setUsuariosCedula(usuariosCedula);
            }
            List<Productoventa> attachedProductoventaList = new ArrayList<Productoventa>();
            for (Productoventa productoventaListProductoventaToAttach : factura.getProductoventaList()) {
                productoventaListProductoventaToAttach = em.getReference(productoventaListProductoventaToAttach.getClass(), productoventaListProductoventaToAttach.getCodigo());
                attachedProductoventaList.add(productoventaListProductoventaToAttach);
            }
            factura.setProductoventaList(attachedProductoventaList);
            em.persist(factura);
            if (clientesCedula != null) {
                clientesCedula.getFacturaList().add(factura);
                clientesCedula = em.merge(clientesCedula);
            }
            if (usuariosCedula != null) {
                usuariosCedula.getFacturaList().add(factura);
                usuariosCedula = em.merge(usuariosCedula);
            }
            for (Productoventa productoventaListProductoventa : factura.getProductoventaList()) {
                Factura oldFacturacodigoOfProductoventaListProductoventa = productoventaListProductoventa.getFacturacodigo();
                productoventaListProductoventa.setFacturacodigo(factura);
                productoventaListProductoventa = em.merge(productoventaListProductoventa);
                if (oldFacturacodigoOfProductoventaListProductoventa != null) {
                    oldFacturacodigoOfProductoventaListProductoventa.getProductoventaList().remove(productoventaListProductoventa);
                    oldFacturacodigoOfProductoventaListProductoventa = em.merge(oldFacturacodigoOfProductoventaListProductoventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getCodigo());
            Clientes clientesCedulaOld = persistentFactura.getClientesCedula();
            Clientes clientesCedulaNew = factura.getClientesCedula();
            Usuarios usuariosCedulaOld = persistentFactura.getUsuariosCedula();
            Usuarios usuariosCedulaNew = factura.getUsuariosCedula();
            List<Productoventa> productoventaListOld = persistentFactura.getProductoventaList();
            List<Productoventa> productoventaListNew = factura.getProductoventaList();
            List<String> illegalOrphanMessages = null;
            for (Productoventa productoventaListOldProductoventa : productoventaListOld) {
                if (!productoventaListNew.contains(productoventaListOldProductoventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Productoventa " + productoventaListOldProductoventa + " since its facturacodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientesCedulaNew != null) {
                clientesCedulaNew = em.getReference(clientesCedulaNew.getClass(), clientesCedulaNew.getCedula());
                factura.setClientesCedula(clientesCedulaNew);
            }
            if (usuariosCedulaNew != null) {
                usuariosCedulaNew = em.getReference(usuariosCedulaNew.getClass(), usuariosCedulaNew.getCedula());
                factura.setUsuariosCedula(usuariosCedulaNew);
            }
            List<Productoventa> attachedProductoventaListNew = new ArrayList<Productoventa>();
            for (Productoventa productoventaListNewProductoventaToAttach : productoventaListNew) {
                productoventaListNewProductoventaToAttach = em.getReference(productoventaListNewProductoventaToAttach.getClass(), productoventaListNewProductoventaToAttach.getCodigo());
                attachedProductoventaListNew.add(productoventaListNewProductoventaToAttach);
            }
            productoventaListNew = attachedProductoventaListNew;
            factura.setProductoventaList(productoventaListNew);
            factura = em.merge(factura);
            if (clientesCedulaOld != null && !clientesCedulaOld.equals(clientesCedulaNew)) {
                clientesCedulaOld.getFacturaList().remove(factura);
                clientesCedulaOld = em.merge(clientesCedulaOld);
            }
            if (clientesCedulaNew != null && !clientesCedulaNew.equals(clientesCedulaOld)) {
                clientesCedulaNew.getFacturaList().add(factura);
                clientesCedulaNew = em.merge(clientesCedulaNew);
            }
            if (usuariosCedulaOld != null && !usuariosCedulaOld.equals(usuariosCedulaNew)) {
                usuariosCedulaOld.getFacturaList().remove(factura);
                usuariosCedulaOld = em.merge(usuariosCedulaOld);
            }
            if (usuariosCedulaNew != null && !usuariosCedulaNew.equals(usuariosCedulaOld)) {
                usuariosCedulaNew.getFacturaList().add(factura);
                usuariosCedulaNew = em.merge(usuariosCedulaNew);
            }
            for (Productoventa productoventaListNewProductoventa : productoventaListNew) {
                if (!productoventaListOld.contains(productoventaListNewProductoventa)) {
                    Factura oldFacturacodigoOfProductoventaListNewProductoventa = productoventaListNewProductoventa.getFacturacodigo();
                    productoventaListNewProductoventa.setFacturacodigo(factura);
                    productoventaListNewProductoventa = em.merge(productoventaListNewProductoventa);
                    if (oldFacturacodigoOfProductoventaListNewProductoventa != null && !oldFacturacodigoOfProductoventaListNewProductoventa.equals(factura)) {
                        oldFacturacodigoOfProductoventaListNewProductoventa.getProductoventaList().remove(productoventaListNewProductoventa);
                        oldFacturacodigoOfProductoventaListNewProductoventa = em.merge(oldFacturacodigoOfProductoventaListNewProductoventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factura.getCodigo();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Productoventa> productoventaListOrphanCheck = factura.getProductoventaList();
            for (Productoventa productoventaListOrphanCheckProductoventa : productoventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the Productoventa " + productoventaListOrphanCheckProductoventa + " in its productoventaList field has a non-nullable facturacodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes clientesCedula = factura.getClientesCedula();
            if (clientesCedula != null) {
                clientesCedula.getFacturaList().remove(factura);
                clientesCedula = em.merge(clientesCedula);
            }
            Usuarios usuariosCedula = factura.getUsuariosCedula();
            if (usuariosCedula != null) {
                usuariosCedula.getFacturaList().remove(factura);
                usuariosCedula = em.merge(usuariosCedula);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
