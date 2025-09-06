document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll('[data-scenario-search]').forEach((input) => {
        let timeoutId;

        input.addEventListener('input', () => {
            clearTimeout(timeoutId);

            timeoutId = setTimeout(() => {
                fetchFilteredScenarios()
            }, 700);
        });
    });

    document.querySelectorAll('[data-scenario-dropdown]').forEach(dropdown => {
        const toggle = dropdown.querySelector('[data-toggle]');
        const label = toggle.querySelector('.dropdown-label');
        const menu = dropdown.querySelector('.dropdown-menu');

        toggle.addEventListener('click', e => {
            e.stopPropagation();
            document.querySelectorAll('.dropdown').forEach(d => d.classList.remove('active'));
            dropdown.classList.toggle('active');
        });

        menu.querySelectorAll('li').forEach(item => {
            item.addEventListener('click', () => {
                label.textContent = item.textContent;
                toggle.dataset.value = item.dataset.value;
                dropdown.classList.remove('active');

                fetchFilteredScenarios()
            });
        });
    });
    document.addEventListener('click', () => {
        document.querySelectorAll('.dropdown').forEach(d => d.classList.remove('active'));
    });
})

function fetchFilteredScenarios() {
    const title = document.querySelector('[data-scenario-search]')?.value || '';
    const difficulty = document.querySelector('[data-scenario-dropdown="difficulty"] [data-toggle]')?.dataset.value || '';
    const attackType = document.querySelector('[data-scenario-dropdown="attack"] [data-toggle]')?.dataset.value || '';

    const params = new URLSearchParams();
    if (title) params.append('title', title);
    if (difficulty) params.append('difficulty', difficulty);
    if (attackType) params.append('attack_type_label', attackType);

    const newUrl = `${window.location.pathname}?${params.toString()}`;
    history.replaceState(null, '', newUrl);

    fetch(`/api/scenarios?${params.toString()}`)
        .then(res => res.json())
        .then(renderScenarios)
        .catch(err => console.error('Failed to fetch scenarios:', err));
}

function renderScenarios(scenarios) {
    const grid = document.querySelector('[data-scenarios-container]');
    grid.innerHTML = ''; // Очистить

    if (scenarios.length === 0) {
        grid.innerHTML = `
                    <div class="card scenario-card empty-card">
                        <div class="header">
                            <h3>No scenarios found</h3>
                        </div>
                        <p class="description">Try adjusting your filters or check back later.</p>
                    </div>
                `;
        return;
    }

    scenarios.forEach(scenario => {
        const tags = scenario.attackTypes.map(at =>
            `<span class="tag" title="${at.description}" data-desc="${at.description}">${at.label}</span>`
        ).join('');

        grid.innerHTML += `
                    <div class="card scenario-card">
                        <div class="top">
                            <div class="header">
                                <h3>${scenario.title}</h3>
                                <span class="badge difficulty badge-${scenario.lowerDifficulty}">
                                    ${scenario.capitalizedDifficulty}
                                </span>
                            </div>
                            <p class="description">${scenario.description}</p>
                        </div>
                        <div class="bottom">
                            <div class="attack-tags">${tags}</div>
                            <a href="/scenario/${scenario.id}" class="btn-primary">Start Scenario</a>
                        </div>  
                    </div>
                `;
    });
}