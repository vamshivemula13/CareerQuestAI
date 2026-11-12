const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const message = document.getElementById("loginMessage");

    message.textContent = "";

    if (!email || !password) {
        message.textContent = "Please enter your email and password.";
        return;
    }

    try {
        const response = await fetch(
            "http://localhost:8081/api/auth/login",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            }
        );

        const data = await response.json();

        if (response.ok) {

            // Store JWT token
            localStorage.setItem("token", data.token);

            message.textContent = "Login successful!";

            console.log("Login response:", data);

            // Temporary redirect
            setTimeout(() => {
                window.location.href = "index.html";
            }, 1000);

        } else {

            message.textContent =
                data.message || "Invalid email or password.";

            console.error("Login failed:", data);
        }

    } catch (error) {

        console.error("Login error:", error);

        message.textContent =
            "Unable to connect to the server. Make sure the backend is running.";
    }
});