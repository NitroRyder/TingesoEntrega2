import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import ahorroService from '../services/ahorro.service';

const createAhorro = () => {
    const [formData, setFormData] = useState({
        transaccion: '',
        tipo: '',
        usuarioId: ''
      });  

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
        console.log(formData);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const { transaccion, tipo, usuarioId } = formData;
        if (transaccion === '' || tipo === '' || usuarioId === '') {
            alert('ERROR: DEBE INGRESAR TODOS LOS DATOS.');
            return;
        }
        if (transaccion == 0) {
            alert('ERROR: EL MONTO NO PUEDE SER 0.');
            return;
        }
        if(tipo !== 'DEPOSITO' && tipo !== 'PAGO' && tipo !== 'TRANSFERENCIA' && tipo !== 'RETIRO'){
            alert('ERROR: EL TIPO DE AHORRO DEBE SER DEPOSITO O PAGO O TRANSFERENCIA O RETIRO EN TRANSICIÓN.' + i);
            return;
          }
          if(tipo === 'DEPOSITO' && transaccion <= 0){
            alert('ERROR: LA TRANSACCIÓN DE UN DEPOSITO DEBE SER MAYOR A 0 EN TRANSICIÓN.' + i);
            return;
          }
          if((tipo === 'PAGO' || tipo === 'TRANSFERENCIA' || tipo === 'RETIRO') && transaccion >= 0){
            alert('ERROR: LA TRANSACCIÓN DE UN PAGO, TRANSFERENCIA O RETIRO DEBE SER MENOR A 0 EN TRANSICIÓN.' + i);
            return;
          }
        try {
            const response = await ahorroService.createAhorro(transaccion, tipo, usuarioId);
            alert('Ahorro creado con éxito');
            alert('ATENCIÓN, el ID de la ahorro es: ' + response.data.id);
          } catch (error) {
            console.error('Error al crear el ahorro:', error);
            alert('Error al crear el ahorro: ' + (error.response?.data || error.message));
          }
    };

  return (
    <div className="container">
      <h2>Solicitar Ahorro</h2>
      <h3><small>Se recomienda realizar el ingreso de 12 ahorros como minimo</small></h3>
      <form onSubmit={handleSubmit}>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label className="form-label">Transacción:</label>
          <input type="number" className="form-control" name="transaccion" value={formData.transaccion} onChange={handleChange} placeholder="Ejemplo: 200000" required />
          <small className="form-text text-muted">Ingrese el monto de la transacción.</small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label className="form-label">Tipo:</label>
          <select className="form-control" name="tipo" value={formData.tipo} onChange={handleChange} required>
            <option value="">Seleccione un tipo</option>
            <option value="PAGO">PAGO</option>
            <option value="TRANSFERENCIA">TRANSFERENCIA</option>
            <option value="RETIRO">RETIRO</option>
            <option value="DEPOSITO">DEPOSITO</option>
          </select>
          <small className="form-text text-muted">Escoger el tipo de transacción realizada.</small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label className="form-label">Usuario ID:</label>
          <input type="number" className="form-control" name="usuarioId" value={formData.usuarioId} onChange={handleChange} placeholder="Ejemplo: 1" required />
          <small className="form-text text-muted">Ingrese el ID del usuario.</small>
        </div>
        <div className="d-grid gap-2">
          <button type="submit" className="btn btn-primary btn-lg">Crear Ahorro</button>
        </div>
      </form>
      {/*---------------------------------------------------------------------------------------------*/}
      <div className="mt-3 d-grid gap-2">
        <button className="btn btn-dark btn-lg" onClick={() => navigate('/home/Client')}>Regresar a Operaciones del Cliente</button>
      </div>
    </div>
  );
};

export default createAhorro;