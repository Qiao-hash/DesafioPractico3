<%@ page import="sv.edu.udb.desafiopractico3.model.LineaVenta" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="sv.edu.udb.desafiopractico3.model.Venta" %>


<%
    // Obtener el objeto venta del request
    Venta venta = (Venta) request.getAttribute("venta");
    if (venta == null) {
        venta = new Venta();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${venta.idVenta == null || venta.idVenta == 0 ? 'Nueva' : 'Editar'} Venta</title>    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .form-container {
            max-width: 600px;
            margin: 2rem auto;
            padding: 2rem;
            background: white;
            border-radius: 15px;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.1);
        }
        .form-header {
            color: #2c3e50;
            border-bottom: 3px solid #3498db;
            padding-bottom: 0.5rem;
            margin-bottom: 1.5rem;
        }
        .required-label:after {
            content: "*";
            color: #e74c3c;
            margin-left: 3px;
        }
    </style>
</head>
<body class="bg-light">
<p>Debug ID: ${venta.idVenta}</p>

<div class="container">
    <div class="form-container">
        <h3 class="form-header">
            <i class="fas fa-${venta.idVenta == null || venta.idVenta == 0 ? 'plus' : 'edit'}"></i>
            ${venta.idVenta == null || venta.idVenta == 0 ? 'Nueva' : 'Editar'} Venta
        </h3>

        <form action="ventas" method="post">
            <input type="hidden" name="action" value="guardar">
            <input type="hidden" name="idVenta" value="${venta.idVenta}">

            <div class="mb-3">
                <label class="form-label required-label">Línea de Venta</label>
                <select name="idLinea" class="form-select" required>
                    <c:forEach var="linea" items="${lineas}">
                        <option value="${linea.idLinea}"
                                <c:if test="${linea.idLinea == venta.idLinea}">selected</c:if>>
                                ${linea.linea}
                        </option>
                    </c:forEach>
                </select>


            </div>

            <div class="mb-3">
                <label class="form-label required-label">Fecha</label>
                <input type="date" name="fecha"
                       value="<fmt:formatDate value="${venta.fechaVenta}" pattern="yyyy-MM-dd" />"
                       class="form-control"
                       required>
            </div>

            <div class="mb-4">
                <label class="form-label required-label">Descripción</label>
                <textarea name="descripcion"
                          class="form-control"
                          rows="3"
                          maxlength="50"
                          required>${venta.descripcion}</textarea>
                <div class="form-text">Máximo 50 caracteres</div>
            </div>

            <div class="d-flex justify-content-end gap-2">
                <a href="ventas" class="btn btn-secondary">
                    <i class="fas fa-times"></i> Cancelar
                </a>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Guardar
                </button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>