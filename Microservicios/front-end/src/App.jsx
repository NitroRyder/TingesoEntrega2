import './App.css';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Home from './components/Home';
import ClientHome from './components/ClientHome';
import RegisterUsuario from './components/RegisterUsuario';
import SimularCredito from './components/SimularCredito';
import SolicitarPrestamo from './components/SolicitarPrestamo';
import SolicitarAhorro from './components/SolicitarAhorro';
import RegisterDocumento from './components/RegisterDocumento';
import EvaluarCredito from './components/EvaluarCredito';
import CalcularCosto from './components/CalcularCosto';
import SeguimientoPrestamo from './components/SeguimientoPrestamo';
import VerNotificationes from './components/VerNotificationes';
import UpdateState from './components/UpdateState';
import ViewDocument from './components/ViewDocument';

import UsuarioList from './components/UsuarioList';

function App() {
  return (
    <Router>
        {/*---------------------------------------------------------------------------------------------*/}
      <div className="container">
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/home/Client" element={<ClientHome />}/>
          <Route path="/usuario/register" element={<RegisterUsuario />} />
          <Route path="/credito/simular" element={<SimularCredito />} />
          <Route path="/credito/solicitar" element={<SolicitarPrestamo />} />
          <Route path="/ahorro/solicitar" element={<SolicitarAhorro />} />
          <Route path="/documento/register" element={<RegisterDocumento />} />
          <Route path="/credito/evaluar" element={<EvaluarCredito />} />
          <Route path="/credito/seguimiento" element={<SeguimientoPrestamo />} />
          <Route path="/costo/calcular" element={<CalcularCosto />} />
          <Route path="/usuario/notificaciones" element={<VerNotificationes />} />
          <Route path="/update-state" element={<UpdateState/>}/>    
          <Route path="/usuario/list" element={<UsuarioList/>} />
          <Route path="/view/Documento" element={<ViewDocument />} />
        </Routes>
      </div>
        {/*---------------------------------------------------------------------------------------------*/}
    </Router>
  );
}

export default App;