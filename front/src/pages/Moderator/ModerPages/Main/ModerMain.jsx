import React from "react";
import ModerCards from "./ModerCards";
import ModerHeader from "../ModerHeader";

const ModerMain = (props) => {

    return (
        <div className="moder">
            <ModerHeader user={props.user} toggle_disabled={true}/>
            <ModerCards/>
        </div>
    )
};

export default ModerMain;