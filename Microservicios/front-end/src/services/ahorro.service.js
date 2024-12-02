import httpClient from "../http-common";

//--------------------------------------------------------------------//
const getAll = () => {
    return httpClient.get('/ahorro');
}
//--------------------------------------------------------------------//
const create = data => {
    return httpClient.post("/ahorro", data);
}
//--------------------------------------------------------------------//
const get = id => {
    return httpClient.get(`/ahorro/${id}`);
}
//--------------------------------------------------------------------//
const update = data => {
    return httpClient.put('/ahorro/update', data); // Assuming you have an update endpoint
}
//--------------------------------------------------------------------//
const remove = id => {
    return httpClient.delete(`/ahorro/${id}`);
}
//--------------------------------------------------------------------//
const getByUsuarioId = usuarioId => {
    return httpClient.get(`/ahorro/byusuario/${usuarioId}`);
}
//--------------------------------------------------------------------//
const createAhorro = (transaccion, tipo, usuarioId) => {
    return httpClient.post(`/ahorro/create`, null, {
        params: {
            transaccion,
            tipo,
            usuarioId
        }
    });
}
//--------------------------------------------------------------------//
const getValorPositivoMasPequeno = usuarioId => {
    return httpClient.get(`/ahorro/valorpositivomaspequeno/${usuarioId}`);
}
//--------------------------------------------------------------------//
export default { getAll, create, get, update, remove, getByUsuarioId, createAhorro, getValorPositivoMasPequeno };