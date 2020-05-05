import React, {Component, useEffect, useState} from "react";
import AppNav from "../components/AppNav";
import {Container} from "react-bootstrap";
import Button from "react-bootstrap/Button";

function Article({match}) {

    useEffect(() => {
        fetchItems()
        fetchSimilarArts()
    }, []);

    const [item, setItems] = useState([]);

    const fetchItems = async () => {
        const data = await fetch("/api/article/" + match.params.id);
        const items = await data.json();
        setItems(items)
    };

    const fetchSimilarArts = async () =>{

        const form = {id:match.params.id,newspaper:"Clarin"};
        const data = await fetch("/api/articles/similar",{
            method: 'post',
            mode: 'no-cors',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
            },
            body: JSON.stringify(form)
        });

        console.log(data);


    };
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

                <button className="btn btn-primary" onClick={fetchSimilarArts} type="submit">Button</button>
            </Container>
        </>
    );

}

export default Article