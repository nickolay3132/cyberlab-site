import "./scenarios/tags.js"
import "./scenarios/filters.js"

document.addEventListener('DOMContentLoaded', function() {
    const toggleBtn = document.querySelector('.menu-toggle');
    const dropdown = document.querySelector('.nav-dropdown-menu');

    toggleBtn.addEventListener('click', () => {
        dropdown.style.display = dropdown.style.display === 'flex' ? 'none' : 'flex';
    });

    window.addEventListener('scroll', () => {
        dropdown.style.display = 'none'
    })



    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();

            const targetId = this.getAttribute('href');
            if (targetId === '#') return;

            const targetElement = document.querySelector(targetId);
            if (targetElement) {
                targetElement.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });

    // Sticky navbar on scroll
    const navbar = document.querySelector('.navbar');
    const hero = document.querySelector('.hero');

    if (navbar && hero) {
        const process_nav_menu = () => {
            dropdown.style.display = 'none';
        }
        const heroHeight = hero.offsetHeight;

        window.addEventListener('scroll', function() {
            if (window.scrollY > heroHeight * 0.8) {
                navbar.classList.add('sticky');
                dropdown.style.display = 'none';
                dropdown.style.top = '100%'
            } else {
                navbar.classList.remove('sticky');
                dropdown.style.display = 'none';
                dropdown.style.top = '15%'
            }
        });
    }

    // Add animation to feature cards when they come into view
    const featureCards = document.querySelectorAll('.feature-card');
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver(function(entries, observer) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('animate');
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);

    featureCards.forEach(card => {
        observer.observe(card);
    });

    const copyLicenseText = document.querySelector('#copy-license-text')
    if (copyLicenseText) {
        copyLicenseText.addEventListener('click', () => {
            const text = document.getElementById("license-text").innerText;
            navigator.clipboard.writeText(text).then(() => {
                console.log("License copied")

                const btn = document.querySelector("#copy-license-text");
                btn.classList.add("copied");
                btn.querySelector(".copy-btn-label").innerText = "Copied"

                setTimeout(() => {
                    btn.classList.remove("copied");
                    btn.querySelector(".copy-btn-label").innerText = "Copy"
                }, 1000);
            }).catch(err => {
                console.error("Copy failed", err);
            });
        })
    }

    // document.querySelectorAll('[data-scenario-search]').forEach((input) => {
    //     let timeoutId;
    //
    //     input.addEventListener('input', () => {
    //         clearTimeout(timeoutId);
    //
    //         timeoutId = setTimeout(() => {
    //             // console.log(`Введено в ${input.getAttribute('data-search')}:`, input.value);
    //             // console.log(`Url: ${input.getAttribute('data-url')}`)
    //             fetchFilteredScenarios()
    //         }, 700);
    //     });
    // });

    // document.querySelector('[data-scenarios-container]')?.addEventListener('click', ev => {
    //     const tag = ev.target.closest('[data-desc]');
    //     if (tag) {
    //         const dialog = document.getElementById('tagDialog');
    //         const content = dialog.querySelector('#dialogContent');
    //         content.textContent = tag.getAttribute('data-desc');
    //         dialog.showModal();
    //     }
    // })


    // document.querySelectorAll('[data-scenario-dropdown]').forEach(dropdown => {
    //     const toggle = dropdown.querySelector('[data-toggle]');
    //     const label = toggle.querySelector('.dropdown-label');
    //     const menu = dropdown.querySelector('.dropdown-menu');
    //
    //     toggle.addEventListener('click', e => {
    //         e.stopPropagation();
    //         document.querySelectorAll('.dropdown').forEach(d => d.classList.remove('active'));
    //         dropdown.classList.toggle('active');
    //     });
    //
    //     menu.querySelectorAll('li').forEach(item => {
    //         item.addEventListener('click', () => {
    //             label.textContent = item.textContent;
    //             toggle.dataset.value = item.dataset.value;
    //             dropdown.classList.remove('active');
    //
    //             // console.log(`selected option for "${dropdown.getAttribute('data-scenario-dropdown')}": ${item.dataset.value}`)
    //             fetchFilteredScenarios()
    //         });
    //     });
    // });
    //
    // document.addEventListener('click', () => {
    //     document.querySelectorAll('.dropdown').forEach(d => d.classList.remove('active'));
    // });
    //
    // function fetchFilteredScenarios() {
    //     const title = document.querySelector('[data-scenario-search]')?.value || '';
    //     const difficulty = document.querySelector('[data-scenario-dropdown="difficulty"] [data-toggle]')?.dataset.value || '';
    //     const attackType = document.querySelector('[data-scenario-dropdown="attack"] [data-toggle]')?.dataset.value || '';
    //
    //     const params = new URLSearchParams();
    //     if (title) params.append('title', title);
    //     if (difficulty) params.append('difficulty', difficulty);
    //     if (attackType) params.append('attack_type_label', attackType);
    //
    //     const newUrl = `${window.location.pathname}?${params.toString()}`;
    //     history.replaceState(null, '', newUrl);
    //
    //     fetch(`/api/scenarios?${params.toString()}`)
    //         .then(res => res.json())
    //         .then(renderScenarios)
    //         .catch(err => console.error('Failed to fetch scenarios:', err));
    // }
    //
    // function renderScenarios(scenarios) {
    //     const grid = document.querySelector('[data-scenarios-container]');
    //     grid.innerHTML = ''; // Очистить
    //
    //     if (scenarios.length === 0) {
    //         grid.innerHTML = `
    //                 <div class="card scenario-card empty-card">
    //                     <div class="header">
    //                         <h3>No scenarios found</h3>
    //                     </div>
    //                     <p class="description">Try adjusting your filters or check back later.</p>
    //                 </div>
    //             `;
    //         return;
    //     }
    //
    //     scenarios.forEach(scenario => {
    //         const tags = scenario.attackTypes.map(at =>
    //             `<span class="tag" title="${at.description}" data-desc="${at.description}">${at.label}</span>`
    //         ).join('');
    //
    //         grid.innerHTML += `
    //                 <div class="card scenario-card">
    //                     <div class="top">
    //                         <div class="header">
    //                             <h3>${scenario.title}</h3>
    //                             <span class="badge difficulty badge-${scenario.lowerDifficulty}">
    //                                 ${scenario.capitalizedDifficulty}
    //                             </span>
    //                         </div>
    //                         <p class="description">${scenario.description}</p>
    //                     </div>
    //                     <div class="bottom">
    //                         <div class="attack-tags">${tags}</div>
    //                         <a href="/scenarios/${scenario.id}" class="btn-primary">Start Scenario</a>
    //                     </div>
    //                 </div>
    //             `;
    //     });
    // }
});