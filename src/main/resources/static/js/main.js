document.addEventListener('DOMContentLoaded', function() {
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
        const heroHeight = hero.offsetHeight;

        window.addEventListener('scroll', function() {
            if (window.scrollY > heroHeight * 0.8) {
                navbar.classList.add('sticky');
            } else {
                navbar.classList.remove('sticky');
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

    document.querySelector('#copy-license-text').addEventListener('click', () => {
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
});