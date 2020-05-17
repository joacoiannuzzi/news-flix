import React, {useEffect, useState} from "react";
import AppNav from "../components/AppNav";
import ArticleCardColumns from "../components/ArticleCardColumns";

function Category({match}) {

    const category = match.params.name;

    useEffect(() => {
        fetchItems()
    }, []);

    const [item, setItems] = useState([]);

    const fetchItems = async () => {
        const data = await fetch(`/api/articles/categories/${category}`);
        const items = await data.json();
        setItems(items)
    };

    return (
        <>
            <AppNav/>
            <h1>{category}</h1>
            <ArticleCardColumns articles={item}/>
        </>
    );

}

export default Category