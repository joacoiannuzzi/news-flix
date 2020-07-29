import {Button, Card, Col} from "react-bootstrap";
import React from "react";
import {Link} from "react-router-dom";


const MoreArticles = ({articles, xs, onClick}) => (
    articles.length ?
        <Col xs={xs}>
            <h2 className="mb-4">Articulos similares de otros diarios</h2>
            <Rect className="mb-4" articles={articles} onClick={onClick}/>
        </Col> :
        <></>
)

const Rect = ({articles, onClick, className}) =>
    articles.map((article) =>
        <Card className={className} key={article.id}>
            {article.image && <Card.Img top={"true"} width="100%" src={article.image}/>}
            <Card.Body>
                <Card.Title>
                    {article.title}
                </Card.Title>
                <Card.Subtitle className="text-muted mt-1 mb-2">{article.newspaper}</Card.Subtitle>
                <div style={{
                    display: 'flex',
                    justifyContent: 'space-evenly'
                }}>
                    <Link to={`/articles/${(article.id)}`}>
                        <Button variant="outline-primary" >Ver</Button>
                    </Link>
                    <Button variant="outline-info" onClick={() => onClick(article.id)}>Comparar</Button>
                </div>
            </Card.Body>
        </Card>
    )

export default MoreArticles