import React, {createContext, useContext, useEffect, useState} from 'react';
import {Route, Switch, withRouter} from "react-router-dom";
import Home from "./pages/Home"
import Category from "./pages/Category"
import Newspaper from "./pages/Newspaper";
import ArticleManager from "./pages/ArticleManager";
import SignUp from "./pages/SignUp";
import LogIn from "./pages/login/LogIn";
import {getCurrentUser} from "./util/APIUtils";
import {ACCESS_TOKEN} from "./constants";
import NotFound from "./pages/NotFound";
import PrivateRoute from "./components/PrivateRoute";
import AppNav from "./components/AppNav";
import LoadingIndicator from "./components/LoadingIndicator";
import Search from "./pages/Search";
import Profile from "./pages/Profile";

import './app.css'
import Favorites from "./pages/Favorites";

const UserContext = createContext({})

export const useUser = () => useContext(UserContext)

function App({history}) {
    const [user, setUser] = useState(null)
    const [isAuthenticated, setIsAuthenticated] = useState(false)
    const [isLoading, setIsLoading] = useState(true)

    useEffect(
        () => {
            loadCurrentUser()
        },
        // eslint-disable-next-line
        [] // no sacar
    )

    async function loadCurrentUser() {
        setIsLoading(true)
        getCurrentUser()
            .then(user => {
                setUser(user)
                setIsAuthenticated(true)
                setIsLoading(false)
            })
            .catch(error => {
                console.log(error)
                console.log('error')
                handleLogout()
                setIsLoading(false)
            })
    }

    function handleLogout(redirectTo = "/") {
        localStorage.removeItem(ACCESS_TOKEN);

        setUser(null)
        setIsAuthenticated(false)

        history.push(redirectTo);
    }

    function handleLogin(redirectTo = "/") {
        loadCurrentUser()
            .then(() => {
                history.push(redirectTo);
            })
    }


    if (isLoading)
        return <LoadingIndicator/>

    return (
        <>
            <UserContext.Provider value={{currentUser: user, updateCurrentUser: setUser}}>

                <AppNav onLogout={handleLogout} isAuthenticated={isAuthenticated}/>

                <Switch>
                    <PrivateRoute authenticated={isAuthenticated} path='/' exact={true}
                                  component={Home}/>
                    <PrivateRoute authenticated={isAuthenticated} path='/search'
                                  exact={true} component={Search}/>
                    <PrivateRoute authenticated={isAuthenticated} path='/categories/:name'
                                  exact={true} component={Category}/>
                    <PrivateRoute authenticated={isAuthenticated} path='/newspapers/:name'
                                  exact={true}
                                  component={Newspaper}/>
                    <PrivateRoute authenticated={isAuthenticated} path='/articles/:id'
                                  exact={true}
                                  component={ArticleManager}/>
                    <PrivateRoute authenticated={isAuthenticated} exact path="/profile"
                                  component={Profile}/><
                    PrivateRoute authenticated={isAuthenticated} exact path="/favorites"
                                 component={Favorites}/>
                    <Route exact={true} path='/signup' component={SignUp}/>
                    <Route exact={true} path="/login"
                           render={props => <LogIn onLogin={handleLogin} {...props} />}
                    />

                    {/*este va al final*/}
                    <Route component={NotFound}/>
                </Switch>
            </UserContext.Provider>
        </>
    )

}

export default withRouter(App);
