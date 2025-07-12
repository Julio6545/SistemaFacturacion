
CREATE TABLE IF NOT EXISTS categoria(
  idcategoria serial,
  nombre varchar(30) NOT NULL,
  descripcion varchar(60) NOT NULL,
  PRIMARY KEY (idcategoria)
);
create table timbrado(
	id serial primary key,
	numero_timbrado int not null,
	fecha_desde date,
	fecha_hasta date,
	nro_facura_inicial varchar(21),
	nro_facura_final varchar(21),
	observacion varchar(100)
	
);
create table caja(
	id serial primary key,
	nro_caja int not null,
	id_sucursal int not null,
	ip varchar(15) null,	
	observacion varchar(100)
	
);
create table sucursal(
	id serial primary key,
	nro_sucursal int not null,
	nombre varchar(70) not null,
	direccion varchar(80) null,	
	telefono varchar(30) null,
	email varchar(60) null,
	cant_cajas int null,
	id_ciudad int not null,
	observacion varchar(100)
	
);
create table arqueo(
	id serial primary key,
	caja int not null,
	usuario_apertura varchar(20) not null,
	usuario_cierre varchar(20) ,
	fecha_apertura date,
	hora_apertura time not null,
	fecha_cierre date,
	hora_cierre time,
	totalcredito  int NULL,
    totalcontado  int NULL,
    total  int ULL,
    ganancia  int NULL,
	estado varchar(10),
	observacion varchar(100)	
);
--
-- Estructura de tabla para la tabla  cierre 
--
CREATE TABLE IF NOT EXISTS  cierre  (
   idcierre  serial,
   nrocierre  int NOT NULL,
   nrocaja  int NOT NULL,
   usuario  varchar(20) NOT NULL,
   fechaactividad  date NOT NULL,
   fechacierre  date NOT NULL,
   hora  time NOT NULL,
   totalcredito  int NOT NULL,
   totalcontado  int NOT NULL,
   total  int NOT NULL,
   ganancia  int NOT NULL,
  PRIMARY KEY ( idcierre )
); 
--
-- Estructura de tabla para la tabla  ciudad 
--

CREATE TABLE IF NOT EXISTS  ciudad  (
   idciudad  serial,
   nombre  varchar(30) NOT NULL,
   idpais  int NOT NULL,
  PRIMARY KEY ( idciudad )  
);
--
-- Estructura de tabla para la tabla  cliente 
--

CREATE TABLE IF NOT EXISTS  cliente  (
   idcliente  serial,
   nrocedula  varchar(20) NOT NULL,
   ruc  varchar(20) NOT NULL,
   nombre  varchar(20) NOT NULL,
   apellido  varchar(30) NOT NULL,
   telefono  varchar(30) NOT NULL,
   direccion  varchar(40) NOT NULL,
   idciudad  int NOT NULL,
   tipo_cliente int not null,
  PRIMARY KEY ( idcliente )
);
--
-- Estructura de tabla para la tabla  cobrocredito 
--

CREATE TABLE IF NOT EXISTS  cobrocredito  (
   idcobro  serial,
   nroventa  int NOT NULL,
   fecha  date NOT NULL,
   hora  time NOT NULL,
   monto  int NOT NULL,
   pagado  int NOT NULL,
   usuario  varchar NOT NULL,
  PRIMARY KEY ( idcobro )
);
--
-- Estructura de tabla para la tabla  compra 
--

