import React from "react";
import './css/popups.css';
import { useDispatch } from "react-redux";

const SuccessPage = (props) => {

    const reduxDispatch = useDispatch();

    function close() {
        reduxDispatch({type: "server/successOK"})
    }

    return (
        <div className="popup" onClick={close}>
            <div className="popup_content" onClick={(e) => {e.stopPropagation()}}>
                <h2>Успех!</h2>
                <button className="success" onClick={close}>ОК</button>
            </div>
        </div>
    )
};

export default SuccessPage;