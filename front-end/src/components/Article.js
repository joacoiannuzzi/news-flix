import {Button, Col, Image} from "react-bootstrap";
import React, {useContext} from "react";
import {UserContext} from "../App";
import {faHeart, faHeartBroken} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {addFavorite} from "../util/APIUtils";


const Article = (props) => {

    const {currentUser: {favorites, id: userId}, updateCurrentUser} = useContext(UserContext)
    const {id, title, date, body, image, xs, handleStopCompare} = props

    const handleFavorite = async (userId, articleId) => {
        const newVar = await addFavorite(userId, articleId)
        updateCurrentUser(newVar)
    }

    return (
        <Col xs={xs}>
            <button onClick={() => handleFavorite(userId, id)} style={{
                background: 'none',
                border: 'none',
            }}>
                <FontAwesomeIcon icon={favorites.some(favorite => favorite.id === id) ? faHeart : faHeartBroken}
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
            )
            }
            <h1 className="">
                {title}
            </h1>
            <h4 className="mt-4">
                {date}
            </h4>
            <Image fluid src={image}/>
            <p className="text-justify mt-4">
                {body}
            </p>
        </Col>

    )


}

export default Article