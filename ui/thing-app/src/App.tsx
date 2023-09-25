import { Route, Routes } from "react-router-dom";
import HomePage from './pages/homePage/homePage';
import LoginPage from './pages/loginPage/loginPage';
import DogPage from './pages/dogPage/dogPage';

function App() {
  return (
    // TODO: add a section that sits on top of everything that draws the login card and a background dimmer if the redux state of promptLogin is true
    // TODO: eventually set up lazy loading if the app gets too big
    
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<DogPage />} />
        <Route path="/doggo" element={<DogPage />} />
      </Routes>
  );
}

export default App;