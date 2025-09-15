-- Libro
INSERT IGNORE INTO producto (nombre, descripcion, precio, stock, tipo, autor, editorial, estado)
VALUES ('Java Avanzado', 'Libro técnico', 29.99, 10, 'LIBRO', 'Patricio', 'TechBooks', 'nuevo');

-- Café
INSERT IGNORE INTO producto (nombre, descripcion, precio, stock, tipo, origen, presentacion, peso_gramos)
VALUES ('Café Colombia', 'Aroma intenso', 12.50, 20, 'CAFE', 'Colombia', 'molido', 250);

-- Separador
INSERT IGNORE INTO producto (nombre, descripcion, precio, stock, tipo, material, dimensiones_cm)
VALUES ('Separador Vintage', 'Diseño clásico', 3.99, 50, 'SEPARADOR', 'cartón', 15);

-- Soporte
INSERT IGNORE INTO producto (nombre, descripcion, precio, stock, tipo, material, peso)
VALUES ('Soporte de Madera', 'Ideal para libros', 18.00, 5, 'SOPORTE', 'madera', 2.5);