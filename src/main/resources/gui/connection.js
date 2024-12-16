document.addEventListener("DOMContentLoaded", () => {
    const connectButton = document.getElementById("connectButton");
    const disconnectButton = document.getElementById("disconnectButton");
    const statusText = document.getElementById("server-status");
    const logs = document.getElementById("logs");

    function log(message) {
        logs.value += message + "\n";
        logs.scrollTop = logs.scrollHeight;
    }

    connectButton.addEventListener("click", () => {
        const serverAddress = document.getElementById("serverAddress").value;
        const serverPort = document.getElementById("serverPort").value;

        if (window.controller) {
            let connected = window.controller.connect(serverAddress, serverPort);
            statusText.textContent = "Connecting to server...";
            statusText.style.color = "blue";

            connectButton.disabled = true;
            disconnectButton.disabled = false;
        } else {
            log("ClientController not available!");
        }
    });

    disconnectButton.addEventListener("click", () => {
        if (window.controller) {
            log("Disconnecting from server...");
            window.controller.disconnect();
            statusText.textContent = "Server is not running";
            statusText.style.color = "red";

            connectButton.disabled = false;
            disconnectButton.disabled = true;
        } else {
            log("ClientController not available!");
        }
    });
});
