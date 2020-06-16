import React, {useContext} from 'react';
import ArticleCardColumns from "../components/ArticleCardColumns";
import {UserContext} from "../App";
import {Container, Row} from "react-bootstrap";


const Favorites = props => {

    const {currentUser: {firstName, lastName, email, favorites}} = useContext(UserContext);

    return (
        <Container>
            <Row>
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

            </Row>
        </Container>
    )
};

export default Favorites
