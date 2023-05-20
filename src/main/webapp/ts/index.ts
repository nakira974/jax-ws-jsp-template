document.addEventListener('DOMContentLoaded', () => {
    function updateAnimation() {
        // Get current hour
        const hour = new Date().getHours();

        // Set time-specific class
        const animationContainer = document.querySelector('.animation-container') as HTMLElement;
        if (hour >= 6 && hour <= 18) {
            animationContainer.classList.add('day');
        } else {
            animationContainer.classList.add('night');
        }
    }

    // Call the function when the page is loaded
    window.onload = updateAnimation;
});