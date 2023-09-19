import axios from "axios";
import {useNavigate} from 'react-router-dom';

const BASE_URL = "http://localhost:8080";


const TransactionHistoryService = {

    async getTransactions(accNo, config){
        try{
            const response=await axios.get(`${BASE_URL}/getTransactions/`+accNo,config);
            return response.data;
        } catch(error){
            if(error.response && error.response.status===401){
                console.log("401: Unauthorized access");
            } else {
                console.log("An error occured");
            }
            return error.response;
        }
    }

};


export {TransactionHistoryService};

