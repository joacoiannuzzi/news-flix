import {CardColumns} from "react-bootstrap";
import React from "react";
import ArticleCards from "./ArticleCards";


const ArticleCardColumns = ({articles}) => (
    <CardColumns className="mx-3 mt-4">
        <ArticleCards articles={articles}/>
    </CardColumns>
);

export default ArticleCardColumns