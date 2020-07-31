import React, {useState} from 'react';
import {Button, Container, Row} from "react-bootstrap";
import {getAvatarColor} from "../util/Colors";
import {useUser} from "../App";
import {useLocation, useHistory} from 'react-router-dom'
import {cancelSubscription} from "../util/APIUtils";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSpinner} from "@fortawesome/free-solid-svg-icons";


const Profile = () => {

    const {currentUser: user, updateCurrentUser} = useUser();
    const {firstName, lastName, email, active, id} = user
    const location = useLocation();
    const history = useHistory();

    const [processing, setProcessing] = useState(false)


    const changePassword = () => {
        history.push(location.pathname + `/changepassword`)
    }

    const handleCancelSubscription = () => {
        setProcessing(true)
        cancelSubscription(id)
            .then(res => {
                updateCurrentUser({
                    ...user,
                    active: false
                })
            })
            .catch(err => {
                alert('No se pudo cancelar la suscripcion')
            })
            .finally(() => setProcessing(false))
    }



    return (
        <Container>
            <Row>
                <div style={{
                    textAlign: "center",
                    width: '100%',
                    display: 'flex',
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center"
                }}>
                    <div id={'circle'} style={{
                        width: '100px',
                        height: '100px',
                        margin: "30px",
                        borderRadius: '50%',
                        backgroundColor: getAvatarColor(firstName),
                        fontSize: '3rem',
                        fontWeight: "normal",
                        display: 'flex',
                        justifyContent: "center",
                        alignItems: "center"
                    }}>
                        {firstName[0].toUpperCase()}
                    </div>
                    <div className="user-summary">
                        <div className="user-name" style={{
                            fontSize: "2rem",
                            marginBottom: '2rem',
                        }}>
                            {firstName} {lastName}
                        </div>
                        <div style={{
                            fontSize: "1.6rem",
                            marginBottom: '1rem'
                        }}>
                            Correo electronico: {email}

                        </div>

                        <div style={{
                            fontSize: "1.6rem",
                            marginBottom: '1rem'
                        }}>
                            Suscrito: {active ? 'Si' : 'No'}
                        </div>
                        <div style={{
                            fontSize: "3rem",
                            marginBottom: '5rem',
                        }}>
                            <Button variant="primary" type="submit" onClick={changePassword}>
                                Cambiar contraseña
                            </Button>
                            <br/>

                            {
                                active
                                    ? (
                                        <Button variant="danger" type="submit" onClick={handleCancelSubscription}>
                                            {!processing ? 'Cancelar suscripcion' : <FontAwesomeIcon icon={faSpinner} spin/>}
                                        </Button>
                                    )
                                    : (
                                        <Button variant="secondary" type="submit" onClick={() => history.push('/subscription')}>
                                            Suscribir
                                        </Button>
                                    )
                            }
                        </div>
                    </div>
                </div>

            </Row>
        </Container>
    )
}

export default Profile







