import React, {Component, useEffect, useState} from "react";
import AppNav from "../components/AppNav";
import {Container} from "react-bootstrap";
import Button from "react-bootstrap/Button";

function Article({match}) {

    useEffect(() => {
        fetchItems();

    }, []);

    const [item, setItems] = useState([]);

    const fetchItems = async () => {
        const data = await fetch("/api/article/" + match.params.id);
        const items = await data.json();
        setItems(items)
    };

    const requestOptions = {
        method: 'Get',
        mode: 'no-cors',
        headers: {
            'Accept': 'application/json',
            'Content-type': 'application/json',
        },
    };

    const fetchSimilar = async() => {
        fetch("/api/articles/similar?id="+match.params.id+"&newspaper=La Nacion", requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
    }

    return (

        <>
            <AppNav/>
            <Container>
                <h1>
                    {item.title}
                </h1>

                <h4>
                    {item.date}
                </h4>
                <p>
                    {item.body}

                </p>

                <Button onClick={() => fetchSimilar()}>fetch</Button>
            </Container>
        </>
    );

}

export default Article