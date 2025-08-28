document.addEventListener("DOMContentLoaded", () => {
    document.querySelector('[data-scenarios-container]')?.addEventListener('click', ev => {
        const tag = ev.target.closest('[data-desc]');
        if (tag) {
            const dialog = document.getElementById('tagDialog');
            const content = dialog.querySelector('#dialogContent');
            content.textContent = tag.getAttribute('data-desc');
            dialog.showModal();
        }
    })

})