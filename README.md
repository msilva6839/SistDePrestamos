# SistDePrestamos
Proyecto de sistema de préstamo para la materia **Estructura de Datos**, 5to semestre.

## Descripción del Proyecto
Se requiere elaborar una **aplicación en Java orientada a objetos** para el registro de préstamos en una cooperativa.  
Los datos que se manejan para el préstamo son los siguientes:

## Datos del Préstamo

1. **Número de préstamo** (Numérico entero).
2. **Solicitante del préstamo** (Persona). Se requiere únicamente:
   - A. Número de identidad  
   - B. Primer nombre  
   - C. Primer y segundo apellido  
   - D. Teléfono de casa y móvil  
3. **Valor del préstamo** (Numérico con decimales).
4. **Fechas de pago de las cuotas** (Arreglo de máximo 6 fechas; plazo máximo 6 meses).
5. **Fecha de autorización del préstamo**.
6. **Fecha tentativa de entrega del préstamo**.

## Reglas del Proyecto

1. El número de préstamo siempre deberá ser mayor que **0**.
2. El valor del préstamo siempre deberá ser mayor que **0**.
3. Debe haber una función para capturar los datos del solicitante, únicamente los requeridos.
4. La fecha tentativa de entrega será **7 días después** de la fecha de autorización.
5. Las fechas de pago se calculan sumando **30 días** a cada una a partir de la fecha de entrega.
6. Los préstamos solo se pueden autorizar **los primeros 20 días del mes**.
7. Existe una **fecha máxima** para la autorización de los préstamos.
8. Existe un **valor máximo total a prestar**. La sumatoria de los préstamos ingresados no debe exceder este valor.
9. Debe permitir la captura de tantos préstamos como desee el usuario, mientras no se supere el valor máximo a prestar.
10. Antes de capturar el préstamo, debe preguntar si se desea capturar:
    - Datos completos del solicitante, o  
    - Solo los datos requeridos.
11. Debe imprimir los **datos completos del préstamo**, incluyendo:
    - Fecha de entrega  
    - Fechas de pago de las cuotas  

