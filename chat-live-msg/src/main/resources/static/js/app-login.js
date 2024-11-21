const loginForm = document.getElementById('loginForm');

// Evento de envio do formulário
loginForm.addEventListener('submit', async (event) => {
    event.preventDefault(); // Impede o comportamento padrão do formulário

    // Captura os valores dos campos do formulário
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        // Faz a requisição POST para o endpoint de login
        const response = await fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Define o conteúdo como JSON
            },
            body: JSON.stringify({ email, password }) // Converte os dados para JSON
        });

        if (response.ok) {
            const data = await response.json();

            // Salva o token no localStorage (ou sessionStorage) para autenticação futura
            localStorage.setItem('authToken', data.token);

            alert('Login realizado com sucesso! Redirecionando para o chat...');
            window.location.href = '/chat.html'; // Redireciona para a tela de mensagens
        } else {
            const error = await response.json();
            alert('Erro ao fazer login: ' + error.message); // Exibe mensagem de erro
        }
    } catch (err) {
        console.error(err);
        alert('Erro inesperado durante o login. Tente novamente mais tarde.');
    }
});
