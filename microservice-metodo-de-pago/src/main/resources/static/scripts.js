var stompClient = null;

function connect() {
  var socket = new SockJS('/ws-metodos-pago');
  stompClient = Stomp.over(socket);

  stompClient.connect({}, function (frame) {
    console.log('Conectado: ' + frame);
    stompClient.subscribe('/topic/metodosPago', function (message) {
      manejarNotificacion(JSON.parse(message.body));
    });
  });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  console.log("Desconectado");
  actualizarIdCliente(null);
}

function actualizarIdCliente(id) {
  var clientIdElement = document.getElementById("client-id").querySelector("span");
  clientIdElement.textContent = id ? `(${id})` : "(No conectado)";
}

function manejarNotificacion(mensaje) {
  var notificacionesDiv = document.getElementById("notificaciones");
  var notificacion = document.createElement("div");
  notificacion.classList.add("notification");

  if (mensaje.action === "error") {
    notificacion.innerHTML = `
            <strong style="color: red;">Error:</strong> ${mensaje.mensaje}
        `;
  } else {
    if (mensaje.data && mensaje.data.id) {
      actualizarIdCliente(mensaje.data.id);
    }
    notificacion.innerHTML = `
            <strong>Acción:</strong> ${mensaje.action}<br>
            <strong>Mensaje:</strong> ${mensaje.mensaje}<br>
            <strong>Datos:</strong> <pre>${JSON.stringify(mensaje.data, null, 2)}</pre>
        `;
  }

  notificacionesDiv.appendChild(notificacion);
}
