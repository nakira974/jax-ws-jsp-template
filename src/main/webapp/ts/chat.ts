$(document).ready(() => {

    const messageInput = document.getElementById('message') as HTMLInputElement;
    const submit = document.getElementById('submit') as HTMLInputElement;
    const blincker = document.querySelector('.blincker') as HTMLElement;

    // Declare updateChatHistory function
    const updateChatHistory = () => {
        $.getJSON('/chat', (chatHistory: any) => {
            let html = '';
            $.each(chatHistory, (index: any, msg: string) => {
                html += '<li>' + msg + '</li>';
            });
            $('#chatHistory').html(html);
        });
    };

    // Fetch the initial chat history
    updateChatHistory();

    // Submit the message form via AJAX
    $('#messageForm').submit((event: { preventDefault: () => void; currentTarget: any; }) => {
        event.preventDefault(); // Prevent the form from submitting the traditional way
        const action = $(event.currentTarget).attr('action');
        if (action !== undefined) { // Check if action is defined
            $.post(action, $(event.currentTarget).serialize(), () => {
                // Clear the input field and refresh the chat history
                $('#message').val('');
                updateChatHistory();
            });
        }
    });

    messageInput.addEventListener('keyup', () => {
        if (messageInput.value.trim() !== '') {
            blincker.style.color = "darkgreen"
            blincker.classList.add('blink');
        }
    });

    submit.addEventListener('click', () => {
        blincker.classList.remove('blink');
        setTimeout(() => {
            blincker.classList.remove('blink');
        }, 1000); // Remove the class after 1 second
    });
});