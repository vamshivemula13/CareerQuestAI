const registerForm = document.getElementById("registerForm");

const roleOptions = document.querySelectorAll(".role-option");

let selectedRole = "JOB_SEEKER";


/* =========================
   ROLE SELECTION
========================= */

roleOptions.forEach(option => {

    option.addEventListener("click", function () {

        roleOptions.forEach(item => {
            item.classList.remove("active");
        });

        this.classList.add("active");

        selectedRole = this.dataset.role;

        console.log("Selected role:", selectedRole);
    });

});


/* =========================
   REGISTRATION
========================= */

registerForm.addEventListener("submit", async function (event) {

    event.preventDefault();


    const fullName =
        document.getElementById("fullName").value.trim();

    const email =
        document.getElementById("email").value.trim();

    const phone =
        document.getElementById("phone").value.trim();

    const password =
        document.getElementById("password").value;

    const confirmPassword =
        document.getElementById("confirmPassword").value;

    const message =
        document.getElementById("registerMessage");


    message.textContent = "";


    /* =========================
       FRONTEND VALIDATION
    ========================= */

    if (!fullName || !email || !phone || !password || !confirmPassword) {

        message.textContent =
            "Please fill in all required fields.";

        return;
    }


    if (password.length < 6) {

        message.textContent =
            "Password must contain at least 6 characters.";

        return;
    }


    if (password !== confirmPassword) {

        message.textContent =
            "Passwords do not match.";

        return;
    }


    /* =========================
       SEND REQUEST TO BACKEND
    ========================= */

    try {

        message.textContent =
            "Creating your account...";


        const response = await fetch(
            "http://localhost:8081/api/auth/register",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({

                    fullName: fullName,

                    email: email,

                    password: password,

                    phone: phone,

                    role: selectedRole

                })
            }
        );


        const data = await response.json();


        /* =========================
           SUCCESS
        ========================= */

        if (response.ok) {

            message.textContent =
                "Registration successful! Redirecting to login...";

            console.log("Registration response:", data);


            setTimeout(() => {

                window.location.href = "login.html";

            }, 1500);

        }


        /* =========================
           ERROR
        ========================= */

        else {

            message.textContent =
                data.message ||
                "Registration failed. Please try again.";

            console.error(
                "Registration failed:",
                data
            );
        }

    }


    catch (error) {

        console.error(
            "Registration error:",
            error
        );

        message.textContent =
            "Unable to connect to the server. Make sure the backend is running.";

    }

});