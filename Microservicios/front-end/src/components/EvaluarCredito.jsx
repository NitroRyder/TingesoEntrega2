import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import evaluaService from '../services/evalua.service';
import creditoService from '../services/credito.service';
import usuarioService from '../services/usuario.service';
import documentosService from '../services/documentos.service';

const evaluateCredito = () => {
  const [usuariosPendientes, setUsuariosPendientes] = useState([]);
  const [userId, setUserId] = useState('');
  const [creditId, setCreditId] = useState('');
  const [files, setFiles] = useState([]);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === 'userId') {
      setUserId(Number(value));
    } else if (name === 'creditId') {
      setCreditId(Number(value));
    }
  };


  useEffect(() => {
    // Función para obtener créditos con estado pendiente y sus usuarios
    const fetchCreditosPendientes = async () => {
      try {
        const response = await creditoService.getAll(); // Usar el servicio configurado
        const creditosPendientes = response.data.filter(credito => credito.state === 'PENDIENTE');

        // Obtener datos del usuario para cada crédito pendiente
        const creditosConUsuarios = await Promise.all(
          creditosPendientes.map(async (credito) => {
            const usuarioResponse = await usuarioService.getById(credito.usuarioId);
            return {
              ...credito,
              usuario: usuarioResponse.data
            };
          })
        );

        setUsuariosPendientes(creditosConUsuarios);
      } catch (error) {
        console.error('Error al obtener créditos pendientes:', error);
      }
    };

    fetchCreditosPendientes();
  }, []);

  const fetchDocumentos = async (creditId) => {
    try {
      const response = await documentosService.getByCreditoId(creditId);
      setFiles(response.data);
    } catch (error) {
      console.error('Error al obtener documentos:', error);
    }
  };

const handleSubmit = async (e) => {
  e.preventDefault();

  console.log('Submitting userId:', userId);
  console.log('Submitting creditId:', creditId);

  try {
    const response = await evaluaService.evaluateCredito(userId, creditId);
    console.log('Response:', response.data);
    if (response.data === "EVALUACIÓN TERMINADA") {
      alert('EVALUACIÓN DE SOLICITUD DE CRÉDITO EXITOSA');
    } else {
      alert('EVALUACIÓN RECHAZADA');
    }
  } catch (error) {
    console.error('Error response:', error.response);
    if (error.response) {
      console.error('Error data:', error.response.data);
      console.error('Error status:', error.response.status);
      console.error('Error headers:', error.response.headers);
    }
    alert('Error al evaluar el crédito: ' + (error.response?.data || error.message));
  }
};

  return (
    <div className="container">
      {/*---------------------------------------------------------------------------------------------*/}
      <div className="mt-3">
        <h3>Usuarios con Estado Pendiente</h3>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Usuario ID</th>
              <th>Credito ID</th>
              <th>Nombre</th>
              <th>Rut</th>
              <th>Estado</th>
            </tr>
          </thead>
          <tbody>
            {usuariosPendientes.map((credito) => (
              <tr key={credito.id}>
                <td>{credito.usuario.id}</td>
                <td>{credito.id}</td>
                <td>{credito.usuario.name}</td>
                <td>{credito.usuario.rut}</td>
                <td>{credito.state}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {/*---------------------------------------------------------------------------------------------*/}
      <h2>Evaluar Prestamo</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>User ID:</label>
          <input type="number" name="userId" value={userId} onChange={handleChange} className="form-control" placeholder="Ejemplo: 1" required />
          <small className="form-text text-muted">
            Ingrese el ID de su cuenta. Ej 102
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label>Credit ID:</label>
          <input type="number" name="creditId" value={creditId} onChange={handleChange} className="form-control" placeholder="Ejemplo: 1" required />
          <small className="form-text text-muted">
            Ingrese el ID de su cuenta. Ej 102
          </small>
        </div>   
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="d-grid gap-2">
          <button type="submit" className="btn btn-primary btn-lg">Realizar Evaluación</button>
        </div>
      </form>
      {/*---------------------------------------------------------------------------------------------*/}
      {files && files.length > 0 && (
        <div className="mt-3">
          <h3>Archivos de la Solicitud</h3>
          <div className="card">
            <div className="card-body">
              {files.map((file, index) => (
                <p key={index} className="card-text">
                  <a href={`data:application/octet-stream;base64,${file.data}`} download={file.name}>{file.name}</a>
                </p>
              ))}
            </div>
          </div>
        </div>
      )}
      {/*---------------------------------------------------------------------------------------------*/}
      <div className="mt-3 d-grid gap-2">
        <button 
          className="btn btn-warning btn-lg w-100"
          onClick={() => navigate('/view/Documento')}
        >
          Obener Documentación del Usuario
        </button>
      </div>
      {/*---------------------------------------------------------------------------------------------*/}
      <div className="mt-3 d-grid gap-2">
        <button 
          className="btn btn-danger btn-lg"
          onClick={() => navigate('/update-state')}
        >
          Actualizar estado de la solicitud
        </button>
      </div>
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

export default evaluateCredito;