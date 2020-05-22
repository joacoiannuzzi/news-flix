import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSpinner} from '@fortawesome/free-solid-svg-icons'


export default function LoadingIndicator(props) {

    return <FontAwesomeIcon
        icon={faSpinner}
        style={{
            fontSize: 30,
            display: 'block',
            textAlign: 'center',
            marginTop: 30
        }}
        spin
    />
}