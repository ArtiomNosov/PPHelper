import React from "react";
import './UI.css'

const CreationForm = ({children, visible, setVisible}) => {
    
    return (
        <div className={"creationForm" + (visible ? " active" : "")} onClick={() => {setVisible(0)}}>
            <div className="creationForm_content" onClick={(e) => {e.stopPropagation()}}>
                {children}
            </div>
        </div>
    )
};

export default CreationForm;