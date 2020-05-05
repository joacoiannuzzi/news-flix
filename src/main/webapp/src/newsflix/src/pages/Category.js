import React, {useEffect, useState} from "react";
import AppNav from "../components/AppNav";
import ArticleCArdColumns from "../components/ArticleCardColumns";

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

    return (
        <>
            <AppNav/>
            <h1>{category}</h1>
            <ArticleCArdColumns articles={item}/>
        </>
    );

}

export default Category