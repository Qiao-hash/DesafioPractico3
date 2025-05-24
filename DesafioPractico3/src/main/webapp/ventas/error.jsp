<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffe6e6;
            color: #b30000;
            margin: 2em;
        }
        h1 {
            color: #b30000;
        }
        .mensaje-error {
            background-color: #ffcccc;
            border: 1px solid #b30000;
            padding: 1em;
            border-radius: 5px;
            max-width: 600px;
        }
        a {
            color: #b30000;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<h1>¡Oops! Ha ocurrido un error.</h1>
<div class="mensaje-error">
    <p><strong>Detalle:</strong></p>
    <p>${error}</p>
</div>
<p><a href="ventas">Volver a la lista de ventas</a></p>
</body>
</html>
