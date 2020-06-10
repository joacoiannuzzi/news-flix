import React, {useEffect, useState} from "react";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {getCategory} from "../util/APIUtils";

function Category({match: { params: { name: category }}}) {

    const [item, setItems] = useState([]);
    useEffect(() => fetchItems());

    const fetchItems = () => {
        getCategory(category)
            .then(response => {
                setItems(response)
            }).catch(error => console.log(error))
    };

    return (
        <>
            <h1>{category}</h1>
            <ArticleCardColumns articles={item}/>
        </>
    );

}

export default Category