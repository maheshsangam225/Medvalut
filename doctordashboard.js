document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    const userFirstName = localStorage.getItem("firstName");
    

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    document.getElementById("welcome-message").textContent = `Welcome, Dr. ${userFirstName}!`;
    document.getElementById("logout-button").addEventListener("click", logout);

    const headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token.trim()
    };

    const pendingListDiv = document.getElementById("pending-appointments-list");
    const pastListDiv = document.getElementById("past-appointments-list");

    // --- 1. Load ALL Doctor's Appointments ---
    async function fetchDoctorAppointments() {
        try {
            const response = await fetch("http://localhost:8080/api/appointments/doctor", { headers });
            if (!response.ok) throw new Error("Failed to fetch appointments");

            const appointments = await response.json();
            displayAppointments(appointments);
        } catch (error) {
            console.error(error);
            pendingListDiv.innerHTML = "<p>Could not load appointments.</p>";
            pastListDiv.innerHTML = "";
        }
    }

    // --- 2. Sort and Display Appointments ---
    function displayAppointments(appointments) {
        pendingListDiv.innerHTML = "";
        pastListDiv.innerHTML = "";

        let pendingCount = 0;
        let pastCount = 0;

        appointments.forEach(app => {
            const date = new Date(app.appointmentDate).toLocaleString();
            const card = document.createElement("div");
            card.className = `appointment-card status-${app.status}`;
            
            let cardHTML = `
                <div>
                    <p><strong>Patient:</strong> ${app.patientName}</p>
                    <p><strong>Date:</strong> ${date}</p>
                    <p><strong>Reason:</strong> ${app.reason || 'N/A'}</p>
                </div>
            `;

            if (app.status === 'PENDING') {
                pendingCount++;
                cardHTML += `
                    <div class="action-buttons">
                        <button class="btn-accept" data-id="${app.id}">Accept</button>
                        <button class="btn-reject" data-id="${app.id}">Reject</button>
                    </div>
                `;
                card.innerHTML = cardHTML;
                pendingListDiv.appendChild(card);
            } else {
                pastCount++;
                cardHTML += `
                    <div>
                        <span class="appointment-status status-${app.status}">${app.status}</span>
                    </div>
                `;
                 card.innerHTML = cardHTML;
                pastListDiv.appendChild(card);
            }
        });

        if (pendingCount === 0) pendingListDiv.innerHTML = "<p>No pending appointments.</p>";
        if (pastCount === 0) pastListDiv.innerHTML = "<p>No past appointments.</p>";
    }

    // --- 3. Handle Accept/Reject Clicks (Event Delegation) ---
    async function handleAppointmentAction(e) {
        if (!e.target.matches(".btn-accept, .btn-reject")) return;

        const button = e.target;
        const id = button.dataset.id;
        const action = button.classList.contains("btn-accept") ? "CONFIRMED" : "REJECTED";

        if (!confirm(`Are you sure you want to ${action.toLowerCase()} this appointment?`)) {
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/appointments/${id}/status`, {
                method: "PUT",
                headers,
                body: JSON.stringify({ status: action })
            });

            if (response.ok) {
                alert(`Appointment ${action} successfully.`);
                fetchDoctorAppointments(); // Refresh the lists
            } else {
                const error = await response.text();
                alert("Action failed: " + error);
            }
        } catch (error) {
            console.error("Error updating status:", error);
            alert("An error occurred. Please try again.");
        }
    }

    // Add single event listener to parent
    pendingListDiv.addEventListener("click", handleAppointmentAction);

    // --- 4. Logout Function ---
    function logout() {
        localStorage.removeItem("authToken");
        localStorage.removeItem("userFirstName");
        window.location.href = "login.html";
    }

    // --- Initial Page Load ---
    fetchDoctorAppointments();
});