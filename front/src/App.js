import React, { useEffect, useState } from 'react'
import WelcomePage from './pages/WelcomePage/WelcomePage';
import ConnectingPage from './pages/ConnectingPage/ConnectingPage';
import ModerPage from './pages/Moderator/ModerPage';
import './App.css'
import axios from 'axios';
import consts from './consts.json';
import { useDispatch, useSelector } from 'react-redux';
import SuccessPage from './pages/SuccessErorrPages/SuccessPage';
import ErrorPage from './pages/SuccessErorrPages/ErrorPage';

function App() {

  const endpoint = consts.endpoint;
  const qlendpoint = endpoint + 'graphql';

  const [myRole, setMyRole] = useState('None');
  const [user, setUser] = useState({});
  const sentRequest = useSelector(state => state.waitingForServer);
  const showSuccess = useSelector(state => state.showingSuccessful);
  const error = useSelector(state => state.error);
  const reduxDispatch = useDispatch();

  function log_in(e) {
    e.preventDefault();
    reduxDispatch({type: "server/waiting"})
    axios.post(endpoint + "login", {
      username: 'Admin',
      password: 'TbJDt27H@3U'
    },
    {
      headers: {
        "content-type": "application/x-www-form-urlencoded",
      },
      withCredentials: true
    })
    
    .then((e) => { whoAmI();})
    .catch((e) => {});
  }

  function whoAmI() {
    axios.post(qlendpoint, {
      query: 
      `
      query 
      {  
        getMyRole 
        getMe {
          id
          firstName
          secondName
          thirdName
        }
      }
      `
    },
    {
      headers: {
        accept: "application/json",
        "content-type": "application/json",
      },
      withCredentials: true
    })
    .then((e) => {
      setMyRole(e.data.data.getMyRole);
      setUser(e.data.data.getMe);
      reduxDispatch({type: "server/gotResponse"})
    })
    .catch((e) => {
      setMyRole('None');
      reduxDispatch({type: "server/gotResponse"});
    });
  }
  
  function log_out() {
    console.log("Sent request2");
    axios.post(endpoint + "logout", { },
    {
      headers: {
        "content-type": "application/x-www-form-urlencoded"
      },
      withCredentials: true
    })
    .then((e) => {whoAmI()})
    .catch((e) => {console.log(e)});
  }

  useEffect(() => {
    reduxDispatch({type: "server/waiting"})
    whoAmI();
  }, []);

  return (
    <div className="App">
      {
      error.length !== 0 ?
      <ErrorPage/> : 
      ""
      }
      {
      showSuccess === 1 ?
      <SuccessPage/> : 
      ""
      }
      {
      sentRequest === 1 ?
      <ConnectingPage/> : 
      ""
      }
      {
        myRole === 'None' ?
        <WelcomePage whoAmI={whoAmI} endpoint={endpoint}/> :
        myRole === 'Administrator' ?
        <ModerPage user={user}/> :
        <h1>Nothing</h1>
      }
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
      <button onClick={log_in}>LOGIN AS ADMIN</button>
      <button onClick={log_out}>LOGOUT</button>
      <span>My role: {myRole}</span>
    </div>
  );
}

export default App;