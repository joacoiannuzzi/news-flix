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

    return articles.map(({id, image, newspaper, title}) => {
            return (
                <Card className={className} key={id}>
                    {image != null && image !== "" && image !== undefined ?
                        <Card.Img top={"true"} width="100%" src={image}/> :
                        <></>
                    }
                    <Card.Body>
                        <Card.Title>
                            {title}
                        </Card.Title>
                        <Card.Subtitle className="text-muted mt-1 mb-2">{newspaper}</Card.Subtitle>
                        <Link to={`/articles/${id}`}>
                            <Button variant="outline-primary">Ver</Button>
                        </Link>
                        <Button variant="outline-info" onClick={() => onClick(id)}>Comparar</Button>
                    </Card.Body>
                </Card>
            );
        }
    )
}

export default MoreArticles