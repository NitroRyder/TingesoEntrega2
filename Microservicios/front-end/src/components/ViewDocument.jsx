import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import documentosService from '../services/documentos.service';

const ViewDocument = () => {
  const [documentoId, setDocumentoId] = useState('');
  const [documento, setDocumento] = useState(null);
  const [error, setError] = useState(null);
  const [documentos, setDocumentos] = useState([]);
  const navigate = useNavigate();


  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await documentosService.getAll();
        setDocumentos(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  const handleChange = (e) => {
    setDocumentoId(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null); // Reset error state

    try {
      const response = await documentosService.getById(documentoId);
      setDocumento(response.data);
      alert('Documento obtenido con éxito');
    } catch (error) {
      setError('Error al obtener el documento. Por favor, intente de nuevo.');
      console.error('Error:', error);
    }
  };

  return (
    <div className="container mt-5">
      <h3 className="mt-5">Documentos y Créditos</h3>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>ID del Documento</th>
            <th>ID del Crédito</th>
          </tr>
        </thead>
        <tbody>
          {documentos.map((doc) => (
            <tr key={doc.id}>
              <td>{doc.id}</td>
              <td>{doc.creditoId}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h2>Ver Documento</h2>
      {/*---------------------------------------------------------------------------------------------*/}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">ID del Documento</label>
          <input type="number" className="form-control" value={documentoId} onChange={handleChange} placeholder="Ejemplo: 1" required />
          <small className="form-text text-muted">
            Ingrese el ID del documento que desea ver.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="d-grid gap-2">
          <button type="submit" className="btn btn-primary btn-lg">Ver Documento</button>
        </div>
      </form>
      {/*---------------------------------------------------------------------------------------------*/}
      {documento && (
        <div className="mt-3">
          <h4>Detalles del Documento</h4>
          <p><strong>Documento ID:</strong> {documento.id}</p>
          <p><strong>Credito ID:</strong> {documento.creditoId}</p>
          <p><strong>Documento:</strong></p>
          <a href={`data:application/octet-stream;base64,${documento.documento}`} download={`documento_${documento.id}`}>Descargar Documento</a>
        </div>
      )}
      {/*---------------------------------------------------------------------------------------------*/}
      {error && (
        <div className="mt-3 alert alert-danger">
          {error}
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
    </div>
  );
};

export default ViewDocument;