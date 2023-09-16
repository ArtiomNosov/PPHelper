import React, { useState } from "react";
import "./css/ModerParticipants.css";
import CloseButton from "../../../../UI/CloseButton";
import InputField from "../../../../UI/InputField";
import axios from "axios";
import consts from './../../../../consts.json';
import { useDispatch, useSelector } from "react-redux";

const CreateParticipantForm = ({closeFunc}) => {

    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [secondName, setSecondName] = useState("");
    const [thirdName, setThirdName] = useState("");
    const [urlVK, setUrlVK] = useState("");
    const [urlTG, setUrlTG] = useState("");
    const [group, setGroup] = useState("");
    const reduxDispatch = useDispatch();

    const state = useSelector(state => state)
    

    function createParticipant_send(e) {
      e.preventDefault();
      reduxDispatch({type: "server/waiting"});
      axios.post(consts.qlendpoint, {
          query: 
          `
          mutation {
              registerParticipant (credentialsInput: {
                login: "` + login + `"
                password: "` + password + `"
              }, userInput: {
                firstName: "` + firstName + `"
                secondName: "` + secondName + `"
                thirdName: "` + thirdName + `"
                urlVkontakte: "` + urlVK + `"
                urlTelegram: "` + urlTG + `"
                group: "` + group + `"
                organization: "НИЯУ МИФИ"
              })
              {
                id
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
          reduxDispatch({type: "server/gotResponse"});
          //let er = e.data.errors[0].message;
          if (e.data.errors === undefined) {
            reduxDispatch({type: "server/success"});
          }
          else {
            reduxDispatch({type: "server/error", payload: e.data.errors[0].message});
            console.log(1)
          }
        })
        .catch((e) => {
          reduxDispatch({type: "server/gotResponse"});
        });
    }
    
    return (
        <form className="create_participant_form" onSubmit={createParticipant_send}>
            <div style={{display: 'flex', flexDirection: 'row', justifyContent: "space-between", alignItems: "center"}}>
                <h3>Регистрация участника</h3>
                <CloseButton closeFunc={closeFunc}/>
            </div>
            Логин
            <InputField stateName="login" state={login} setState={setLogin} required={'True'}/>
            Пароль
            <InputField stateName="password" state={password} setState={setPassword} required={'True'}/>
            Фамилия
            <InputField stateName="Иванов" state={secondName} setState={setSecondName} required={'True'}/>
            Имя
            <InputField stateName="Иван" state={firstName} setState={setFirstName} required={'True'}/>
            Отчество
            <InputField stateName="Иванович" state={thirdName} setState={setThirdName} />
            Группа
            <InputField stateName="Б22-504" state={group} setState={setGroup} required={'True'}/>
            Vk
            <InputField stateName="vk.com/ivanov337123" state={urlVK} setState={setUrlVK} />
            Telegram
            <InputField stateName="@vanya337123" state={urlTG} setState={setUrlTG} />
            <button type="submit">Создать участника!</button>
        </form>
    )
};

//query: "mutation {\n  registerParticipant (credentialsInput: {\n    login: \"login\"\n    password: \"password\"\n  }, userInput: {\n    firstName: String!\n    secondName: String!\n    thirdName: String!\n    urlVkontakte: String!\n    urlTelegram: String!\n    group: String!\n    organization: String!\n  })\n}"
export default CreateParticipantForm;