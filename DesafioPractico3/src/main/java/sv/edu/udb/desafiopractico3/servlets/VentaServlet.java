package sv.edu.udb.desafiopractico3.servlets;

import sv.edu.udb.desafiopractico3.model.Venta;
import sv.edu.udb.desafiopractico3.model.LineaVenta;
import sv.edu.udb.desafiopractico3.dao.VentaDAO;
import sv.edu.udb.desafiopractico3.dao.LineaVentaDAO;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "VentaServlet", urlPatterns = {"/ventas"})
public class VentaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        VentaDAO ventaDao = new VentaDAO();
        LineaVentaDAO lineaDao = new LineaVentaDAO();

        try {
            if (action == null || action.isEmpty()) {
                listarVentas(request, response, ventaDao);
            } else {
                switch (action) {
                    case "nuevo":
                        System.out.println("DEBUG - Entrando en NUEVO sin ID");
                        mostrarFormulario(request, response, lineaDao);
                        break;

                    case "editar":
                        String idStr = request.getParameter("id");
                        System.out.println("DEBUG - id recibido para editar: '" + idStr + "'");
                        if (request.getParameter("id") == null) {
                            response.sendRedirect("ventas?action=nuevo");
                        } else if (idStr.trim().isEmpty()) {
                            manejarError(request, response, "ID vacío recibido para editar", null);
                        } else {
                            mostrarFormularioEdicion(request, response, ventaDao, lineaDao);
                        }
                        break;

                    case "eliminar":
                        eliminarVenta(request, response, ventaDao);
                        break;

                    default:
                        listarVentas(request, response, ventaDao);
                        break;
                }
            }
        } catch (Exception e) {
            manejarError(request, response, "Error en operación: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        VentaDAO ventaDao = new VentaDAO();
        LineaVentaDAO lineaDao = new LineaVentaDAO();

        if ("guardar".equals(action)) {
            try {
                Venta venta = mapearVentaDesdeRequest(request);

                String errorMsg = validarVentaConMensaje(venta);
                if (errorMsg != null) {
                    request.getSession().setAttribute("error", errorMsg);
                    request.getSession().setAttribute("ventaTemp", venta);
                    response.sendRedirect("ventas?action=nuevo");
                    return;
                }

                String idVentaStr = request.getParameter("idVenta");
                if (idVentaStr == null || idVentaStr.trim().isEmpty()) {
                    ventaDao.insertar(venta);
                } else {
                    venta.setIdVenta(Integer.parseInt(idVentaStr));
                    ventaDao.actualizar(venta);
                }
                response.sendRedirect("ventas");
            } catch (Exception e) {
                manejarError(request, response, "Error al guardar venta: " + e.getMessage(), e);
            }
        }
    }

    private void listarVentas(HttpServletRequest request, HttpServletResponse response, VentaDAO ventaDao)
            throws Exception {
        List<Venta> ventas = ventaDao.listar();
        request.setAttribute("ventas", ventas);
        request.getRequestDispatcher("/ventas/listar.jsp").forward(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, LineaVentaDAO lineaDao)
            throws Exception {
        
        List<LineaVenta> lineas = lineaDao.listar();
        if (lineas.isEmpty()) {
            throw new Exception("No se encontraron líneas de venta");
        }

        Venta venta = (Venta) request.getSession().getAttribute("ventaTemp");
        if (venta != null) {
            request.getSession().removeAttribute("ventaTemp");
        } else {
            venta = new Venta();
        }

        String errorMsg = (String) request.getSession().getAttribute("error");
        if (errorMsg != null) {
            request.setAttribute("error", errorMsg);
            request.getSession().removeAttribute("error");
        }

        request.setAttribute("venta", venta);
        request.setAttribute("lineas", lineas);
        request.getRequestDispatcher("/ventas/form.jsp").forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response,
                                          VentaDAO ventaDao, LineaVentaDAO lineaDao) throws Exception {
        try {
            int idVenta = Integer.parseInt(request.getParameter("id"));
            Venta venta = ventaDao.obtenerPorId(idVenta);
            if (venta == null) {
                throw new Exception("Venta no encontrada");
            }
            List<LineaVenta> lineas = lineaDao.listar();
            request.setAttribute("venta", venta);
            request.setAttribute("lineas", lineas);
            request.getRequestDispatcher("/ventas/form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            throw new Exception("ID de venta inválido");
        }
    }

    private void eliminarVenta(HttpServletRequest request, HttpServletResponse response, VentaDAO ventaDao)
            throws Exception {
        try {
            int idVenta = Integer.parseInt(request.getParameter("id"));
            ventaDao.eliminar(idVenta);
            response.sendRedirect("ventas");
        } catch (NumberFormatException e) {
            throw new Exception("ID de venta inválido");
        }
    }

    private Venta mapearVentaDesdeRequest(HttpServletRequest request) throws Exception {
        Venta venta = new Venta();

        try {
            String idLineaStr = request.getParameter("idLinea");
            if (idLineaStr == null || idLineaStr.trim().isEmpty()) {
                throw new Exception("Debe seleccionar una línea de venta.");
            }
            int idLinea = Integer.parseInt(idLineaStr);
            venta.setIdLinea(idLinea);

            String fechaStr = request.getParameter("fecha");
            if (fechaStr == null || fechaStr.trim().isEmpty()) {
                throw new Exception("La fecha es obligatoria.");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            java.util.Date utilDate;
            try {
                utilDate = sdf.parse(fechaStr);
            } catch (ParseException e) {
                throw new Exception("Formato de fecha inválido. Use yyyy-MM-dd");
            }
            venta.setFechaVenta(new Date(utilDate.getTime()));

            String descripcion = request.getParameter("descripcion");
            if (descripcion == null || descripcion.trim().isEmpty()) {
                throw new Exception("La descripción es obligatoria.");
            }
            if (descripcion.trim().length() > 50) {
                throw new Exception("La descripción debe tener máximo 50 caracteres.");
            }
            venta.setDescripcion(descripcion.trim());

        } catch (NumberFormatException e) {
            throw new Exception("Formato numérico inválido: " + e.getMessage());
        }

        return venta;
    }

    private String validarVentaConMensaje(Venta venta) {
        if (venta.getIdLinea() <= 0) {
            return "Debe seleccionar una línea de venta válida.";
        }
        if (venta.getFechaVenta() == null) {
            return "La fecha de venta es obligatoria.";
        }
        if (venta.getDescripcion() == null || venta.getDescripcion().isEmpty()) {
            return "La descripción es obligatoria.";
        }
        if (venta.getDescripcion().length() > 50) {
            return "La descripción debe tener máximo 50 caracteres.";
        }
        return null;
    }

    private void manejarError(HttpServletRequest request, HttpServletResponse response,
                              String mensaje, Exception e) throws ServletException, IOException {
        if (e != null) e.printStackTrace();
        request.setAttribute("error", mensaje);
        request.getRequestDispatcher("/ventas/error.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador para gestionar operaciones CRUD de ventas";
    }
}
