// AuditPage.js
import React, { useState, useEffect } from 'react';

const AuditPage = () => {
  const [auditData, setAuditData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchAuditData() {
      const accessToken = localStorage.getItem("accessToken");

      console.log("AUDIT ACCESS TOKEN: " + accessToken)

      const response = await fetch("https://localhost:8443/thingapp/api/audit", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${accessToken}`
        }
      });

      console.log("RESPONSE: " + response);

      if (response.ok) {
        const data = await response.json();
        setAuditData(data);
        setLoading(false);
      } else {
        console.error("Failed to fetch audit data");
        setLoading(false);
      }
    }

    fetchAuditData();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="grid-container">
      {auditData.map(audit => (
        <div key={audit.id} className="grid-item">
          <p><strong>URL:</strong> {audit.url}</p>
          <p><strong>Source IP:</strong> {audit.sourceIp}</p>
          <p><strong>User ID:</strong> {audit.userId}</p>
          {/* Add other fields as necessary */}
        </div>
      ))}
    </div>
  );
};

export default AuditPage;
