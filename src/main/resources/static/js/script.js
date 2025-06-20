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

const registerForm = document.getElementById('register_form');

registerForm.addEventListener('submit', async function (event) {
    event.preventDefault();

    const formData = new FormData(this);

    document.querySelector('#custom_loader_box').classList.add('show');

    try {
        const response = await fetch('/selecao/candidatar', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            alert('Candidatura enviada com sucesso!');
        } else {
            const errorText = await response.text();
            alert('Erro ao enviar candidatura: ' + errorText);
        }
    } catch (error) {
        console.error('Erro de comunicação com o backend:', error);
        alert('Erro ao enviar candidatura, tente novamente.');
    }

    document.querySelector('#custom_loader_box').classList.remove('show');
});