CREATE TABLE IF NOT EXISTS  compra  (
   idcompra  serial,
   nrocompra  int NOT NULL,
   idempresa  varchar(20) NOT NULL,
   idvendedor  int NOT NULL,
   idusuario  varchar(15) NOT NULL,
   tiporecibo  int NOT NULL,
   fecha  date NOT NULL,
   fecha_vence date,
   hora  time NOT NULL,
   numcaja  int NOT NULL,
   modopago  int NOT NULL,
   totalC  int NOT NULL,
   cancelado  int NOT NULL,
   estado varchar(15) not null,
  PRIMARY KEY ( idcompra )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  detallecompra 
--

CREATE TABLE IF NOT EXISTS  detallecompra  (
   iddetallecompra  serial,
   idcompra  int NOT NULL,
   idproducto  varchar(20) NOT NULL,
   cantidad  int NOT NULL,
   precio  int NOT NULL,
  PRIMARY KEY ( iddetallecompra )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  detalleentrada 
--

CREATE TABLE IF NOT EXISTS  detalleentrada  (
   iddetalleentrada  serial,
   nroentrada  int NOT NULL,
   codproducto  varchar(20) NOT NULL,
   cantidad  int NOT NULL,
   precio  int NOT NULL,
  PRIMARY KEY ( iddetalleentrada )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  detallefactura 
--

CREATE TABLE IF NOT EXISTS  detallefactura  (
   iddetalle  serial,
   nrofactura  int NOT NULL,
   idproducto  varchar(20) NOT NULL,
   nrolote  varchar(15) NULL,
   cantidad  int NOT NULL,
   precio  int NOT NULL,
  PRIMARY KEY ( iddetalle )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  detallesalida 
--

CREATE TABLE IF NOT EXISTS  detallesalida  (
   iddetallesalida  serial,
   nrosalida  int NOT NULL,
   codproducto  varchar(20) NOT NULL,
   cantidad  int NOT NULL,
   precio  int NOT NULL,
  PRIMARY KEY ( iddetallesalida )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  detalleticket 
--

CREATE TABLE IF NOT EXISTS  detalleticket  (
   iddetalle  serial,
   idticket  int NOT NULL,
   idproducto  varchar(20) NOT NULL,
   nrolote  varchar(12) NOT NULL,
   cantidad  int NOT NULL,
   precio  int NOT NULL,
  PRIMARY KEY ( iddetalle )
) ;


--
-- Estructura de tabla para la tabla  detalleventa 
--

CREATE TABLE IF NOT EXISTS  detalleventa  (
   iddetalle  serial,
   nroventa  int NOT NULL,
   idproducto  varchar(20) NOT NULL,
   nrolote  varchar(15) NOT NULL,
   cantidad  int NOT NULL,
   precio  int NOT NULL,
   iva 5 int null,
   iva 10 int null,
   excenta int null,
   limite int null
  PRIMARY KEY ( iddetalle )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  empresa 
--

CREATE TABLE IF NOT EXISTS  empresa  (
   idempresa  serial,
   rucempresa  varchar(20) NOT NULL,
   nombre  varchar(30) NOT NULL,
   direccion  varchar(30) NOT NULL,
   telefono  varchar(30) NOT NULL,
   correo  varchar(30) NOT NULL,
  PRIMARY KEY ( idempresa )
);


-------------------------------------------
--
-- Estructura de tabla para la tabla  entrada 
--

CREATE TABLE IF NOT EXISTS  entrada  (
   identrada  serial,
   nroentrada  int NOT NULL,
   identidad  int NOT NULL,
   tipoentrada  int NOT NULL,
   idusuario  int NOT NULL,
   fecha  date NOT NULL,
   hora  time NOT NULL,
   totalentrada  int NOT NULL,
  PRIMARY KEY ( identrada )
  ) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  factura 
--

CREATE TABLE IF NOT EXISTS  factura  (
   idfactura  serial,
   nrofactura  int NOT NULL,
   id_timbrado int not null,
   idcliente  int NOT NULL,
   idusuario  varchar(15) NOT NULL,
   idvendedor  varchar(20) NOT NULL,
   fecha  date NOT NULL,
   fecha_vence date,
   hora  time NOT NULL,
   numcaja  int NOT NULL,
   modopago  int NOT NULL,
   totalfactura  int NOT NULL,
   ganancia  int NOT NULL,
   cancelado  int NOT NULL,
   estado varchar(15) not null,
   numero_presupuesto int
  PRIMARY KEY ( idfactura )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  lote 
--

CREATE TABLE IF NOT EXISTS  lote  (
   idlote  serial,
   nrolote  varchar(15) NOT NULL,
   idproducto  varchar(20) NOT NULL,
   cantidad  int NOT NULL,
   fechavencimiento  date NOT NULL,
  PRIMARY KEY ( idlote )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  marca 
--

CREATE TABLE IF NOT EXISTS  marca  (
   idmarca  serial,
   nombre  varchar(35) NOT NULL,
   origen  varchar(30) NOT NULL,
  PRIMARY KEY ( idmarca )
);


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  pagocredito 
--

CREATE TABLE IF NOT EXISTS  pagocredito  (
   idpago  serial,
   nrocompra  int NOT NULL,
   nrorecibopago  int NOT NULL,
   cicobrador  int NOT NULL,
   fecha  date NOT NULL,
   hora  time NOT NULL,
   monto  int NOT NULL,
   pagado  int NOT NULL,
   usuario  varchar(12) NOT NULL,
  PRIMARY KEY ( idpago )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  pais 
--

CREATE TABLE IF NOT EXISTS  pais  (
   idpais  serial,
   nombre  varchar(30) NOT NULL,
  PRIMARY KEY ( idpais )
) ;

--
-- Volcado de datos para la tabla  pais 
--

INSERT INTO  pais  ( idpais ,  nombre ) VALUES
(1, 'Paraguay'),
(2, 'Argentina'),
(3, 'Brasil'),
(4, 'Bolivia'),
(5, 'Uruguay');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  producto 
--

CREATE TABLE IF NOT EXISTS  producto  (
   idproducto  serial,
   codigo  varchar(20) NOT NULL,
   descripcion  varchar(40) NOT NULL,
   idcategoria  int NOT NULL,
   idmarca  int NOT NULL,
   existencia  int NOT NULL,
   stock_minimo int not null,
   costo  int NOT NULL,
   incremento  int NOT NULL,
   iva  int NOT NULL,
   precio  int NOT NULL,
   depositoestante  varchar(5) NOT NULL,
  PRIMARY KEY ( idproducto )
) ;



-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  proveedor 
--

CREATE TABLE IF NOT EXISTS  proveedor  (
   idvendedor  serial,
   nrocedula  int NOT NULL,
   nombre  varchar(20) NOT NULL,
   apellido  varchar(30) NOT NULL,
   telefono  varchar(30) NOT NULL,
   correo  varchar(40) NOT NULL,
   direccion  varchar(40) NOT NULL,
   idempresa  int NOT NULL,
  PRIMARY KEY ( idvendedor )
)  ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  roll 
--

CREATE TABLE IF NOT EXISTS  roll  (
   idroll  serial,
   nombre  text NOT NULL,
   descripcion  text NOT NULL,
  PRIMARY KEY ( idroll )
) ;

--
-- Volcado de datos para la tabla  roll 
--

INSERT INTO  roll  ( idroll ,  nombre ,  descripcion ) VALUES
(1, 'administrador', 'responsable de la administracion del sistema'),
(2, 'Usuario del sistema', 'responsable de las operaciones de compras y ventas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  salida 
--

CREATE TABLE IF NOT EXISTS  salida  (
   idsalida  serial,
   nrosalida  int NOT NULL,
   identidad  int NOT NULL,
   tiposalida  int NOT NULL,
   idusuario  varchar(20) NOT NULL,
   fecha  date NOT NULL,
   hora  time NOT NULL,
   totalsalida  int NOT NULL,
  PRIMARY KEY ( idsalida )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  ticket 
--

CREATE TABLE IF NOT EXISTS  ticket  (
   nroticket  serial,
   idcliente  varchar(20) NOT NULL,
   idusuario  varchar(15) NOT NULL,
   fecha  date NOT NULL,
   hora  time NOT NULL,
   numcaja  int NOT NULL,
   modopago  int NOT NULL,
   totalticket  int NOT NULL,
   ganancia  int NOT NULL,
   cancelado  int NOT NULL,
   procesado  int NOT NULL,
   estado varchar(15) not null,
  PRIMARY KEY ( nroticket )
) ;

------------------------------

--
-- Estructura de tabla para la tabla  tiposalida 
--

CREATE TABLE IF NOT EXISTS  tiposalida  (
   idtiposalida  serial,
   nombre  varchar(20) NOT NULL,
   descripcion  varchar(40) NOT NULL,
  PRIMARY KEY ( idtiposalida )
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  usuario 
--

CREATE TABLE IF NOT EXISTS  usuario  (
   idusuario  serial,
   nrocedula  int NOT NULL,
   nombre  varchar(20) NOT NULL,
   apellido  varchar(30) NOT NULL,
   fechaNacimiento  date NOT NULL,
   telefono  varchar(30) NOT NULL,
   direccion  varchar(30) NOT NULL,
   email  varchar(40) NOT NULL,
   usuario  varchar(15) NOT NULL,
   clave  varchar(15) NOT NULL,
   roll  int NOT NULL,
  PRIMARY KEY ( idusuario )
  ) ;

--
-- Volcado de datos para la tabla  usuario 
--

INSERT INTO  usuario  ( idusuario ,  nrocedula ,  nombre ,  apellido ,  fechaNacimiento ,  telefono ,  direccion ,  email ,  usuario ,  clave ,  roll ) VALUES
(1, 4333255, 'Antoliano', 'Caceres Estigarribia', '1986-02-06', 984219397, 'Ruta 1 km 35 Aveiro', 'ingantolianoce@gmail.com', 'ace', '333', 1);


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla  venta 
--

CREATE TABLE IF NOT EXISTS  venta  (
   idventas  serial,
   nroventa  int NOT NULL,
   idcliente  int NOT NULL,
   idusuario  varchar(15) NOT NULL,
   idvendedor  varchar(20) NOT NULL,
   fecha  date NOT NULL,
   fecha_vence date,
   hora  time NOT NULL,
   numcaja  int NOT NULL,
   modopago  int NOT NULL,
   totalventa  int NOT NULL,
   ganancia  int NOT NULL,
   cancelado  int NOT NULL,
   estado varchar(15) not null,
   numero_presupuesto int null,
  PRIMARY KEY ( idventas )
) ;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla  cierre 
--
ALTER TABLE  cierre 
  ADD CONSTRAINT  cierre_ibfk_1  FOREIGN KEY ( usuario ) REFERENCES  usuario  ( usuario );

--
-- Filtros para la tabla  ciudad 
--
ALTER TABLE  ciudad 
  ADD CONSTRAINT  ciudad_ibfk_1  FOREIGN KEY ( idpais ) REFERENCES  pais  ( idpais );

--
-- Filtros para la tabla  cliente 
--
ALTER TABLE  cliente 
  ADD CONSTRAINT  cliente_ibfk_1  FOREIGN KEY ( idciudad ) REFERENCES  ciudad  ( idciudad );

--
-- Filtros para la tabla  cobrocredito 
--
ALTER TABLE  cobrocredito 
  ADD CONSTRAINT  cobrocredito_ibfk_2  FOREIGN KEY ( usuario ) REFERENCES  usuario  ( usuario ),
  ADD CONSTRAINT  cobrocredito_ibfk_3  FOREIGN KEY ( nroventa ) REFERENCES  ticket  ( nroticket );

--
-- Filtros para la tabla  compra 
--
ALTER TABLE  compra 
  ADD CONSTRAINT  compra_ibfk_1  FOREIGN KEY ( idempresa ) REFERENCES  empresa  ( rucempresa ),
  ADD CONSTRAINT  compra_ibfk_2  FOREIGN KEY ( idvendedor ) REFERENCES  proveedor  ( nrocedula ),
  ADD CONSTRAINT  compra_ibfk_3  FOREIGN KEY ( idusuario ) REFERENCES  usuario  ( usuario );

--
-- Filtros para la tabla  detallecompra 
--
ALTER TABLE  detallecompra 
  ADD CONSTRAINT  detallecompra_ibfk_1  FOREIGN KEY ( idproducto ) REFERENCES  producto  ( codigo ),
  ADD CONSTRAINT  detallecompra_ibfk_2  FOREIGN KEY ( idcompra ) REFERENCES  compra  ( nrocompra );

--
-- Filtros para la tabla  detallefactura 
--
ALTER TABLE  detallefactura 
  ADD CONSTRAINT  detallefactura_ibfk_1  FOREIGN KEY ( nrofactura ) REFERENCES  factura  ( nrofactura ),
  ADD CONSTRAINT  detallefactura_ibfk_2  FOREIGN KEY ( nrolote ) REFERENCES  lote  ( nrolote ),
  ADD CONSTRAINT  detallefactura_ibfk_3  FOREIGN KEY ( idproducto ) REFERENCES  producto  ( codigo );

--
-- Filtros para la tabla  detallesalida 
--
ALTER TABLE  detallesalida 
  ADD CONSTRAINT  detallesalida_ibfk_1  FOREIGN KEY ( nrosalida ) REFERENCES  salida  ( nrosalida ),
  ADD CONSTRAINT  detallesalida_ibfk_2  FOREIGN KEY ( codproducto ) REFERENCES  producto  ( codigo );

--
-- Filtros para la tabla  detalleticket 
--
ALTER TABLE  detalleticket 
  ADD CONSTRAINT  detalleticket_ibfk_1  FOREIGN KEY ( idticket ) REFERENCES  ticket  ( nroticket ),
  ADD CONSTRAINT  detalleticket_ibfk_2  FOREIGN KEY ( idproducto ) REFERENCES  producto  ( codigo );

--
-- Filtros para la tabla  detalleventa 
--
ALTER TABLE  detalleventa 
  ADD CONSTRAINT  detalleventa_ibfk_2  FOREIGN KEY ( idproducto ) REFERENCES  producto  ( codigo ),
  ADD CONSTRAINT  detalleventa_ibfk_3  FOREIGN KEY ( nrolote ) REFERENCES  lote  ( nrolote ),
  ADD CONSTRAINT  detalleventa_ibfk_4  FOREIGN KEY ( nroventa ) REFERENCES  venta  ( nroventa );

--
-- Filtros para la tabla  lote 
--
ALTER TABLE  lote 
  ADD CONSTRAINT  lote_ibfk_1  FOREIGN KEY ( idproducto ) REFERENCES  producto  ( codigo );

--
-- Filtros para la tabla  producto 
--
ALTER TABLE  producto 
  ADD CONSTRAINT  producto_ibfk_1  FOREIGN KEY ( idcategoria ) REFERENCES  categoria  ( idcategoria ),
  ADD CONSTRAINT  producto_ibfk_2  FOREIGN KEY ( idmarca ) REFERENCES  marca  ( idmarca );

--
-- Filtros para la tabla  proveedor 
--
ALTER TABLE  proveedor 
  ADD CONSTRAINT  proveedor_ibfk_1  FOREIGN KEY ( idempresa ) REFERENCES  empresa  ( idempresa );

--
-- Filtros para la tabla  salida 
--
ALTER TABLE  salida 
  ADD CONSTRAINT  salida_ibfk_1  FOREIGN KEY ( identidad ) REFERENCES  cliente  ( nrocedula ),
  ADD CONSTRAINT  salida_ibfk_2  FOREIGN KEY ( idusuario ) REFERENCES  usuario  ( usuario ),
  ADD CONSTRAINT  salida_ibfk_3  FOREIGN KEY ( tiposalida ) REFERENCES  tiposalida  ( idtiposalida );

--
-- Filtros para la tabla  usuario 
--
ALTER TABLE  usuario 
  ADD CONSTRAINT  usuario_ibfk_1  FOREIGN KEY ( roll ) REFERENCES  roll  ( idroll );

--
-- Filtros para la tabla  venta 
--
ALTER TABLE  venta 
  ADD CONSTRAINT  venta_ibfk_3  FOREIGN KEY ( idcliente ) REFERENCES  cliente  ( nrocedula ),
  ADD CONSTRAINT  venta_ibfk_4  FOREIGN KEY ( idusuario ) REFERENCES  usuario  ( usuario );

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
