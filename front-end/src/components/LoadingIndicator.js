import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSpinner} from '@fortawesome/free-solid-svg-icons'


const LoadingIndicator = () => (
    <div style={{
        height: '100vh',
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    }}>
        <FontAwesomeIcon
            icon={faSpinner}
            style={{
                fontSize: 100
            }}
            spin
        />
    </div>
);

export default LoadingIndicator