import React, {useEffect, useState} from "react";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {getNewspaper} from "../util/APIUtils";

function Newspaper({match}) {

    const newspaper = match.params.name;
    useEffect(() => fetchItems());

    const [item, setItems] = useState([]);

    const fetchItems = () => {
        getNewspaper(newspaper)
            .then(response => {
                setItems(response)
            }).catch(error => console.log(error))
    };

    return (
        <>
            <h1>{newspaper}</h1>
            <ArticleCardColumns articles={item}/>

        </>
    );

}

export default Newspaper