import React, {Component, createContext} from 'react';
import {Route, Switch, withRouter} from "react-router-dom";
import Home from "./pages/Home"
import Category from "./pages/Category"
import Newspaper from "./pages/Newspaper";
import ArticleManager from "./pages/ArticleManager";
import SignUp from "./pages/SignUp";
import Login from "./pages/login/Login";
import {getCurrentUser} from "./util/APIUtils";
import {ACCESS_TOKEN} from "./constants";
import NotFound from "./pages/NotFound";
import PrivateRoute from "./components/PrivateRoute";
import AppNav from "./components/AppNav";
import LoadingIndicator from "./components/LoadingIndicator";
import Search from "./pages/Search";
import Profile from "./pages/Profile";

import './app.css'

export const UserContext = createContext()

class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            currentUser: null,
            isAuthenticated: false,
            isLoading: true
        }
    }

    loadCurrentUser = async () => {
        this.setState({
            isLoading: true
        });
        await getCurrentUser()
            .then(response => {
                this.setState({
                    currentUser: response,
                    isAuthenticated: true,
                    isLoading: false
                });
            }).catch(error => {
                console.log(error);
                this.handleLogout();
                this.setState({
                    isLoading: false
                });
            })
    };

    componentDidMount() {
        this.loadCurrentUser();
    }

    handleLogout = (redirectTo = "/") => {
        localStorage.removeItem(ACCESS_TOKEN);

        this.setState({
            currentUser: null,
            isAuthenticated: false
        });

        this.props.history.push(redirectTo);
    };

    handleLogin = (redirectTo = "/") => {
        this.loadCurrentUser().then(r => {
            this.props.history.push(redirectTo);
        });

    };

    updateCurrentUser = user => this.setState({currentUser: user});

    render() {
        const {isLoading, isAuthenticated, currentUser} = this.state;

        if (isLoading)
            return <LoadingIndicator/>;

        return (
            <>
                <UserContext.Provider value={{currentUser, updateCurrentUser: this.updateCurrentUser}}>

                    <AppNav
                        isAuthenticated={isAuthenticated}
                        currentUser={currentUser}
                        onLogout={this.handleLogout}/>

                    <Switch>
                        <PrivateRoute authenticated={isAuthenticated} currentUser={currentUser} path='/' exact={true}
                                      component={Home}/>
                        <PrivateRoute authenticated={isAuthenticated} currentUser={currentUser} path='/search'
                                      exact={true} component={Search}/>
                        <PrivateRoute authenticated={isAuthenticated} currentUser={currentUser} path='/categories/:name'
                                      exact={true}
                                      component={Category}/>
                        <PrivateRoute authenticated={isAuthenticated} currentUser={currentUser} path='/newspapers/:name'
                                      exact={true}
                                      component={Newspaper}/>
                        <PrivateRoute authenticated={isAuthenticated} currentUser={currentUser} path='/articles/:id'
                                      exact={true}
                                      component={ArticleManager}/>
                        <PrivateRoute authenticated={isAuthenticated} currentUser={currentUser} exact path="/profile"
                                      component={Profile}/>
                        <Route exact={true} path='/signup' component={SignUp}/>
                        <Route exact={true} path="/login"
                               render={props => <Login onLogin={this.handleLogin} {...props} />}/>


                        {/*este va al final*/}
                        <Route component={NotFound}/>
                    </Switch>
                </UserContext.Provider>
            </>
        )
    }
}

export default withRouter(App);
