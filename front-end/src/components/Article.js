import {Button, Col, Image} from "react-bootstrap";
import React from "react";
import {useUser} from "../App";
import {faHeart, faHeartBroken} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {addOrRemoveFavorite} from "../util/APIUtils";
import {formatDateTime} from "../util/Helpers";
import {
    FacebookIcon,
    FacebookShareButton,
    EmailIcon,
    EmailShareButton,
    // RedditIcon,
    // RedditShareButton,
    // RedditShareCount,
    FacebookShareCount,
    WhatsappShareButton,
    WhatsappIcon,
    TwitterIcon,
    TwitterShareButton
} from "react-share";


const Article = ({id: articleId, title, date, body, image, xs, handleStopCompare}) => {

    const {currentUser: {favorites, id: userId}, updateCurrentUser} = useUser();

    const handleFavorite = () => {
        addOrRemoveFavorite(userId, articleId)
            .then(updateCurrentUser)
    };

    const shareUrl = 'http://www.news-flix.com/articles/' + articleId;


    const split = body.split('\\{\\{\\{SPLIT\\}\\}\\}');

    return (
        <Col xs={xs}>
            <button onClick={handleFavorite} style={{
                background: 'none',
                border: 'none',
            }}>
                <FontAwesomeIcon icon={favorites.some(favorite => favorite.id === articleId) ? faHeart : faHeartBroken}
                                 style={{
                                     color: 'red',
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

                <div className="fbrender">
                    <FacebookShareButton
                        url={shareUrl}
                        quote={title}
                        className="fbrender__share-button"
                    >
                        <FacebookIcon size={32} round/>
                    </FacebookShareButton>

                    <WhatsappShareButton
                        url={shareUrl}
                        title={title}
                        separator=":: "
                        className="Demo__some-network__share-button"
                    >
                        <WhatsappIcon size={32} round/>
                    </WhatsappShareButton>
                    <TwitterShareButton
                        url={shareUrl}
                        title={title}
                        className="Demo__some-network__share-button"
                    >
                        <TwitterIcon size={32} round/>
                    </TwitterShareButton>

                    <EmailShareButton
                        url={shareUrl}
                        subject={title}
                        body="body"
                        className="Demo__some-network__share-button"
                    >
                        <EmailIcon size={32} round/>
                    </EmailShareButton>


                    <div>
                        <FacebookShareCount url={shareUrl} className="fbrender_share-count">
                            {count => count}
                        </FacebookShareCount>
                    </div>
                </div>
            </h4>
            <Image fluid src={image}/>
            <div className="text-justify mt-4">
                {split.map((paragraph, index) => (
                    <p key={index}>{paragraph}</p>
                ))}
            </div>
        </Col>

    )


};

export default Article