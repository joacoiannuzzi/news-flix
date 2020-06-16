import React from "react";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {Container} from "react-bootstrap";
import LoadingIndicator from "../components/LoadingIndicator";
import {getFilteredArticles} from "../util/APIUtils";
import useGetArticles from "../components/hooks/useGetArticles";

const getQuery = search => new URLSearchParams(search).get('query');

const Search = ({location: {search}}) => {
    const query = getQuery(search);
    const {isLoading, articles} = useGetArticles(getFilteredArticles, query);

    if (isLoading)
        return <LoadingIndicator/>;

    return (
        <>
            <Container>
                <h1>Resultado para: {query}</h1>
                <ArticleCardColumns articles={articles}/>
            </Container>
        </>
    );

};

export default Search