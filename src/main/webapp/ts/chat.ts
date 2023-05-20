$(document).ready(() => {
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
});