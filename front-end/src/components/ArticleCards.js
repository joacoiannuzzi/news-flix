import React from "react";
import {Card} from "react-bootstrap";
import {Link} from "react-router-dom";
import {useUser} from "../App";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHeart, faHeartBroken} from "@fortawesome/free-solid-svg-icons";
import {addOrRemoveFavorite} from "../util/APIUtils";

const ArticleCards = ({articles, className}) => {

    const {currentUser: {favorites, id: userId}, updateCurrentUser} = useUser()

    const handleFavorite = (articleId) => {
        addOrRemoveFavorite(userId, articleId)
            .then(updateCurrentUser)
    }

    return (
        articles.map(article => (
            <Card className={className} key={article.id}>
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
                        <button onClick={() => handleFavorite(article.id)} style={{
                            background: 'none',
                            border: 'none',
                        }}>
                            <FontAwesomeIcon
                                icon={favorites.some(favorite => favorite.id === article.id) ? faHeart : faHeartBroken}
                                style={{
                                    color: 'red',
                                }}/>
                        </button>
                    </Card.Subtitle>
                </Card.Body>
            </Card>
        ))
    )
};

export default ArticleCards