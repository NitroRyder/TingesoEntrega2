import httpClient from "../http-common";

//--------------------------------------------------------------------//
const getAll = () => {
    return httpClient.get('/documentos');
};
//--------------------------------------------------------------------//
const getById = (id) => {
    return httpClient.get(`/documentos/${id}`);
};
//--------------------------------------------------------------------//
const saveDocumentos = (documentos) => {
    return httpClient.post('/documentos/save', documentos);
};
//--------------------------------------------------------------------//
const getByCreditoId = (creditoId) => {
    return httpClient.get(`/documentos/bycredito/${creditoId}`);
};
//--------------------------------------------------------------------//
const deleteDocumentos = (id) => {
    return httpClient.delete(`/documentos/delete/${id}`);
};
//--------------------------------------------------------------------//
const registrarDocumentos = (creditoId, documento) => {
    const formData = new FormData();
    formData.append('creditoId', creditoId);
    formData.append('documento', documento);

    return httpClient.post('/documentos/registrar', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
};
//--------------------------------------------------------------------//
export default {getAll,getById,saveDocumentos,getByCreditoId,deleteDocumentos,registrarDocumentos};