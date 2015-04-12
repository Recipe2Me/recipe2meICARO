var ws = null;
    function connect() {
        var URL = 'ws://localhost:8080/events/';
        if ('WebSocket' in window) {
            ws = new WebSocket(URL);
        } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket(URL);
        } else {
            alert('Tu navegador no soporta WebSockets');
            return;
        }
        ws.onopen = function () {
            addMessage('Concectado!');
        };
        ws.onmessage = function (event) {
            var message = event.data;
            addMessage(message);
        };
        ws.onclose = function () {
            addMessage('Desconectado!');
        };
        ws.onerror = function (event) {
            addMessage('Se produjo un error!');
        };
    }
    function disconnect() {
        if (ws != null) {
            ws.close();
            ws = null;
        }
    }
    function sendMessage(message) {
        if (ws != null) {
            ws.send(message);
        }
    }
    function addMessage(message) {
        var messages = document.getElementById('messages').value;
        messages += (message + '\n');
        document.getElementById('messages').value = messages;
    }