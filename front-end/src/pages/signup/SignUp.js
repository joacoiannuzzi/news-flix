import React from "react";
import {Link} from "react-router-dom";

import classes from './signup.module.css'
import avatar from '../../media/avatar.svg'
import addUser from '../../media/add_user.svg'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAt, faLock, faUser} from '@fortawesome/free-solid-svg-icons'
import useFormInput from "../../components/hooks/useFormInput";
import {signup} from "../../util/APIUtils";

const addFocus = ({target}) => {
    const parent = target.parentNode.parentNode;
    parent.classList.add(classes.focus);
};
const removeFocus = ({target}) => {
    const {parentNode, value} = target;
    const parent = parentNode.parentNode;
    if (value === "") {
        parent.classList.remove(classes.focus);
    }
};

const SignUp = ({history}) => {
    const email = useFormInput('');
    const password = useFormInput('');
    const firstName = useFormInput('');
    const lastName = useFormInput('');

    const handleSubmit = event => {
        event.preventDefault();
        const signUpRequest = {
            firstName: firstName.value,
            lastName: lastName.value,
            email: email.value,
            password: password.value
        };

        signup(signUpRequest)
            .then(response => history.push("/login"))
            .catch(error => {
                alert('Invalid Request');
                console.log(error);
            })
    };

    return (
        <>
            <div style={{
                marginTop: 100,
                width: '100%',
                display: "flex",
                justifyContent: "center",
                alignItems: "center"
            }}>
                <h1 className='display-1'>NEWS FLIX</h1>
            </div>

            <div className={classes.cont}>

                <div className={classes.loginContent}>
                    <form className={classes.form} onSubmit={handleSubmit}>
                        <img src={avatar} alt=''/>
                        <h2 className="title">Registrate</h2>

                        <div className={`${classes.inputDiv} ${classes.one}`}>
                            <div className={classes.i}>
                                <FontAwesomeIcon icon={faUser}/>
                            </div>
                            <div className="div">
                                <h5>Nombre</h5>
                                <input name={'firstName'} type="text" className="input"
                                       onFocus={addFocus} onBlur={removeFocus}
                                       {...firstName}
                                />
                            </div>
                        </div>

                        <div className={`${classes.inputDiv} ${classes.one}`}>
                            <div className={classes.i}>
                                <FontAwesomeIcon icon={faUser}/>
                            </div>
                            <div className="div">
                                <h5>Apellido</h5>
                                <input name={'lastName'} type="text" className="input"
                                       onFocus={addFocus} onBlur={removeFocus}
                                       {...lastName}
                                />
                            </div>
                        </div>

                        <div className={`${classes.inputDiv} ${classes.one}`}>
                            <div className={classes.i}>
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

                        <div className={`${classes.inputDiv} ${classes.pass}`}>
                            <div className={classes.i}>
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
                        <input type="submit" className={classes.button} value={'Entrar'}/>
                        <Link to='/login'>Iniciar sesion</Link>
                    </form>

                </div>

                <div className={classes.img}>
                    <img src={addUser} alt=''/>
                </div>
            </div>
        </>
    )

};

export default SignUp
