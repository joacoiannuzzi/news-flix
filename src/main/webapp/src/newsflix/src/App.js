import React, {Component} from 'react';
import {Route, Switch, withRouter} from "react-router-dom";
import Home from "./pages/Home"
import Category from "./pages/Category"
import Newspaper from "./pages/Newspaper";
import Article from "./pages/Article";
import SignUp from "./pages/SignUp";
import Login from "./pages/Login";
import {getCurrentUser} from "./util/APIUtils";
import {ACCESS_TOKEN} from "./constants";
import NotFound from "./pages/NotFound";
import PrivateRoute from "./components/PrivateRoute";
import AppNav from "./components/AppNav";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
            isAuthenticated: false,
            isLoading: false
        }
    }

    loadCurrentUser = () => {
        this.setState({
            isLoading: true
        });
        getCurrentUser()
            .then(response => {
                console.log(response)
                this.setState({
                    currentUser: response,
                    isAuthenticated: true,
                    isLoading: false
                });
            }).catch(error => {
            console.log(error)
            this.setState({
                isLoading: false
            });
        });
    }

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

    handleLogin = () => {
        this.loadCurrentUser();
        this.props.history.push("/");
    };

    render() {
        return (
            <>
                <AppNav
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    onLogout={this.handleLogout}/>

                <Switch>
                    <PrivateRoute authenticated={this.state.isAuthenticated} path='/' exact={true} component={Home}/>
                    <PrivateRoute authenticated={this.state.isAuthenticated} path='/categories/:name' exact={true}
                                  component={Category}/>
                    <PrivateRoute authenticated={this.state.isAuthenticated} path='/newspapers/:name' exact={true}
                                  component={Newspaper}/>
                    <PrivateRoute authenticated={this.state.isAuthenticated} path='/articles/:id' exact
                                  component={Article}/>
                    <Route path="/login"
                           render={props => <Login onLogin={this.handleLogin} {...props} />}/>
                    <Route path='/signup' exact={true} component={SignUp}/>


                    {/*este va al final*/}
                    <Route component={NotFound}/>
                </Switch>
            </>
        )
    }
}

export default withRouter(App);
