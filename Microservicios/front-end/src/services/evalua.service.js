import httpClient from "../http-common";

//--------------------------------------------------------------------//
const evaluateCredito = (userId, creditId) => {
    return httpClient.post('/evalua/evaluateCredito', null, {
        params: { userId, creditId }
    });
};
//--------------------------------------------------------------------//
const updateState = (userId, creditId, state) => {
    return httpClient.post('/evalua/updateState', null, {
        params: { userId, creditId, state }
    });
};
//--------------------------------------------------------------------//
export default {evaluateCredito,updateState};