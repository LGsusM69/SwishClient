document.addEventListener("DOMContentLoaded", () => {
    const sendButton = document.getElementById("sendButton");
    const disconnectButton = document.getElementById("disconnectButton");
    const chatLog = document.getElementById("chat-log");
    const chatMessage = document.getElementById("chat-message");

    function log(message) {
        console.log("grandmasterflash");
        chatLog.value += message + "\n";
        chatLog.scrollTop = chatLog.scrollHeight;
    }

    sendButton.addEventListener("click", () => {
        const message = chatMessage.value.trim();
        if (message && window.controller) {
            console.log(`Sending message: ${message}`);
            window.controller.sendMessage(message);
            //log(`You: ${message}`);
            chatMessage.value = "";
        }
    });

    disconnectButton.addEventListener("click", () => {
        if (window.controller) {
            console.log("Disconnecting...");
            window.controller.disconnect();
            // Optionally navigate back to connection.html
        }
    });
});
