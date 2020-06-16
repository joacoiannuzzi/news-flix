import React from "react";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {getCategory} from "../util/APIUtils";
import {useParams} from 'react-router-dom'
import LoadingIndicator from "../components/LoadingIndicator";
import {Container} from "react-bootstrap";
import useGetArticles from "../components/hooks/useGetArticles";

const Category = () => {
    const {name: category} = useParams();
    const {isLoading, articles} = useGetArticles(getCategory, category);

    if (isLoading)
        return <LoadingIndicator/>;

    return (
        <>
            <Container>

                <h1>{category}</h1>
                <ArticleCardColumns articles={articles}/>
            </Container>
        </>
    );

};

export default Category