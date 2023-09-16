import React from "react";
import './css/popups.css';
import { useDispatch, useSelector } from "react-redux";

const ErrorPage = () => {

    const reduxDispatch = useDispatch();
    const errorMessage = useSelector(state => state.error)

    function close() {
        reduxDispatch({type: "server/errorOK"})
    }

    return (
        <div className="popup" onClick={close}>
            <div className="popup_content" onClick={(e) => {e.stopPropagation()}}>
                <h2>Ошибка</h2>
                <p>{errorMessage}</p>
                <button className="error" onClick={close}>ОК</button>
            </div>
        </div>
    )
};

export default ErrorPage;