import axios from "axios";
import {useNavigate} from 'react-router-dom';

const BASE_URL = "http://localhost:8080";


const AccountService = {

    async openAccount(userData, config){
        try{
            const response=await axios.post(`${BASE_URL}/openAccount`, userData, config);
            return response.data;
        } catch(error){
            if(error.response && error.response.status===401){
                console.log("401: Unauthorized access");
            } else {
                console.log("An error occured");
            }
            return error.response;
        }
    },

    async checkBalance(userData, config){
        try{
            const response=await axios.post(`${BASE_URL}/checkBalance`, userData, config);
            return response.data;
        } catch(error){
            if(error.response && error.response.status===401){
                console.log("401: Unauthorized access");
            } else {
                console.log("An error occured");
            }
            return error.response;
        }
    },
    async getAllAccounts(config){
        try{
            const response=await axios.post(`${BASE_URL}/getAccountsByUser`, null,config);
            return response;
        } catch(error){
            if(error.response && error.response.status===401){
                console.log("401: Unauthorized access");
            } else {
                console.log("An error occured");
            }
            return error.response;
        }
    },
    async depositMoney(userData, config){
        try{
            const response=await axios.post(`${BASE_URL}/depositMoney`, userData, config);
            console.log("in service - ",response);
            return response.data;
        } catch(error){
            if(error.response && error.response.status===401){
                console.log("401: Unauthorized access");
            } else {
                console.log("An error occured");
            }
            return error.response;
        }
    },
    async withdrawMoney(userData, config){
        try{
            const response=await axios.post(`${BASE_URL}/withdrawMoney`, userData, config);
            console.log("in service - ",response);
            return response.data;
        } catch(error){
            if(error.response && error.response.status===401){
                console.log("401: Unauthorized access");
            } else {
                console.log("An error occured");
            }
            return error.response;
        }
    },

};


export {AccountService};

