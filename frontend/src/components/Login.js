import React from 'react'
import '../App.css';
import {useForm} from "react-hook-form";

function Login() {
  const{register,handleSubmit,formState:{error}}=useForm();
  const onSubmit=(data)=>console.log(data);
  return (
    <div>
        <p className='title'>Registration</p>
      <form className="App" onSubmit={handleSubmit(onSubmit)}>
        <input placeholder="Enter your name" type='text' {...register("name")}></input>
        <input placeholder="Enter your email" type="email" {...register("email",{required:true})}></input>
        <input placeholder="Enter your password" type="password"{...register("password")}></input>
        <input type={"submit"}></input>
      </form>
    </div>
  )
}

export default Login