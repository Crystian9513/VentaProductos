/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author APRENDIZ
 */
public class MostrarReportes {
 
    private Connection conectar;

    public void MostrarReporte(String rutaReporte) {
        try {
            // Establecer la conexión a la base de datos
            conectar = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos_basedata", "root", "27478426*cP");
            System.out.println("Conexión exitosa a la base de datos");

            // Cargar el archivo de reporte desde el flujo de entrada proporcionado
            InputStream inputStream = getClass().getResourceAsStream(rutaReporte);

            if (inputStream != null) {
               
                JasperReport reporte = (JasperReport) JRLoader.loadObject(inputStream);

                // Llenar el reporte y mostrarlo en un visor
                 System.out.println("Ruta del reporte: " + rutaReporte);
                JasperPrint imprimir = net.sf.jasperreports.engine.JasperFillManager.fillReport(reporte, null, conectar);
                JasperViewer.viewReport(imprimir,false);
               
                   
            } else {
                System.out.println("No se pudo encontrar el recurso: " + rutaReporte);
            }

        } catch (SQLException | JRException ex) {
            Logger.getLogger(MostrarReportes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerrar la conexión en el bloque finally para asegurar que siempre se cierre
            try {
                if (conectar != null && !conectar.isClosed()) {
                    conectar.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MostrarReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
        public void MostrarReporteExpesificoCategoria(String rutaReporte, int cod_catego) {
        try {
            // Establecer la conexión a la base de datos
            conectar = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos_basedata", "root", "27478426*cP");
            System.out.println("Conexión exitosa a la base de datos");

            // Cargar el archivo de reporte desde el flujo de entrada proporcionado
            InputStream inputStream = getClass().getResourceAsStream(rutaReporte);

            if (inputStream != null) {
                JasperReport reporte = (JasperReport) JRLoader.loadObject(inputStream);
                Map parametros = new HashMap();
                parametros.put("cod_catego", cod_catego);
                // Llenar el reporte y mostrarlo en un visor
                JasperPrint imprimir = net.sf.jasperreports.engine.JasperFillManager.fillReport(reporte, parametros, conectar);
                JasperViewer.viewReport(imprimir,false);
                   
            } else {
                System.out.println("No se pudo encontrar el recurso: " + rutaReporte);
            }

        } catch (SQLException | JRException ex) {
            Logger.getLogger(MostrarReportes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerrar la conexión en el bloque finally para asegurar que siempre se cierre
            try {
                if (conectar != null && !conectar.isClosed()) {
                    conectar.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MostrarReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
         public void MostrarReporteUnProveedor(String rutaReporte, int cod_producto) {
        try {
            // Establecer la conexión a la base de datos
            conectar = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos_basedata", "root", "27478426*cP");
            System.out.println("Conexión exitosa a la base de datos");

            // Cargar el archivo de reporte desde el flujo de entrada proporcionado
            InputStream inputStream = getClass().getResourceAsStream(rutaReporte);

            if (inputStream != null) {
                JasperReport reporte = (JasperReport) JRLoader.loadObject(inputStream);
                Map parametros = new HashMap();
                parametros.put("cod_producto", cod_producto);
                // Llenar el reporte y mostrarlo en un visor
                JasperPrint imprimir = net.sf.jasperreports.engine.JasperFillManager.fillReport(reporte, parametros, conectar);
                JasperViewer.viewReport(imprimir,false);
                   
            } else {
                System.out.println("No se pudo encontrar el recurso: " + rutaReporte);
            }

        } catch (SQLException | JRException ex) {
            Logger.getLogger(MostrarReportes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerrar la conexión en el bloque finally para asegurar que siempre se cierre
            try {
                if (conectar != null && !conectar.isClosed()) {
                    conectar.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MostrarReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
         
          public void MostrarReporteFecha(String rutaReporte, Date fecha) {
        try {
            // Establecer la conexión a la base de datos
            conectar = DriverManager.getConnection("jdbc:mysql://localhost:3306/productos_basedata", "root", "27478426*cP");
            System.out.println("Conexión exitosa a la base de datos");

            // Cargar el archivo de reporte desde el flujo de entrada proporcionado
            InputStream inputStream = getClass().getResourceAsStream(rutaReporte);

            if (inputStream != null) {
                JasperReport reporte = (JasperReport) JRLoader.loadObject(inputStream);
                Map parametros = new HashMap();
                parametros.put("fecha", fecha);
                // Llenar el reporte y mostrarlo en un visor
                JasperPrint imprimir = net.sf.jasperreports.engine.JasperFillManager.fillReport(reporte, parametros, conectar);
                JasperViewer.viewReport(imprimir,false);
                   
            } else {
                System.out.println("No se pudo encontrar el recurso: " + rutaReporte);
            }

        } catch (SQLException | JRException ex) {
            Logger.getLogger(MostrarReportes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerrar la conexión en el bloque finally para asegurar que siempre se cierre
            try {
                if (conectar != null && !conectar.isClosed()) {
                    conectar.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MostrarReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
          
  
}