import {Col, Image} from "react-bootstrap";
import React from "react";
import {useUser} from "../App";
import {faHeart, faHeartBroken} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {addOrRemoveFavorite, shareArticle} from "../util/APIUtils";
import {formatDateTime} from "../util/Helpers";
import {useLocation} from 'react-router-dom'

import {
    FacebookIcon,
    FacebookShareButton,
    EmailIcon,
    EmailShareButton,
    FacebookShareCount,
    WhatsappShareButton,
    WhatsappIcon,
    TwitterIcon,
    TwitterShareButton
} from "react-share";


const Article = ({id: articleId, title, date, body, image, xs, share}) => {

    const {currentUser: {favorites, id: userId}, updateCurrentUser} = useUser();

    const location = useLocation()
    console.log(location)

    const handleFavorite = () => {
        addOrRemoveFavorite(userId, articleId)
            .then(updateCurrentUser)
    };

    const shareUrl = 'http://www.news-flix.com' + location.pathname + location.search;
    const compareId = new URLSearchParams(location.search).get('compare')

    const split = body.split('\\{\\{\\{SPLIT\\}\\}\\}');

    const handleShare = (a) => {
        console.log(a)
        shareArticle({
            articleId1: articleId,
            articleId2: compareId
        })
    }

    return (
        <Col xs={xs}>

            <h1 className="">
                {title}
            </h1>
            <h4 className="mt-4">
                {formatDateTime(date)}

                {
                    share &&
                    <div className="fbrender">
                        <FacebookShareButton
                            url={shareUrl}
                            quote={title}
                            className="fbrender__share-button"
                            onClick={handleShare}
                        >
                            <FacebookIcon size={32} round/>
                        </FacebookShareButton>

                        <WhatsappShareButton
                            url={shareUrl}
                            title={title}
                            separator=":: "
                            className="Demo__some-network__share-button"
                            onClick={handleShare}

                        >
                            <WhatsappIcon size={32} round/>
                        </WhatsappShareButton>
                        <TwitterShareButton
                            url={shareUrl}
                            title={title}
                            className="Demo__some-network__share-button"
                            onClick={handleShare}

                        >
                            <TwitterIcon size={32} round/>
                        </TwitterShareButton>

                        <EmailShareButton
                            url={shareUrl}
                            subject={title}
                            body={title}
                            className="Demo__some-network__share-button"
                            beforeOnClick={handleShare}

                        >
                            <EmailIcon size={32} round/>
                        </EmailShareButton>


                        <div>
                            <FacebookShareCount url={shareUrl} className="fbrender_share-count" onClick={handleShare}>
                                {count => count}
                            </FacebookShareCount>
                        </div>

                    </div>

                }

                <div>
                    <button onClick={handleFavorite} style={{
                        background: 'none',
                        border: 'none',
                    }}>
                        <FontAwesomeIcon
                            icon={favorites.some(favorite => favorite.id === articleId) ? faHeart : faHeartBroken}
                            style={{
                                color: 'red',
                                fontSize: 30
                            }}
                        />
                    </button>
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