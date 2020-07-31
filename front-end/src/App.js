import React, {createContext, useContext, useEffect, useState} from 'react';
import {Route, Switch, withRouter} from "react-router-dom";
import Home from "./pages/Home"
import Category from "./pages/Category"
import Newspaper from "./pages/Newspaper";
import ArticleManager from "./pages/ArticleManager";
import SignUp from "./pages/signup/SignUp";
import LogIn from "./pages/login/LogIn";
import {getCurrentUser} from "./util/APIUtils";
import {ACCESS_TOKEN} from "./constants";
import NotFound from "./pages/NotFound";
import PrivateRoute from "./components/PrivateRoute";
import AppNav from "./components/AppNav";
import LoadingIndicator from "./components/LoadingIndicator";
import Search from "./pages/Search";
import Profile from "./pages/Profile";
import Subscription from "./pages/subscription"

import './app.css'
import Favorites from "./pages/Favorites";
import ChangePassword from "./pages/ChangePassword";

const UserContext = createContext({});

export const useUser = () => useContext(UserContext);

function App({history}) {
    const [user, setUser] = useState(null);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
            loadCurrentUser()
        },
        // eslint-disable-next-line
        [] // no sacar
    );

    async function loadCurrentUser() {
        setIsLoading(true);
        getCurrentUser()
            .then(user => {
                console.log({user});
                setUser(user);
                setIsAuthenticated(true);
                setIsLoading(false)
            })
            .catch(error => {
                console.log({loadCurrentUser: 'fail', error});
                console.log('error');
                handleLogout();
                setIsLoading(false)
            })
    }

    function handleLogout(redirectTo = "/") {
        localStorage.removeItem(ACCESS_TOKEN);

        setUser(null);
        setIsAuthenticated(false);

        history.push(redirectTo);
    }

    function handleLogin(redirectTo = "/") {
        loadCurrentUser()
            .then(() => {
                console.log('redirecting to ' + redirectTo)
                history.push(redirectTo);
            })
            .catch(error => console.log({handleLogin: 'true', error}))
    }


    if (isLoading)
        return <LoadingIndicator/>;

    const isActive = user?.active ?? false

    console.log({isActive})

    return (
        <>
            <UserContext.Provider value={{currentUser: user, updateCurrentUser: setUser}}>

                <AppNav onLogout={handleLogout} isAuthenticated={isAuthenticated}/>

                <Switch>
                    <PrivateRoute isActive={isActive} authenticated={isAuthenticated} path='/' exact={true}
                                  component={Home}/>
                    <PrivateRoute isActive={isActive} authenticated={isAuthenticated} path='/search'
                                  exact={true} component={Search}/>
                    <PrivateRoute isActive={isActive} authenticated={isAuthenticated} path='/categories/:name'
                                  exact={true} component={Category}/>
                    <PrivateRoute isActive={isActive} authenticated={isAuthenticated} path='/newspapers/:name'
                                  exact={true}
                                  component={Newspaper}/>
                    <PrivateRoute isActive={true} authenticated={isAuthenticated} path='/subscription'
                                  exact={true}
                                  component={Subscription}/>
                    <PrivateRoute isActive={isActive} authenticated={isAuthenticated} path='/articles/:id'
                                  exact={true}
                                  component={ArticleManager}/>
                    <PrivateRoute isActive={true} authenticated={isAuthenticated} exact path="/profile"
                                  component={Profile}/>
                    <PrivateRoute isActive={isActive} authenticated={isAuthenticated} exact path="/favorites"
                                  component={Favorites}/>
                    <PrivateRoute isActive={true} authenticated={isAuthenticated} exact
                                  path="/profile/changepassword"
                                  component={ChangePassword}/>
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
