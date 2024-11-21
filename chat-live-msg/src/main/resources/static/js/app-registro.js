document.addEventListener('DOMContentLoaded', (event) => {
    const registerForm = document.getElementById('registerForm');

    if (registerForm) {
        registerForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const formData = new FormData();
            formData.append('registerRequest', new Blob([JSON.stringify({
                email: document.getElementById('email').value,
                senha: document.getElementById('password').value,
            })], { type: 'application/json' }));

            const profilePicture = document.getElementById('profilePicture').files[0];
            if (profilePicture) {
                formData.append('fotoUrl', profilePicture);
            }

            try {
                const response = await fetch('/api/auth/register', {
                    method: 'POST',
                    body: formData
                });

                if (response.ok) {
                    alert('Registro realizado com sucesso! Redirecionando para a página de login...');
                    window.location.href = '/login';
                } else {
                    const error = await response.json();
                    alert('Erro ao registrar: ' + error.message);
                }
            } catch (err) {
                console.error(err);
                alert('Erro inesperado durante o registro. Tente novamente mais tarde.');
            }
        });
    } else {
        console.error('Formulário de registro não encontrado.');
    }
});
