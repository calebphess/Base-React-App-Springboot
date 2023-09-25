// LoginPage.js
import React, { useState } from 'react';
import {
  Form,
  useLoaderData,
  redirect,
  useNavigate,
} from "react-router-dom";

const LoginPage = () => {
  const [userLoginId, setEmail] = useState("");
  const [userPassword, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [serverResponse, setServerResponse] = useState(null);
  
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch("https://localhost:8443/thingapp/api/auth/token", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userLoginId, userPassword }),
    });

    if (response.ok) {
      const responseData = await response.json();
      localStorage.setItem("accessToken", responseData.accessToken);
      localStorage.setItem("expires", responseData.expires);
      localStorage.setItem("tokenType", responseData.tokenType);
      
      console.log("accessToken: " + responseData.accessToken)
      console.log("expires: " + responseData.expires)
      console.log("tokenType: " + responseData.tokenType)

      setError(null);
      setServerResponse(responseData);
      await new Promise(r => setTimeout(() => r(), 5000));
      navigate("/audit");
      // Redirect or perform some other action after successful login here
    } else {
      const errorData = await response.json();
      setError(errorData.message || "Failed to login");
      setServerResponse(errorData);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email:</label>
          <input type="email" value={userLoginId} onChange={(e) => setEmail(e.target.value)} required />
        </div>
        <div>
          <label>Password:</label>
          <input type="password" value={userPassword} onChange={(e) => setPassword(e.target.value)} required />
        </div>
        <button type="submit">Login</button>
      </form>
      {error && <div style={{ color: "red" }}>{error}</div>}

      {/* show server response data */}
      {serverResponse && (
        <div style = {{marginTop: "20px"}}>
          <h3>Server Response</h3>
          <pre>{JSON.stringify(serverResponse, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default LoginPage;