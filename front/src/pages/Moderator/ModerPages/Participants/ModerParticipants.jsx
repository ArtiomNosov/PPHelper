import React from "react";
import ModerHeader from "../ModerHeader";
import Participants from "./Participants";

const ModerParticipants = (props) => {
    
    return (
        <div className="moderParticipants">
            <ModerHeader user={props.user} toggle_disabled={false}/>
            <Participants/>
        </div>
    )
};

export default ModerParticipants;