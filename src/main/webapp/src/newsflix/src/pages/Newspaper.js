import React, {useEffect, useState} from "react";
import AppNav from "../components/AppNav";
import ArticleCArdColumns from "../components/ArticleCardColumns";

function Newspaper({match}) {

    const newspaper = match.params.name;
    useEffect(() => {
        fetchItems()
    }, []);

    const [item, setItems] = useState([]);

    const fetchItems = async () => {
        const data = await fetch(`/api/newspaper/${newspaper}`);
        const items = await data.json();
        setItems(items)
    };

    return (
        <>
            <AppNav/>
            <h1>{newspaper}</h1>
            <ArticleCArdColumns articles={item}/>

        </>
    );

}

export default Newspaper