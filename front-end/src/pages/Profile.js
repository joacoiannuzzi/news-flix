import React from 'react';
import {Button, Container, Row} from "react-bootstrap";
import {getAvatarColor} from "../util/Colors";
import {useUser} from "../App";


const Profile = () => {

    const {currentUser: {firstName, lastName, email}} = useUser()

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
                            fontSize: "3rem",
                            marginBottom: '5rem',
                        }}>
                            <Button variant={"warning"} onClick={() => alert('TODO')}>
                                Cambiar contrasena
                            </Button>
                        </div>
                    </div>
                </div>

            </Row>
        </Container>
    )
}

export default Profile







