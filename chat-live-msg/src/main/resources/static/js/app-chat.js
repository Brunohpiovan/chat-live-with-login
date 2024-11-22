// Conectar ao WebSocket
const stompClient = new StompJs.Client({
    brokerURL: 'ws://' + window.location.host + '/buildrun-livechat-websocket'
});

stompClient.connect({}, () => {
    // Assinatura para mensagens públicas
    stompClient.subscribe('/topic/public', (message) => {
        const parsedMessage = JSON.parse(message.body);
        showMessage(parsedMessage);
    });

    // Atualizar lista de usuários online periodicamente
    setInterval(updateUserList, 5000);
});

// Enviar mensagem
document.getElementById('sendButton').addEventListener('click', () => {
    const messageContent = document.getElementById('messageInput').value;
    if (messageContent.trim() !== '') {
        const message = {
            sender: "User1", // Atualize com o nome do usuário autenticado
            content: messageContent,
            profilePicture: "https://via.placeholder.com/50",
            timestamp: new Date().toISOString() // Adicionar timestamp na mensagem
        };
        stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message));
        document.getElementById('messageInput').value = '';
    }
});

// Mostrar mensagem
function showMessage(message) {
    const messages = document.getElementById('messages');
    const messageElement = document.createElement('li');
    messageElement.innerHTML = `
        <img src="${message.profilePicture}" alt="Profile" style="width:30px; margin-right:10px; border-radius:50%;">
        <strong>${message.sender}</strong> [${new Date(message.timestamp).toLocaleTimeString()}]: ${message.content}
    `;
    messages.appendChild(messageElement);
    messages.scrollTop = messages.scrollHeight; // Scroll automático
}

// Atualizar lista de usuários online
function updateUserList() {
    fetch('/api/online-users')
        .then(response => response.json())
        .then(users => {
            const userList = document.getElementById('users');
            userList.innerHTML = '';
            users.forEach(user => {
                const userElement = document.createElement('li');
                userElement.textContent = user.username; // Presume que a resposta seja uma lista de objetos com o campo 'username'
                userList.appendChild(userElement);
            });
        })
        .catch(error => console.error('Error fetching online users:', error));
}

// Carregar mensagens anteriores
function loadPreviousMessages() {
    fetch('/api/messages')
        .then(response => response.json())
        .then(messages => {
            messages.forEach(message => {
                showMessage(message);
            });
        })
        .catch(error => console.error('Error fetching messages:', error));
}

document.addEventListener('DOMContentLoaded', (event) => {
    updateUserList();
    loadPreviousMessages();
});
