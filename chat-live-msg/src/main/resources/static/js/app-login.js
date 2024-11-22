document.addEventListener('DOMContentLoaded', (event) => {
    const loginForm = document.getElementById('loginForm');

    if (loginForm) {
        // Evento de envio do formulário
        loginForm.addEventListener('submit', async (event) => {
            event.preventDefault(); 

            // Captura os valores dos campos do formulário
            const email = document.getElementById('email').value;
            const senha = document.getElementById('password').value;

            try {
                // Faz a requisição POST para o endpoint de login
                const response = await fetch('/api/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json' 
                    },
                    body: JSON.stringify({ email, senha }) 
                });

                if (response.ok) {
                    const data = await response.json();
                    localStorage.setItem('authToken', data.token);
                    alert('Login realizado com sucesso! Redirecionando para o chat...');
                    window.location.href = '/api/chat'; // Redireciona para a tela de mensagens
                } else {
                    const error = await response.json();
                    alert('Erro ao fazer login: ' + error.message); // Exibe mensagem de erro
                }
            } catch (err) {
                console.error(err);
                alert('Erro inesperado durante o login. Tente novamente mais tarde.');
            }
        });
    } else {
        console.error('Formulário de login não encontrado.');
    }
});
