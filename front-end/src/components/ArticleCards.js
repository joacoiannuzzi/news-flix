import React, {useContext} from "react";
import {Card} from "react-bootstrap";
import {Link} from "react-router-dom";
import {UserContext} from "../App";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHeart, faHeartBroken} from "@fortawesome/free-solid-svg-icons";
import {addFavorite} from "../util/APIUtils";

function ArticleCards({articles, ...props}) {

    const {currentUser: {favorites, id: userId}, updateCurrentUser} = useContext(UserContext)

    const handleFavorite = async (userId, articleId) => {
        const newVar = await addFavorite(userId, articleId)
        updateCurrentUser(newVar)
    }

    return (
        articles.map(article => (
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
                            <FontAwesomeIcon
                                icon={favorites.some(favorite => favorite.id === article.id) ? faHeart : faHeartBroken}
                                style={{
                                    color: 'pink',
                                }}/>
                        </button>
                    </Card.Subtitle>
                </Card.Body>
            </Card>
        ))
    )
}

export default ArticleCards