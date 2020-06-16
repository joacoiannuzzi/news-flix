import React from 'react';
import {
    Route,
    Redirect
} from "react-router-dom";


const PrivateRoute = ({component: Component, authenticated, ...rest}) => {
    if (!authenticated) {
        return (
            <Redirect to={{
                pathname: '/login',
                // state: {from: props.location}
            }}/>
        )
    }

    return <Route {...rest} render={props => <Component {...rest} {...props} />}/>

}

export default PrivateRoute