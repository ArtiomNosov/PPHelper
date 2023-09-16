import React from "react";
import './../../css/moder.css'
import ModerCard from "./ModerCard";
import { Link } from "react-router-dom";


const ModerCards = (props) => {
    return (
        <div className="grid_cards">
            <Link to="/participants" style={{textDecoration: "none", outline: "0"}}>
                <ModerCard text="Участники"/>
            </Link>
            <Link to="/" style={{textDecoration: "none", outline: "0"}}>
                <ModerCard text="Менторы"/>
            </Link>
            <Link to="/" style={{textDecoration: "none", outline: "0"}}>
                <ModerCard text="Команды"/>
            </Link>
            <Link to="/" style={{textDecoration: "none", outline: "0"}}>
                <ModerCard text="Темы"/>
            </Link>
            <Link to="/" style={{textDecoration: "none", outline: "0"}}>
                <ModerCard text="Отчеты"/>
            </Link>
        </div>
    )
};

export default ModerCards;