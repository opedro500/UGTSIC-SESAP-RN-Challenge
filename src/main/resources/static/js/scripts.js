// JavaScript para formatação automática do input de telefone
const phone = document.getElementById("phone");
phone.addEventListener("input", function(e) {
    let input = e.target.value.replace(/\D/g, "");

    if (input.length > 10) {
        input = input.replace(/^(\d{2})(\d{5})(\d{4}).*/, "($1) $2-$3");
    } else if (input.length > 6) {
        input = input.replace(/^(\d{2})(\d{4})(\d{0,4}).*/, "($1) $2-$3");
    } else if (input.length > 2) {
        input = input.replace(/^(\d{2})(\d{0,5})/, "($1) $2");
    } else {
        input = input.replace(/^(\d*)/, "($1");
    }

    e.target.value = input;
});

// JavaScript para input de arquivo
const fileInput = document.getElementById('file');
const fileNameDisplay = document.getElementById('file_name');
fileInput.addEventListener('change', () => {
    const file = fileInput.files[0];
    if (file) {
        fileNameDisplay.textContent = `Arquivo selecionado: ${file.name}`;
    } else {
        fileNameDisplay.textContent = 'Nenhum arquivo selecionado';
    }
});

// JavaScript para botão de submit com SweetAlert2
const registerForm = document.getElementById('register_form');
const sendButton = document.getElementById('send_button'); // Pegando o botão de enviar
const loaderBox = document.querySelector('#custom_loader_box');

registerForm.addEventListener('submit', async function (event) {
    event.preventDefault();

    const formData = new FormData(this);

    loaderBox.classList.add('show');
    sendButton.style.display = 'none';

    try {
        const response = await fetch('/selecao/candidatar', {
            method: 'POST',
            body: formData
        });

        const data = await response.json();

        if (response.ok) {
            Swal.fire({
                title: 'Sucesso!',
                text: data.mensagem,
                icon: 'success',
                confirmButtonColor: '#498ea0',
                confirmButtonText: 'Concluir'
            }).then(() => {
                registerForm.reset();
                fileNameDisplay.textContent = 'Nenhum arquivo selecionado';
            });
        } else {
            Swal.fire({
                title: 'Ops!',
                text: data.mensagem,
                icon: 'error',
                confirmButtonColor: '#498ea0'
            });
        }
    } catch (error) {
        Swal.fire({
            title: 'Erro de Conexão',
            text: 'Erro ao conectar com o servidor. Verifique sua internet ou tente novamente mais tarde.',
            icon: 'warning',
            confirmButtonColor: '#498ea0'
        });
    } finally {
        loaderBox.classList.remove('show');
        sendButton.style.display = 'block';
    }
});
