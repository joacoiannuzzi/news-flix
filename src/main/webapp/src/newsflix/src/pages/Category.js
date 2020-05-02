import React, {useEffect, useState} from "react";
import AppNav from "../components/AppNav";
import {Card, CardColumns} from "react-bootstrap";
import {Link} from "react-router-dom";

function Category({match}) {

    const category = match.params.name;

    useEffect(() => {
        fetchItems()
    }, []);

    const [item, setItems] = useState([]);

    const fetchItems = async () => {
        const data = await fetch(`/api/category/${category}`);
        const items = await data.json();
        setItems(items)
    };

    const cards = item.map(article => {
            if (article.image != null && article.image !== "" && article.image !== undefined)
                return (
                    <Card>
                        <Link to={"/articles/" + article.id}>
                            <Card.Img top width="100%" src={article.image}/>
                            <Card.Body>
                                <Card.Title>
                                    {article.title}
                                </Card.Title>
                            </Card.Body>
                        </Link>
                    </Card>
                );
            return (
                <Card>
                    <Link to={"/articles/" + article.id}>
                        <Card.Body>
                            <Card.Title>
                                {article.title}
                            </Card.Title>
                        </Card.Body>
                    </Link>
                </Card>
            )
        }
    );
    return (
        <>
            <AppNav/>
            <h1>{category}</h1>
            <CardColumns className="mx-3 mt-2">
                {cards}
            </CardColumns>
        </>
    );

}

export default Category