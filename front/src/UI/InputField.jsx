import React from "react";
import './UI.css'

const InputField = (props) => {
    //props must contains state: text element and its change-function, it also contains placeholder (name of the element)


    return (
    <input className="inputField" placeholder={props.stateName} value={props.state} id={props.id} required={props.required} type={props.type}
            onChange={(e) => {props.setState(e.target.value)}}>
                
            </input>
    )
};

export default InputField;