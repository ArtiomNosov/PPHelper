import React, { useState } from "react";
import './css/ModerParticipants.css';
import CreationForm from "../../../../UI/CreationForm";
import CreateParticipantForm from "./CreateParticipantForm";

const Participants = (props) => {

    const [createParticipantForm_visible, setCreateParticipantForm_visible] = useState(0)

    return (
        <div>
            <div className="control_participants">
                <input></input>
                <button onClick={() => {setCreateParticipantForm_visible(1)}}>Создать участника</button>
                <CreationForm visible={createParticipantForm_visible} setVisible={setCreateParticipantForm_visible}>
                    <CreateParticipantForm closeFunc={()=>{setCreateParticipantForm_visible(0)}}/>
                </CreationForm>
            </div>
        </div>
    )
};

export default Participants;