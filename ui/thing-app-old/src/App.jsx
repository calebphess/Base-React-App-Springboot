// App.js
import React from 'react';
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";

import LoginPage from './LoginPage';
import AuditPage from './AuditPage';


const router = createBrowserRouter([
  {
    path: "/",
    element: <LoginPage />,
  },
  {
    path: "/audit",
    element: <AuditPage />,
  },
]);

const App = () => {
  return (
    <div className="App">
      <RouterProvider router={router} />
    </div>
  );
}

export default App;