import React from "react";
import './UI.css';
import logoutSVG from './../images/logout.svg';
import axios from "axios";
import consts from './../consts.json';
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";

const LogoutEl = () => {

    const reduxDispatch = useDispatch();

    function logout() {
      reduxDispatch({type: "server/waiting"});
      axios.post(consts.endpoint + "logout", { },
      {
        headers: {
          "content-type": "application/x-www-form-urlencoded"
        },
        withCredentials: true
      })
      .then((e) => {window.location.reload();})
      .catch((e) => {console.log(e)});
      }

    return (
        <Link to="/" style={{textDecoration: "none", outline: "0"}}>
          <div className='logout' onClick={logout}>
              <img src={logoutSVG} alt="logout" style={{height: '25px'}}/>
          </div>
        </Link>
    )
};

export default LogoutEl;