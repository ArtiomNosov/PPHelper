import React from "react";
import './css/moder.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ModerMain from "./ModerPages/Main/ModerMain";
import ModerParticipants from "./ModerPages/Participants/ModerParticipants";

const ModerPage = (props) => {

    return (
        <div className="moder">
            <BrowserRouter>
                <Routes>
                    <Route exact path="/" element={<ModerMain user={props.user}/>} />
                    <Route exact path="/participants" element={<ModerParticipants user={props.user}/>} />
                </Routes>
            </BrowserRouter>
        </div>
    )
};

export default ModerPage;