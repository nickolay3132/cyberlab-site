import 'https://cdn.jsdelivr.net/npm/canvas-confetti@1.6.0/dist/confetti.browser.min.js'


const steps = [
    {
        instruction: "Scan the vulnerable system and identify the open ports.",
        expectedFlag: "FLAG{open_ports}"
    },
    {
        instruction: "Exploit the outdated service to gain access.",
        expectedFlag: "FLAG{exploit_success}"
    },
    {
        instruction: "Establish persistence and exfiltrate data.",
        expectedFlag: "FLAG{data_exfiltrated}"
    }
];

let currentStep = 0;

const container = document.getElementById("scenario-container");
const startBtn = document.getElementById("start-btn");

startBtn.addEventListener("click", () => {
    renderStep(currentStep);
});

function renderStep(index) {
    container.innerHTML = `
      <h3>Step ${index + 1}</h3>
      <p style="margin-bottom:1.5rem;">${steps[index].instruction}</p>
      <div class="form-group" style="margin-bottom:1rem;">
        <label for="flag-input">Enter flag:</label>
        <input type="text" id="flag-input" placeholder="FLAG{...}" style="padding:0.75rem 1rem;font-size:1rem;border:1px solid var(--gray-color);border-radius:0.375rem;width:100%;">
      </div>
      <button class="btn-primary" id="submit-flag">Submit</button>
      <p id="feedback" style="margin-top:1rem;font-size:0.875rem;"></p>
    `;

    document.getElementById("submit-flag").addEventListener("click", () => {
        const userFlag = document.getElementById("flag-input").value.trim();
        const feedback = document.getElementById("feedback");

        if (userFlag === steps[index].expectedFlag) {
            feedback.textContent = "✅ Correct flag!";
            feedback.style.color = "green";
            currentStep++;

            if (currentStep < steps.length) {
                setTimeout(() => renderStep(currentStep), 1000);
            } else {
                setTimeout(() => {
                    container.innerHTML = `
      <h3>🎉 Scenario Completed</h3>
      <p>Well done! You’ve completed all steps.</p>
    `;
                    // Конфетти с левой стороны
                    confetti({
                        particleCount: 100,
                        angle: 60,
                        spread: 55,
                        origin: { x: 0 }
                    });

                    // Конфетти с правой стороны
                    confetti({
                        particleCount: 100,
                        angle: 120,
                        spread: 55,
                        origin: { x: 1 }
                    });

                }, 1000);
            }
        } else {
            feedback.textContent = "❌ Incorrect flag. Try again.";
            feedback.style.color = "var(--danger-color)";
        }
    });
}