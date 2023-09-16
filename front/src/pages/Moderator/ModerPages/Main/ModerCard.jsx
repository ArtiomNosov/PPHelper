import React from "react";
import './../../css/moder.css'

const ModerCard = (props) => {
    return (
        <div className="card">
            <p>{props.text}</p>
        </div>
    )
};

export default ModerCard;