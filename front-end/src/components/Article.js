import {Button, Col, Image} from "react-bootstrap";
import React from "react";
import {useUser} from "../App";
import {faHeart, faHeartBroken} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {addOrRemoveFavorite} from "../util/APIUtils";
import {formatDateTime} from "../util/Helpers";


const Article = ({id: articleId, title, date, body, image, xs, handleStopCompare}) => {

    const {currentUser: {favorites, id: userId}, updateCurrentUser} = useUser()

    const handleFavorite = () => {
        addOrRemoveFavorite(userId, articleId)
            .then(updateCurrentUser)
    }

    return (
        <Col xs={xs}>
            <button onClick={handleFavorite} style={{
                background: 'none',
                border: 'none',
            }}>
                <FontAwesomeIcon icon={favorites.some(favorite => favorite.id === articleId) ? faHeart : faHeartBroken}
                                 style={{
                                     color: 'pink',
                                     fontSize: '30px'
                                 }}
                />
            </button>
            {handleStopCompare && (
                <Button onClick={handleStopCompare} variant='danger'>
                    Dejar de comparar
                </Button>
            )}
            <h1 className="">
                {title}
            </h1>
            <h4 className="mt-4">
                {formatDateTime(date)}
            </h4>
            <Image fluid src={image}/>
            <p className="text-justify mt-4">
                {body}
            </p>
        </Col>

    )


}

export default Article