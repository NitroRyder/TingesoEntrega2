import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import seguimientoService from '../services/seguimiento.service';

const followCredito = () => {
  const [userId, setUserId] = useState('');
  const [solicitudes, setSolicitudes] = useState([]);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setUserId(Number(e.target.value));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log('Submitting userId:', userId);

    try {
      const response = await seguimientoService.followCredito(userId);
      if (response.data.length === 0) {
        alert('ERROR: EL USUARIO NO TIENE SOLICITUDES DE CRÉDITO');
      } else {
        setSolicitudes(response.data); // Almacenar las solicitudes en el estado
        console.log('Solicitudes recibidas:', response.data); // Verificar los datos recibidos
      }
    } catch (error) {
      console.error('Error response:', error.response);
      if (error.response) {
        console.error('Error data:', error.response.data);
        console.error('Error status:', error.response.status);
        console.error('Error headers:', error.response.headers);
      }
      alert('Error al realizar el seguimiento del préstamo: ' + (error.response?.data || error.message));
    }
  };

  return (
    <div className="container">
      <h2>Seguimiento de Préstamos</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>User ID:</label>
          <input type="number" id="userId" value={userId} onChange={handleChange} className="form-control" placeholder="Ejemplo: 1" required />
          <small className="form-text text-muted">
            Ingrese el ID de su cuenta. Ej 102
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}        
        <div className="d-grid gap-2">
          <button type="submit" className="btn btn-primary btn-lg">Realizar Seguimiento</button>
        </div>
      </form>
      {solicitudes.length > 0 && (
        <div className="mt-3">
          <h3>Detalles de las Solicitudes</h3>
          {solicitudes.map((solicitud, index) => (
            <div className="card mb-3" key={index}>
              <div className="card-body">
                <p className="card-text"><strong>ID de la Solicitud:</strong> {solicitud.id}</p>
                <p className="card-text"><strong>Estado de la solicitud:</strong> {solicitud.state}</p>
                <p className="card-text"><strong>Monto del Préstamo:</strong> {solicitud.montop}</p>
                <p className="card-text"><strong>Plazo:</strong> {solicitud.plazo} años</p>
                <p className="card-text"><strong>Tasa de Interés Anual:</strong> {solicitud.intanu}%</p>
                <p className="card-text"><strong>Tasa de Interés Mensual:</strong> {solicitud.intmen}%</p>
                <p className="card-text"><strong>Seguro de Desgravamen:</strong> {solicitud.segudesg}</p>
                <p className="card-text"><strong>Seguro de Incendio:</strong> {solicitud.seguince}</p>
                <p className="card-text"><strong>Comisión Administrativa:</strong> {solicitud.comiad}</p>
              </div>
            </div>
          ))}
        </div>
      )}
        {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mt-3 d-grid gap-2">
          <button 
            className="btn btn-dark btn-lg"
            onClick={() => navigate('/home/Client')}
          >
            Regresar a Operaciones del Cliente
          </button>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}        
    </div>
  );
};

export default followCredito;