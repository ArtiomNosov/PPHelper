import React, { useState } from "react";
import ModerCards from "./Main/ModerCards";
import LogoutEl from "./../../../UI/LogoutEl";
import MenuEl from "../../../UI/MenuEl";

const ModerHeader = (props) => {

    const [headerShown, setHeaderShown] = useState(0)

    function headerToggle() {
        setHeaderShown(1 - headerShown);
    }

    return (
        <header id="ModerHeader">
            <div className="HeadeControls">
                <MenuEl callbackf={headerToggle} toggle_disabled={props.toggle_disabled}/>
                <div className="leavebtns">
                    <span>{props.user.secondName} {props.user.firstName[0]}. {props.user.thirdName[0]}.</span>
                    <LogoutEl/>
                </div>
            </div>
            <div className="headerNavigation" style={(headerShown === 0) ? {display: 'none'} : {display: 'flex'}}>
                <ModerCards/>
            </div>
        </header>
    )
};

export default ModerHeader;