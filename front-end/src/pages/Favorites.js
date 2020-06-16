import React, {useContext} from "react";
import {UserContext} from "../App";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {Container} from "react-bootstrap";


const Favorites = () => {
    const {currentUser: {favorites}} = useContext(UserContext)

    return (
        <Container>

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
        </Container>
    )

}

export default Favorites