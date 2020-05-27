import {Button, Card, Col} from "react-bootstrap";
import React from "react";
import {Link} from "react-router-dom";


const MoreArticles = props => {
    const {articles, xs, onClick} = props

    return (
        articles.length ?
            <Col xs={xs}>
                <h2 className="mb-4">Articulos similares de otros diarios</h2>
                <Rect className="mb-4" articles={articles} onClick={onClick}/>
            </Col> :
            <></>
    )

}

const Rect = props => {
    const {articles, onClick, className} = props;

    return articles.map((article) => {
            return (
                <Card className={className} key={article.id}>
                    {article.image != null && article.image !== "" && article.image !== undefined ?
                        <Card.Img top={"true"} width="100%" src={article.image}/> :
                        <></>
                    }
                    <Card.Body>
                        <Card.Title>
                            {article.title}
                        </Card.Title>
                        <Card.Subtitle className="text-muted mt-1 mb-2">{article.newspaper}</Card.Subtitle>
                        <Link to={`/articles/${(article.id)}`}>
                            <Button variant="outline-primary" onClick={() => onClick(null)}>Ver</Button>
                        </Link>
                        <Button variant="outline-info" onClick={() => onClick(article)}>Comparar</Button>
                    </Card.Body>
                </Card>
            );
        }
    )
}

export default MoreArticles