import 'https://cdn.jsdelivr.net/npm/canvas-confetti@1.6.0/dist/confetti.browser.min.js'

let currentStepIndex = null;
const totalSteps = document.body.getAttribute("data-total-steps")

const container = document.getElementById("scenario-container");
const startBtn = document.getElementById("start-btn");
const continueBtn = document.getElementById("continue-btn");
const restartBtn = document.getElementById("restart-btn");

startBtn?.addEventListener("click", startScenario);
continueBtn?.addEventListener("click", continueScenario);
restartBtn?.addEventListener("click", startScenario);

async function startScenario() {
    const scenarioId = getScenarioId();
    const response = await fetch("/api/scenario/start", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ scenario_id: scenarioId })
    });

    const data = await response.json();
    if (data.step) {
        currentStepIndex = 1;

        renderStep(scenarioId, data.step);
    }
}

async function continueScenario() {
    const scenarioId = getScenarioId();
    const response = await fetch("/api/scenario/continue", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ scenario_id: scenarioId })
    });

    const data = await response.json();
    if (data.step) {
        currentStepIndex = document.body.getAttribute("data-current-step");
        renderStep(scenarioId, data.step);
    }
}

async function advanceToNextStep() {
    const scenarioId = getScenarioId();
    const userFlag = document.getElementById("flag-input").value.trim();
    const feedback = document.getElementById("feedback");

    const response = await fetch("/api/scenario/advance", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({
            scenario_id: scenarioId,
            flag: userFlag
        })
    });

    const data = await response.json();
    document.querySelector('.flag-label').classList.remove("error")

    switch (data.status) {
        case "Completed":
            showCompletion();
            break;
        case "InvalidFlag":
            document.querySelector('.flag-label').classList.add("error")
            break;
        case "InvalidSession":
            feedback.textContent = "⚠️ Session expired. Please restart.";
            feedback.style.color = "orange";
            break;
        case "ScenarioNotFound":
            feedback.textContent = "⚠️ Scenario not found.";
            feedback.style.color = "orange";
            break;
        default:
            currentStepIndex++;
            renderStep(scenarioId, data.step);
    }
}

function renderStep(scenarioId, step) {
    const wrapper = container.querySelector(".container__wrapper");
    wrapper.innerHTML = `
        <h3>Step ${currentStepIndex} of ${totalSteps}</h3>
        <div class="instructions">${step.instruction}</div>
        <div class="form-group space">
            <label for="flag-input" class="flag-label">Enter flag:</label>
            <input type="text" 
                   id="flag-input" 
                   placeholder="Found Flag" 
                   class="flag-input"
                   autocomplete="off"
                   autocapitalize="off"
                   spellcheck="false"
            >
        </div>
        <button class="btn-primary" id="submit-flag">Submit</button>
        <p id="feedback" class="feedback"></p>
    `;

    document.getElementById("submit-flag").addEventListener("click", advanceToNextStep);
}

function showCompletion() {
    const wrapper = container.querySelector(".container__wrapper");
    wrapper.innerHTML = `
        <h3>🎉 Scenario Completed</h3>
        <p>Well done! You’ve completed all steps.</p>
    `;

    confetti({ particleCount: 100, angle: 60, spread: 55, origin: { x: 0 } });
    confetti({ particleCount: 100, angle: 120, spread: 55, origin: { x: 1 } });
}

function getScenarioId() {
    return document.body.getAttribute("data-scenario-id");
}

