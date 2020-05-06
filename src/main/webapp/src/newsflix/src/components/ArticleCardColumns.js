import {CardColumns} from "react-bootstrap";
import React from "react";
import ArticleCards from "./ArticleCards";


function ArticleCardColumns(props) {

    return (
        <CardColumns className="mx-3 mt-4">
            <ArticleCards articles={props.articles}/>
        </CardColumns>
    )
}

export default ArticleCardColumns