import React, {Component} from 'react';
import {Route, BrowserRouter as Router, Switch} from "react-router-dom";
import Home from "./pages/Home"
import Category from "./pages/Category"
import Newspaper from "./pages/Newspaper";
import Article from "./pages/Article";
import SignUp from "./pages/SignUp";
import Login from "./pages/Login";

class App extends Component {
    state = {};

    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/categories/:name' exact={true} component={Category}/>
                    <Route path='/newspapers/:name' exact={true} component={Newspaper}/>
                    <Route path='/articles/:id' exact={true} component={Article}/>
                    <Route path='/signup' exact={true} component={SignUp}/>
                    <Route path='/login' exact={true} component={Login}/>
                </Switch>
            </Router>
        );
    }
}

export default App;
