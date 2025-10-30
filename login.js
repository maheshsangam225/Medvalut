document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("login-form");
    const apiMessage = document.getElementById("api-message");

    const API_URL = "http://localhost:8080/api/auth/login";

    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault(); 

        const email = loginForm.email.value;
        const password = loginForm.password.value;

        const loginData = {
            email: email,
            password: password
        };

        try {
            const response = await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(loginData)
            });
            


            const message = await response.json();
            localStorage.setItem("token", message.token);
localStorage.setItem("role", message.role);
localStorage.setItem("firstName", message.firstName);
            if (response.ok) {
                console.log(message);
                if(message.role === "ROLE_PATIENT") {
                    window.location.href = "patientdashboard.html";
                }
                else if (message.role === "ROLE_DOCTOR") {
                    window.location.href = "doctordashboard.html";
                }
                else{
                    showMessage(message.role);
                }
                showMessage(message.role);
            
             } else {
                 showMessage("login failed");
             }
        } catch (error) {
            console.error("Login error:", error);
            showMessage("An error occurred. Please try again.", "error");
        }
    });

    function showMessage(message, type) {
        apiMessage.textContent = message;
        apiMessage.className = `api-message ${type}`;
        apiMessage.style.display = "block";
    }
});