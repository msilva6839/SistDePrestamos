# SistDePrestamos
Proyecto de sistema de prestamo para la materia Estructura de datos, 5to semestre, UNIMAR



	Se requiere que elbore una aplicacion en java orientada a objetos para el registro de prestamos en una cooperativa. Los datos que se manejan para el prestamo son los siguientes:

1.- Numero de prestamo(Numerico entero).
2.- Solicitante del prestamo(Persona). Se requiere uncamente: 
	A.- Nro. de identidad.
	B.- Primer Nombre.
	C.- Primer y segundo apellido.
	D.- Telefono de casa y movil.
3.- Valor del prestamo(Numerico con decimales).
4.- Fecha de pago de las cuotas(arreglo de un maximo de 6 fechas, se asume que el plazo maximo de pago son 6 meses).
5.- Fecha de autorizacion del prestamo.
6.- Fecha tentativa de entrega del prestamo.

	Las reglas que debe respetar este proyectos on las siguientes:

1.- El numero de prestamo siempre debera ser un valor mayor que cero(0).
2.- El valor del prestamo siempre debera ser mayor a cero(0).
3.- Debe haber una funcion de captura de los datos del solicitante, debe capturar unicamente los datos requeridos.
4.- La fecha tentativa de entrega del prestamo sera siete(7) dias despues de la fecha de autorizacion del prestamo.
5.- Las fechas de pago del prestamo se calculan, sumando 30 dias a cada una a partir de la fecha de entrega del prestamo.
6.- Los prestamos solo se pueden autorizar los primeros 20 dias del mes. Esta es una politca que nunca va a cambiar.
7.- Estite una fecha maxima para la autorizacion de los prestamos.
8.- Existe un gran valor maximo a prestar. La sumatoria de los prestamos que se ingresen no debe exceder este valor.
9.- Debe premitir la captura de tantos prestamos como desee ingresar el usuario, a menos que se haya llegado al valor maximo a prestar.
10.- Antes de capturar el prestamo debe preguntar si se desea capturar los datos completos del solicitante o unicamente los datos requeridos por el prestamo.
11.- Debe imprimir los datos completos del prestamoi incluyendo la fecha de entrega y los fechas de pago de las cuotas.
