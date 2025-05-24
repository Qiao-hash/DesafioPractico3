DROP DATABASE IF EXISTS control_ventas;
CREATE DATABASE control_ventas;

USE control_ventas;

CREATE TABLE IF NOT EXISTS `lineas_de_venta` (
    `id_linea` int(4) NOT NULL AUTO_INCREMENT,
    `Linea` varchar(30) NOT NULL,
    PRIMARY KEY (`id_linea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=5;

INSERT INTO `lineas_de_venta` (`id_linea`, `Linea`) VALUES
(1, 'Linea Blanca'),
(2, 'Electronica'),
(3, 'Ferreteria'),
(4, 'Hogar');

CREATE TABLE IF NOT EXISTS `ventas` (
    `id_venta` int(4) NOT NULL AUTO_INCREMENT,
    `id_linea` int(4) NOT NULL,
    `fecha_venta` date NOT NULL,
    `descripcion` varchar(50) NOT NULL,
    PRIMARY KEY (`id_venta`),
    KEY `id_linea` (`id_linea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=9;

ALTER TABLE `ventas`
ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`id_linea`)
REFERENCES `lineas_de_venta` (`id_linea`)
ON DELETE CASCADE
ON UPDATE CASCADE;