import React, {Component, useEffect, useState} from "react";
import AppNav from "../components/AppNav";
import {Container} from "react-bootstrap";

function Article({match}) {

    useEffect(() => {
        fetchItems()
    }, []);

    const [item, setItems] = useState([]);

    const fetchItems = async () => {
        const data = await fetch("/api/article/" + match.params.id);
        const items = await data.json();
        setItems(items)
    };
    return (

        <>
            <AppNav/>
            <Container>
                <h1>
                    {item.title}
                </h1>
                <p>
                    {item.body}

                </p>
            </Container>
        </>
    );

}

export default Article