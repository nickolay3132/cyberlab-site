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


    document.querySelectorAll('[data-dropdown]').forEach(dropdown => {
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

                console.log(`selected option for "${dropdown.dataset.dropdown}": ${item.dataset.value}`)
            });
        });
    });

    document.addEventListener('click', () => {
        document.querySelectorAll('.dropdown').forEach(d => d.classList.remove('active'));
    });
});