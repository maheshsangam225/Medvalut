document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("authToken");
    const userFirstName = localStorage.getItem("userFirstName");

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    document.getElementById("welcome-message").textContent = `Welcome, ${userFirstName}!`;
    document.getElementById("logout-button").addEventListener("click", logout);

    const headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
    };

    const doctorSelect = document.getElementById("doctor-list");
    const appointmentsListDiv = document.getElementById("my-appointments-list");
    const bookingForm = document.getElementById("book-appointment-form");

    // --- 1. Load Doctors into Select Dropdown ---
    async function fetchDoctors() {
        try {
            const response = await fetch("http://localhost:8080/api/users/doctors", { headers });
            if (!response.ok) throw new Error("Failed to fetch doctors");
            
            const doctors = await response.json();
            doctorSelect.innerHTML = '<option value="">Select a Doctor</option>';
            doctors.forEach(doc => {
                const option = document.createElement("option");
                option.value = doc.id;
                option.textContent = `Dr. ${doc.firstName} ${doc.lastName} (${doc.specialization})`;
                doctorSelect.appendChild(option);
            });
        } catch (error) {
            console.error(error);
            doctorSelect.innerHTML = '<option value="">Could not load doctors</option>';
        }
    }

    // --- 2. Load Patient's Appointments ---
    async function fetchMyAppointments() {
        try {
            const response = await fetch("http://localhost:8080/api/appointments/patient", { headers });
            if (!response.ok) throw new Error("Failed to fetch appointments");

            const appointments = await response.json();
            displayAppointments(appointments);
        } catch (error) {
            console.error(error);
            appointmentsListDiv.innerHTML = "<p>Could not load appointments.</p>";
        }
    }

    // --- 3. Display Appointments in HTML ---
    function displayAppointments(appointments) {
        if (appointments.length === 0) {
            appointmentsListDiv.innerHTML = "<p>You have no appointments.</p>";
            return;
        }

        appointmentsListDiv.innerHTML = "";
        appointments.forEach(app => {
            const date = new Date(app.appointmentDate).toLocaleString();
            const card = document.createElement("div");
            card.className = `appointment-card status-${app.status}`;
            card.innerHTML = `
                <div>
                    <p><strong>Doctor:</strong> Dr. ${app.doctorName}</p>
                    <p><strong>Date:</strong> ${date}</p>
                    <p><strong>Reason:</strong> ${app.reason || 'N/A'}</p>
                </div>
                <div>
                    <span class="appointment-status status-${app.status}">${app.status}</span>
                    ${app.status === 'COMPLETED' ? 
                        '<button class="btn-feedback" data-id="${app.id}">Give Feedback</button>' 
                        : ''}
                </div>
            `;
            appointmentsListDiv.appendChild(card);

            // TODO: Add event listener for feedback button
        });
    }

    // --- 4. Handle Appointment Booking Form Submit ---
    bookingForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const appointmentData = {
            doctorId: document.getElementById("doctor-list").value,
            appointmentDate: document.getElementById("appointment-date").value,
            reason: document.getElementById("appointment-reason").value
        };

        try {
            const response = await fetch("http://localhost:8080/api/appointments", {
                method: "POST",
                headers,
                body: JSON.stringify(appointmentData)
            });

            if (response.ok) {
                alert("Appointment booked successfully! It is now pending doctor approval.");
                bookingForm.reset();
                fetchMyAppointments(); // Refresh the list
            } else {
                const error = await response.text();
                alert("Booking failed: " + error);
            }
        } catch (error) {
            console.error("Error booking:", error);
            alert("An error occurred. Please try again.");
        }
    });

    // --- 5. Logout Function ---
    function logout() {
        localStorage.removeItem("authToken");
        localStorage.removeItem("userFirstName");
        window.location.href = "login.html";
    }

    // --- Initial Page Load ---
    fetchDoctors();
    fetchMyAppointments();
});