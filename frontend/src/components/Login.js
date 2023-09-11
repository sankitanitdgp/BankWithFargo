import React from 'react'
import '../App.css';
import '../../src/styles/Login.css'
import {useForm} from "react-hook-form";
import UserService from '../service/UserService';

function Login() {
  const{register,handleSubmit,formState:{error}}=useForm();
  const onSubmit=(data)=>{
    console.log(data);
    UserService.login({
      email: data.email,
      password: data.password
    }).then((data)=> console.log(data))
      .catch((error)=>console.log(error));
  }
  return (
    <div>
        <p className='title'>Login/Sign-up</p>
      <form className="App" onSubmit={handleSubmit(onSubmit)}>
        <input placeholder="Enter your email" type="email" {...register("email",{required:true})}></input>
        <input placeholder="Enter your password" type="password"{...register("password")}></input>
        <input placeholder="Login" className='submit' type={"submit"}></input>
        <div>
        <span class="psw">First-time user? <a href="#">Sign-up</a></span>
        <span class="psw">Forgot <a href="#">password?</a></span>
        </div>
      </form>
    </div>
  )
}

export default Login