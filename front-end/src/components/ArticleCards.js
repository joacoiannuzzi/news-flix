import {Card} from "react-bootstrap";
import {Link} from "react-router-dom";
import React from "react";

function ArticleCards(props) {

    const articles = props.articles;

    return articles.map(article => {
            return (
                <Card className={props.className} key={article.id}>
                    <Link to={`/articles/${article.id}`}>
                        {article.image != null && article.image !== "" && article.image !== undefined ?
                            <Card.Img top={"true"} width="100%" src={article.image}/> :
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
    )

}

export default ArticleCards