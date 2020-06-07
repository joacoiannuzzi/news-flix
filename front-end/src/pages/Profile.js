import React from 'react';
import {Button, Container, Row} from "react-bootstrap";
import {getAvatarColor} from "../util/Colors";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {UserContext} from "../App";


const Profile = (props) => {
    return (
        <UserContext.Consumer>
            {({currentUser: {firstName, lastName, email, favorites}, updateCurrentUser, loadUser}) => (
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
                            <div style={{
                                width: '200px',
                                height: '200px',
                                margin: "30px",
                                borderRadius: '50%',
                                backgroundColor: getAvatarColor(firstName),
                                fontSize: '7rem',
                                fontWeight: "normal",
                                display: 'flex',
                                justifyContent: "center",
                                alignItems: "center"
                            }}>
                                {firstName[0].toUpperCase()}
                            </div>
                            <div className="user-summary">
                                <div className="user-name" style={{
                                    fontSize: "4rem",
                                    marginBottom: '3rem',
                                }}>
                                    {firstName} {lastName}
                                </div>
                                <div style={{
                                    fontSize: "2rem",
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
                            <div id='favorites' style={{
                                marginBottom: '5rem'
                            }}>
                                <h2 style={{
                                    fontSize: '5rem',
                                    marginBottom: '4rem'
                                }}>
                                    Mis favoritos
                                </h2>
                                {
                                    favorites.length ?
                                        <ArticleCardColumns articles={favorites}/> :
                                        <p style={{
                                            fontSize: '2rem'
                                        }}>
                                            Actualmente no tienes favoritos
                                        </p>
                                }
                            </div>
                        </div>

                    </Row>
                </Container>
            )}
        </UserContext.Consumer>
    )
}

export default Profile







