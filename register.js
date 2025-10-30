function openTab(evt, roleName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tab-content");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tab-link");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(roleName).style.display = "block";
    evt.currentTarget.className += " active";
    
    showMessage("", "success", true);
}

document.addEventListener("DOMContentLoaded", () => {
    const patientForm = document.getElementById("patient-reg-form");
    const doctorForm = document.getElementById("doctor-reg-form");
    const adminForm = document.getElementById("admin-reg-form");
    
    const apiMessage = document.getElementById("api-message");
    const API_URL1 = "http://localhost:8080/api/auth/patient/register";
    const API_URL2 = "http://localhost:8080/api/auth/doctor/register";


    patientForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const formData = {
            firstName: patientForm.firstName.value,
            lastName: patientForm.lastName.value,
            email: patientForm.email.value,
            password: patientForm.password.value,
            //role: "PATIENT",
            gender: patientForm.gender.value,
            dateOfBirth: patientForm.dateOfBirth.value,
            address: patientForm.address.value,
            phoneNumber: patientForm.phoneNumber.value,
            emergencyContact: patientForm.emergencyContact.value
        };
        registerPatient(formData, e.target);
    });

    doctorForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const formData = {
            firstName: doctorForm.firstName.value,
            lastName: doctorForm.lastName.value,
            email: doctorForm.email.value,
            password: doctorForm.password.value,
            //role: "DOCTOR",
            gender: doctorForm.gender.value,
            dateOfBirth: doctorForm.dateOfBirth.value,
            //address: doctorForm.address.value,
           // phoneNumber: doctorForm.phoneNumber.value,
            //emergencyContact: doctorForm.emergencyContact.value,
            specialisation: doctorForm.specialty.value,
            licenseNo: doctorForm.license.value,
            hospitalName: doctorForm.hospitalName.value
        };
        registerDoctor(formData, e.target);
    });

  /*  adminForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const formData = {
            firstName: adminForm.firstName.value,
            lastName: adminForm.lastName.value,
            email: adminForm.email.value,
            password: adminForm.password.value,
            role: "ADMIN"
        };
        registerUser(formData, e.target);
    });*/

    async function registerDoctor(data, formElement) {
        try {
            const response = await fetch(API_URL2, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const result = await response.json();
                console.log("Registration successful:", result);
                showMessage(`Registration successful! Welcome, ${result.firstName}.`, "success");
                formElement.reset();
                setTimeout(() => {
                    window.location.href = "login.html";
                }, 2000);
            } else {
                const errorMessage = await response.text();
                showMessage(errorMessage, "error");
            }
        } catch (error) {
            console.error("Registration error:", error);
            showMessage("An error occurred. Please try again.", "error");
        }
    }

    async function registerPatient(data, formElement) {
        try {
            const response = await fetch(API_URL1, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const result = await response.json();
                console.log("Registration successful:", result);
                showMessage(`Registration successful! Welcome, ${result.firstName}.`, "success");
                formElement.reset();
            } else {
                const errorMessage = await response.text();
                showMessage(errorMessage, "error");
            }
        } catch (error) {
            console.error("Registration error:", error);
            showMessage("An error occurred. Please try again.", "error");
        }
    }

    function showMessage(message, type, clear = false) {
        if (clear) {
            apiMessage.style.display = "none";
            return;
        }
        apiMessage.textContent = message;
        apiMessage.className = `api-message ${type}`;
        apiMessage.style.display = "block";
    }
});