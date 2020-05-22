import React, {Component} from "react";
import {Link} from "react-router-dom";
import {login} from "../../util/APIUtils";
import {ACCESS_TOKEN} from "../../constants";

import './login.css'
import avatar from '../../media/avatar.svg'
import manReading from '../../media/man-reading-news.svg'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAt, faLock} from '@fortawesome/free-solid-svg-icons'

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
        }
    }


    addFocus = (event) => {
        let parent = event.target.parentNode.parentNode;
        parent.classList.add("focus");
    };

    removeFocus = (event) => {
        let parent = event.target.parentNode.parentNode;
        if (event.target.value === "") {
            parent.classList.remove("focus");
        }
    }

    handleChange = event => {
        const isCheckbox = event.target.type === 'checkbox';
        this.setState({
            [event.target.name]: isCheckbox ?
                event.target.checked :
                event.target.value
        })
    };

    handleSubmit = event => {
        event.preventDefault();
        const loginRequest = this.state
        login(loginRequest)
            .then(response => {
                localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                this.props.onLogin();
            }).catch(error => {
            console.log(error)
        })
    };

    render() {
        const {password, email} = this.state;
        return (
            <>
                {/*<img className="wave" src="img/wave.png">*/}
                <div className="cont">
                    <div className="img">
                        <img src={manReading} alt=''/>
                    </div>
                    <div className="login-content">
                        <form onSubmit={this.handleSubmit}>
                            <img src={avatar} alt=''/>
                            <h2 className="title">Bienvenido</h2>
                            <div className="input-div one">
                                <div className="i">
                                    <FontAwesomeIcon icon={faAt}/>
                                </div>
                                <div className="div">
                                    <h5>Correo Electronico</h5>
                                    <input name={'email'} value={email} type="email" className="input"
                                           onChange={this.handleChange}
                                           onFocus={this.addFocus} onBlur={this.removeFocus}
                                    />
                                </div>
                            </div>
                            <div className="input-div pass">
                                <div className="i">
                                    <FontAwesomeIcon icon={faLock}/>

                                </div>
                                <div className="div">
                                    <h5>Contraseña</h5>
                                    <input name={'password'} value={password} type="password" className="input"
                                           onChange={this.handleChange}
                                           onFocus={this.addFocus} onBlur={this.removeFocus}
                                    />
                                </div>
                            </div>
                            <Link to="#" className='a'>¿Olvidaste tu contraseña?</Link>
                            <input type="submit" className="button"/>
                            <Link to='/signup'>Registrarse</Link>
                        </form>
                    </div>
                </div>
            </>
        )
    }

}

export default Login
