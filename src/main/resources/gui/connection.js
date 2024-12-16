document.addEventListener("DOMContentLoaded", () => {
    const connectButton = document.getElementById("connectButton");
    const statusText = document.getElementById("server-status");
    const logs = document.getElementById("logs");

    function log(message) {
        logs.value += message + "\n";
        logs.scrollTop = logs.scrollHeight;
    }

    connectButton.addEventListener("click", () => {
        const serverAddress = document.getElementById("serverAddress").value;
        const serverPort = document.getElementById("serverPort").value;
        const nickName = document.getElementById("nickName").value;

        if (window.controller) {
            let connected = window.controller.connect(serverAddress, serverPort, nickName);
            statusText.textContent = "Connecting to server...";
            statusText.style.color = "blue";
            if(!connected){
                statusText.textContent = "connection failed";
                statusText.style.color = "red";
            }
        } else {
            log("ClientController not available!");
        }
    });

});
