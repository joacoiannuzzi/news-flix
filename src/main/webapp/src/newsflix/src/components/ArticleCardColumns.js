import {Card, CardColumns} from "react-bootstrap";
import {Link} from "react-router-dom";
import React from "react";


function ArticleCArdColumns(props) {

    const articles = props.articles

    let cards = articles.map(article => {
            return (
                <Card>
                    <Link to={`/articles/${article.id}`}>
                        {article.image != null && article.image !== "" && article.image !== undefined ?
                            <Card.Img top width="100%" src={article.image}/> :
                            <></>
                        }
                        <Card.Body>
                            <Card.Title>
                                {article.title}
                            </Card.Title>
                            <Card.Subtitle className="text-muted mt-1 mb-n2">{article.newspaper}</Card.Subtitle>
                        </Card.Body>
                    </Link>
                </Card>
            );
        }
    );
    return (
        <CardColumns className="mx-3 mt-4">
            {cards}
        </CardColumns>
    )
}

export default ArticleCArdColumns