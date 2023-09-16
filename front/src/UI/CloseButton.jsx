import React from "react";
import './UI.css'
import closeSVG from './../images/close.svg';

const CloseButton = ({closeFunc}) => {

    return (
        <div className="close_button" onClick={closeFunc}>
            <img src={closeSVG} alt="close" style={{height: '100%'}}/>
        </div>
    )
};

export default CloseButton;