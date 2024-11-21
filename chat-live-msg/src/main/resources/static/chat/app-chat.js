
const stompClient = new StompJs.Client({
    brokerURL: 'ws://' + window.location.host + '/buildrun-livechat-websocket'
});

stompClient.connect({}, () => {
    // Assinatura para mensagens públicas
    stompClient.subscribe('/topic/public', (message) => {
        const parsedMessage = JSON.parse(message.body);
        showMessage(parsedMessage);
    });

    // Simular carregamento de usuários online
    updateUserList(['User1', 'User2', 'User3']);
});

document.getElementById('sendButton').addEventListener('click', () => {
    const messageContent = document.getElementById('messageInput').value;
    if (messageContent.trim() !== '') {
        const message = {
            sender: "User1", // Atualize com o nome do usuário autenticado
            content: messageContent,
            profilePicture: "https://via.placeholder.com/50"
        };
        stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message));
        document.getElementById('messageInput').value = '';
    }
});

function showMessage(message) {
    const messages = document.getElementById('messages');
    const messageElement = document.createElement('li');
    messageElement.innerHTML = `
        <img src="${message.profilePicture}" alt="Profile" style="width:30px; margin-right:10px; border-radius:50%;">
        <strong>${message.sender}</strong> [${message.timestamp}]: ${message.content}
    `;
    messages.appendChild(messageElement);
    messages.scrollTop = messages.scrollHeight; // Scroll automático
}

function updateUserList(users) {
    const userList = document.getElementById('users');
    userList.innerHTML = '';
    users.forEach(user => {
        const userElement = document.createElement('li');
        userElement.textContent = user;
        userList.appendChild(userElement);
    });
}
