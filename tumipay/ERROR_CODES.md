# Diccionario de códigos de error

- `000`: Successful operation — La operación se ejecutó correctamente.
- `001`: Validation error — Errores de validación en los datos de entrada. El campo `message` contiene detalles.
- `002`: Internal error — Error en el servidor o en la lógica de negocio.
- `003`: Not found — El recurso solicitado no fue encontrado.
- `004`: Duplicate — Identificador de transacción del cliente ya existe.

Notas:
- Mantener este diccionario sincronizado con las respuestas de la API.
- Considerar internacionalización de los mensajes y separación entre `code` y `user_message` en futuras versiones.
