import {Card} from "react-bootstrap";
import {Link} from "react-router-dom";
import React from "react";
import {UserContext} from "../App";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHeart, faHeartBroken} from "@fortawesome/free-solid-svg-icons";
import {addFavorite} from "../util/APIUtils";

function ArticleCards({articles, ...props}) {

    return (
        <UserContext.Consumer>
            {
                ({currentUser: {favorites, id: userId}, updateCurrentUser}) => {
                    const handleFavorite = async (userId, articleId) => {
                        const newVar = await addFavorite(userId, articleId)
                        updateCurrentUser(newVar)
                    }
                    return (
                        articles.map(article => {
                            return (
                                <Card className={props.className} key={article.id}>
                                    {article.image != null && article.image !== "" && article.image !== undefined ?
                                        <Card.Img top={"true"} width="100%" src={article.image}/> :
                                        <></>
                                    }
                                    <Card.Body>
                                        <Link to={`/articles/${article.id}`}>
                                            <Card.Title>
                                                {article.title}
                                            </Card.Title>
                                        </Link>
                                        <Card.Subtitle style={{
                                            display: 'flex',
                                            justifyContent: 'space-between'
                                        }}>
                                            <p className="text-muted mt-1 mb-n2">
                                                {article.newspaper}
                                            </p>
                                            <button onClick={() => handleFavorite(userId, article.id)} style={{
                                                background: 'none',
                                                border: 'none',
                                            }}>
                                                <FontAwesomeIcon icon={favorites.some(favorite => favorite.id === article.id) ? faHeart : faHeartBroken} style={{
                                                    color: 'pink',
                                                }}/>
                                            </button>
                                        </Card.Subtitle>
                                    </Card.Body>
                                </Card>
                            )
                        })
                    );
                }
            }
        </UserContext.Consumer>
    )
}

export default ArticleCards