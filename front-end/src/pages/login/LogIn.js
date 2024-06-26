import React from "react";
import {Link} from "react-router-dom";
import {login} from "../../util/APIUtils";
import {ACCESS_TOKEN} from "../../constants";

import './login.css'
import avatar from '../../media/avatar.svg'
import manReading from '../../media/man-reading-news.svg'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAt, faLock} from '@fortawesome/free-solid-svg-icons'
import useFormInput from "../../components/hooks/useFormInput";

const addFocus = ({target}) => {
    const parent = target.parentNode.parentNode;
    parent.classList.add("focus");
};
const removeFocus = ({target}) => {
    const {parentNode, value} = target;
    const parent = parentNode.parentNode;
    if (value === "") {
        parent.classList.remove("focus");
    }
};

const LogIn = ({onLogin}) => {
    const email = useFormInput('');
    const password = useFormInput('');

    const handleSubmit = event => {
        event.preventDefault();
        const logInRequest = {
            email: email.value,
            password: password.value
        };
        login(logInRequest)
            .then(response => {
                console.log({login: 'login', response})
                localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                onLogin();
            })
            .catch(error => {
            console.log({error})
            alert('Invalido');
        })
    };

    return (
        <>
            <div style={{
                marginTop: 100,
                // border: '1px solid black',
                width: '100%',
                display: "flex",
                // alignContent: "center",
                justifyContent: "center",
                alignItems: "center"
            }}>
                <h1 className='display-1'>NEWS FLIX</h1>
            </div>
            <div className="cont">
                <div className="img">
                    <img src={manReading} alt=''/>
                </div>
                <div className="login-content">
                    <form className=".form" onSubmit={handleSubmit}>
                        <img src={avatar} alt=''/>
                        <h2 className="title">Bienvenido</h2>
                        <div className="input-div one">
                            <div className="i">
                                <FontAwesomeIcon icon={faAt}/>
                            </div>
                            <div className="div">
                                <h5>Correo Electronico</h5>
                                <input name={'email'} type="email" className="input"
                                       onFocus={addFocus} onBlur={removeFocus}
                                       {...email}
                                />
                            </div>
                        </div>
                        <div className="input-div pass">
                            <div className="i">
                                <FontAwesomeIcon icon={faLock}/>

                            </div>
                            <div className="div">
                                <h5>Contraseña</h5>
                                <input name={'password'} type="password" className="input"
                                       onFocus={addFocus} onBlur={removeFocus}
                                       {...password}
                                />
                            </div>
                        </div>
                        <Link to="#" className='a' onClick={() => alert("Mala Suerte")}>¿Olvidaste tu contraseña?</Link>
                        <input type="submit" className="button" value={'Entrar'}/>
                        <Link to='/signup'>Registrarse</Link>
                    </form>

                </div>
            </div>
        </>
    )

};

export default LogIn
