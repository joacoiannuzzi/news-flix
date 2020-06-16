import React from "react";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {getNewspaper} from "../util/APIUtils";
import {useParams} from 'react-router-dom'
import LoadingIndicator from "../components/LoadingIndicator";
import {Container} from "react-bootstrap";
import useGetArticles from "../components/hooks/useGetArticles";

const Newspaper = () => {
    const {name: newspaper} = useParams();
    const {isLoading, articles} = useGetArticles(getNewspaper, newspaper);

    if (isLoading)
        return <LoadingIndicator/>;

    return (
        <>
            <Container>

                <h1>{newspaper}</h1>
                <ArticleCardColumns articles={articles}/>
            </Container>
        </>
    );

};

export default Newspaper