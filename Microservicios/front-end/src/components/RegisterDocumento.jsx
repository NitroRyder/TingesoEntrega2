import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import documentosService from '../services/documentos.service';

const registrarDocumentos = () => {
    const [formData, setFormData] = useState({
        creditoId: '',
        documento: null
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value, files } = e.target;
        if (files) {
            setFormData({ ...formData, [name]: files[0] });
        } else {
            setFormData({ ...formData, [name]: value });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const data = new FormData();
        for (const key in formData) {
            data.append(key, formData[key]);
        }
        const { creditoId, documento } = formData;
        if (creditoId <= 0) {
            alert("ERROR: EL ID DEL CRÉDITO DEBE SER MAYOR A 0");
            return;
        }
        if(creditoId === ''){
            alert("ERROR: DEBE INGRESAR EL ID DEL CRÉDITO");
            return;
        }
        if (!documento) {
            alert("ERROR: DEBE SELECCIONAR UN DOCUMENTO");
            return;
        }
        try {
            const response = await documentosService.registrarDocumentos(creditoId, documento);
            alert('Documento registrado con éxito');
            alert('ATENCIÓN, el ID de la documento es: ' + response.data.id);
            navigate('/home/Client'); // Redirige a la página de operaciones del cliente
        } catch (error) {
            console.error('Error al registrar el documento:', error);
            alert('Error al registrar el documento: ' + (error.response?.data || error.message));
        }
    };

    return (
        <div className="container">
            <h2>Registrar Documentos</h2>
            <h3><small>OBSERVACIÓN: SOLO INGRESAR UN ARCHIVO POR CREDITO</small></h3>
            <form onSubmit={handleSubmit}>
                {/*---------------------------------------------------------------------------------------------*/}
                <div className="form-group">
                    <label htmlFor="creditoId">ID del Crédito</label>
                    <input type="number" className="form-control" id="creditoId" name="creditoId" placeholder="Ejemplo: 1" onChange={handleChange} />
                    <small className="form-text text-muted">Ingrese el ID del credito.</small>
                </div>
                {/*---------------------------------------------------------------------------------------------*/}
                <div className="form-group">
                    <label htmlFor="documento">Documento</label>
                    <input type="file" className="form-control" id="documento" name="documento" onChange={handleChange} />
                    <small className="form-text text-muted">
                        Ingrese un archivo del tipo pdf con la información necesaria para el prestamo.
                    </small>
                </div>
                {/*---------------------------------------------------------------------------------------------*/}
                <div className="mt-3 d-grid gap-2">
                    <button type="submit" className="btn btn-primary mt-3 btn-lg">Registrar Documento</button>
                </div>            
            </form>
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
}

export default registrarDocumentos;