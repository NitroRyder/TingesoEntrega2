import httpClient from "../http-common";

//--------------------------------------------------------------------//
const getAll = () => {
    return httpClient.get('/credito');
};
//--------------------------------------------------------------------//
const getById = (id) => {
    return httpClient.get(`/credito/${id}`);
};
//--------------------------------------------------------------------//
const saveCredito = (credito) => {
    return httpClient.post('/credito/save', credito);
};
//--------------------------------------------------------------------//
const getByUsuarioId = (usuarioId) => {
    return httpClient.get(`/credito/byusuario/${usuarioId}`);
};
//--------------------------------------------------------------------//
const deleteCredito = (id) => {
    return httpClient.delete(`/credito/delete/${id}`);
};
//--------------------------------------------------------------------//
const getDocumentoByCreditoId = (creditoId) => {
    return httpClient.get(`/credito/documento/${creditoId}`);
};
//--------------------------------------------------------------------//
const registrarCredito = (credito) => {
    return httpClient.post('/credito/registrar', credito);
};
//--------------------------------------------------------------------//
export default {getAll,getById,saveCredito,getByUsuarioId,deleteCredito,getDocumentoByCreditoId,registrarCredito};