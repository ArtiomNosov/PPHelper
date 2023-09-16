import React, { useState } from "react";
import InputField from "../../UI/InputField";
import axios from "axios";

import logo from './../../images/logo1.png';
import './css/welcome.css';
import { useDispatch } from "react-redux";

const LoginForm = (props) => {
    const [login, setLogin] = useState('');
    const [pwd, setPwd] = useState('');

    const reduxDispatch = useDispatch();

    function logIn(e) {
        e.preventDefault();
        reduxDispatch({type: "server/waiting"});
        axios.post(props.endpoint + "login", {
          username: login,
          password: pwd
        },
        {
          headers: {
            "content-type": "application/x-www-form-urlencoded",
          },
          withCredentials: true
        })
        .then((e) => {props.whoAmI()})
        .catch((e) => {reduxDispatch({type: "server/gotResponse"});});
        }

    return (
        <form className="login" onSubmit={logIn}>
            <div className="form__image">
                <img alt="codenerve.com" src={logo} style={{width: '250px', height: '250px'}}/>
            </div>
            <div className="loginField">
                <label htmlFor="welcomeLogin">
                    <svg className='loginIcon' xmlns="http://www.w3.org/2000/svg" height="48" viewBox="0 96 960 960" width="48">
                        <path d="M489 936v-60h291V276H489v-60h291q24 0 42 18t18 42v600q0 24-18 42t-42 18H489Zm-78-185-43-43 102-102H120v-60h348L366 444l43-43 176 176-174 174Z"/>
                    </svg>
                <span hidden>Логин</span></label>
                <InputField state={login} setState={setLogin} stateName='Логин' id='welcomeLogin' required='True' type='text'/>
            </div>
            <div className="loginField">
                <label htmlFor="welcomePwd">
                    <svg className='loginIcon' xmlns="http://www.w3.org/2000/svg" height="48" viewBox="0 96 960 960" width="48">
                        <path d="M220 976q-24.75 0-42.375-17.625T160 916V482q0-24.75 17.625-42.375T220 422h70v-96q0-78.85 55.606-134.425Q401.212 136 480.106 136T614.5 191.575Q670 247.15 670 326v96h70q24.75 0 42.375 17.625T800 482v434q0 24.75-17.625 42.375T740 976H220Zm0-60h520V482H220v434Zm260.168-140Q512 776 534.5 753.969T557 701q0-30-22.668-54.5t-54.5-24.5Q448 622 425.5 646.5t-22.5 55q0 30.5 22.668 52.5t54.5 22ZM350 422h260v-96q0-54.167-37.882-92.083-37.883-37.917-92-37.917Q426 196 388 233.917 350 271.833 350 326v96ZM220 916V482v434Z"/>
                    </svg>
                <span hidden>Пароль</span></label>
                <InputField state={pwd} setState={setPwd} stateName='Пароль' id='welcomePwd' required='True' type='password'/>
            </div>
            <div className="text--center">
                <input id="login__checkbox" name="remember-me" type="checkbox"/>
                <span>Запомнить меня</span>
            </div>
            <div className="formField">
                <input id='welcomeSubmit' type="submit" value="Войти"/>
            </div>
            <p className="text--center">
                Проектная практика <a href="https://goit.mephi.ru">ИИКС</a>
            </p>
        </form>
    )
};

export default LoginForm;