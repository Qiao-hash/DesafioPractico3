<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Ventas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .header-bg {
            background: linear-gradient(45deg, #2c3e50, #3498db);
            color: white;
        }
        .table-hover tbody tr:hover {
            background-color: rgba(52, 152, 219, 0.1);
        }
        .action-buttons .btn {
            margin: 0 3px;
            transition: transform 0.2s;
        }
        .action-buttons .btn:hover {
            transform: scale(1.1);
        }
        .card-shadow {
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card card-shadow">
        <div class="card-header header-bg">
            <h3 class="mb-0"><i class="fas fa-chart-line"></i> Gestión de Ventas</h3>
        </div>

        <div class="card-body">
            <a href="${pageContext.request.contextPath}/ventas?action=nuevo" class="btn btn-success mb-4">
                <i class="fas fa-plus-circle"></i> Nueva Venta
            </a>


            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Línea</th>
                        <th>Fecha</th>
                        <th>Descripción</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ventas}" var="v">
                        <tr>
                            <td class="fw-bold">#${v.idVenta}</td>
                            <td>
                                        <span class="badge bg-primary">
                                                ${v.idLinea == 1 ? 'Blanca' :
                                                        v.idLinea == 2 ? 'Electrónica' :
                                                                v.idLinea == 3 ? 'Ferretería' : 'Hogar'}
                                        </span>
                            </td>
                            <td>
                                <fmt:formatDate value="${v.fechaVenta}" pattern="dd/MM/yyyy" />
                            </td>
                            <td>${v.descripcion}</td>
                            <td class="action-buttons">
                                <a href="ventas?action=editar&id=${v.idVenta}"
                                   class="btn btn-sm btn-warning"
                                   title="Editar">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <a href="ventas?action=eliminar&id=${v.idVenta}"
                                   class="btn btn-sm btn-danger"
                                   title="Eliminar"
                                   onclick="return confirm('¿Seguro que deseas eliminar esta venta?');">
                                    <i class="fas fa-trash-alt"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>